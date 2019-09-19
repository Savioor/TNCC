package gameengine;

import gameengine.actions.IRespondableAction;
import gameengine.actions.WaitAction;
import gameengine.actions.reactions.Reaction;
import gameengine.events.AbstractEvent;
import gameengine.events.ProductionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import gameengine.history.HistoricalAction;
import util.Tuple2;
import util.log.Logger;
import util.log.NamedLogger;

public class Game {

    private List<Player> players;
    private List<AbstractEvent> events;
    private GameConstants consts;
    private Logger logger;
    private int turn;
    private final long BOT_TIMEOUT = 50;
    private final long BOT_REPLY_TIMEOUT = 25;
    private ArrayList<HistoricalAction> historyBook;
    private ExecutorService executor;

    public static final String ACTION_OK = "OK", ACTION_FAIL = "FAIL";

    public Game(List<Player> players, GameConstants conts) {
        this.players = players;
        this.events = new ArrayList<>();
        this.executor = Executors.newSingleThreadExecutor();
        this.consts = conts;
        this.historyBook = new ArrayList<>();
        events.add(new ProductionEvent(this));
        for (Player p : players)
            p.initialize(this);
        logger = new NamedLogger("GAME");
        logger.setDebug(false);
        logger.setInfo(true);
        logger.setWarn(true);
        turn = 0;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameConstants getConsts() {
        return consts;
    }

    public enum Resources{
        GOLD,
        FOOD,
        POPULATION,
        MILITARY,
        LAND
    }

    public List<Player> executeCycle(){
        for (Player p : players) {
            if (p.isAlive())
                executeTurn(p);
        }

        List<AbstractEvent> eventClone = new ArrayList<>(events);
        for (AbstractEvent event : eventClone){
            event.execute();
            if (event.isFinished())
                events.remove(event);
        }

        List<Player> alive = new ArrayList<>();
        for (Player p : players)
            if (p.isAlive()) alive.add(p);
        if (alive.size() == 1){
            return alive;
        }
        if (turn == consts.maxTurns){
            return alive;
        }

        turn++;
        logBoard();
        return null;
    }

    private void logBoard(){
        logger.info("------------------");
        logger.info("Current Board: ");
        StringBuilder title = new StringBuilder("NAME\t");
        for (Game.Resources res : Game.Resources.values()){
            title.append(res.name());
            title.append("\t");
        }
        title.append("ALIVE");
        logger.info(title.toString());
        for (Player p : this.getPlayers()){
            StringBuilder resourseBuilder = new StringBuilder(p.getName() + "\t");
            for (Game.Resources res : Game.Resources.values()){
                resourseBuilder.append(p.getResource(res.ordinal()));
                resourseBuilder.append("\t\t");
            }
            resourseBuilder.append(p.isAlive());
            logger.info(resourseBuilder.toString());
        }
        logger.info("------------------");
    }

    public void executeTurn(Player current) {

        IRespondableAction action;
        Reaction reaction;
        Tuple2<Boolean, Tuple2<Player, List<String>>> response;
        Game game = this;
        Future<IRespondableAction> actionGetter = executor.submit(new Callable<>() {
            @Override
            public IRespondableAction call() {
                return current.getAction(game);
            }
        });

        while(true){

            if (current.isBot) {
                try {
                    action = actionGetter.get(BOT_TIMEOUT, TimeUnit.MILLISECONDS);
                } catch (Exception e) {
                    action = new WaitAction();
                    logger.warn(current.getName() + " took too much time/crashed");
                    logger.warn(e.toString());
                }
            } else {
                action = current.getAction(game);
            }
            if (action == null) action = new WaitAction();
            response = action.execute(this, current);
            if (response.first) {

                if (response.second == null) {
                    updateHistory(action, this, current, null, null);
                    break;
                }
                Player responder = getPlayerByNameOrId(response.second.first.getName());

                final var message = response.second.second;

                Future<Reaction> reactionGetter = executor.submit(new Callable<Reaction>() {
                    @Override
                    public Reaction call() {
                        return responder.getReaction(message, game);
                    }
                });

                while (true) {
                    if (responder.isBot){
                        try {
                            reaction = reactionGetter.get(BOT_REPLY_TIMEOUT, TimeUnit.MILLISECONDS);
                        } catch (Exception e) {
                            reaction = action.defaultBotResponse();
                            logger.warn(current.getName() + " took too much time/crashed");
                            logger.warn(e.toString());
                        }
                    } else {
                        reaction = responder.getReaction(response.second.second, this);
                    }
                    if (action.validateResponse(reaction)){
                        break;
                    }
                    if (response.second.first.isBot){
                        reaction = action.defaultBotResponse();
                        break;
                    }
                }

                if (action.executeWithResponse(this, current, responder, reaction)){
                    updateHistory(action, game, current, responder, reaction);
                    break;
                }

            }
            logger.warn(current.getName() + " executed Illegal action " + action.getName());
            if (current.isBot){
                action = new WaitAction();
                break;
            }
        }

        logger.debug(action.getName() + " was run by " + current.getName());

    }

    private void updateHistory(IRespondableAction action, Game game, Player actor, Player reactor, Reaction reac){
        historyBook.add(action.generateChronicle(game, actor, reactor, reac));
    }

    public List<HistoricalAction> getHistory(){
        return historyBook;
    }

    public Player getPlayerByNameOrId(String input){
        Player ret = null;
        try {
            int ID = Integer.parseInt(input);
            if (ID >= this.getPlayers().size() || ID < 0)
                throw new NumberFormatException();
            ret = this.getPlayers().get(ID);
        } catch (NumberFormatException e){
            for (Player p : this.getPlayers()){
                if (p.getName().equals(input)){
                    ret = p;
                    break;
                }
            }
        }
        return ret;
    }

}
