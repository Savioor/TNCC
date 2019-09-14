package game;

import game.actions.IAction;
import game.events.AbstractEvent;
import game.events.ProductionEvent;

import java.util.ArrayList;
import java.util.List;
import util.log.Logger;
import util.log.NamedLogger;

public class Game {

    private List<Player> players;
    private List<AbstractEvent> events;
    private GameConstants consts;
    private Logger gameLogger;
    private int turn;
    private final long BOT_TIMEOUT = 20;

    public Game(List<Player> players, GameConstants conts) {
        this.players = players;
        this.events = new ArrayList<>();
        this.consts = conts;
        events.add(new ProductionEvent(this));
        for (Player p : players)
            p.initialize(this);
        gameLogger = new NamedLogger("GAME");
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
            if (p.isAlive()) {
                if (!p.isBot) {
                    executeTurn(p);
                } else {
                    Thread botThread = new Thread(() -> executeTurn(p));
                    botThread.start();
                    long timeStart = System.currentTimeMillis();
                    while (botThread.isAlive()){
                        if (System.currentTimeMillis() - timeStart > BOT_TIMEOUT){
                            botThread.interrupt();
                            gameLogger.warn(p.getName() + " took too much time to respond");
                            break;
                        }
                    }
                }
            }
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
        return null;
    }

    public void executeTurn(Player current){

        IAction action;

        while(true){
            action = current.getAction(this);
            if (action.execute(this, current))
                break;
            gameLogger.warn(current.getName() + " executed Illegal action " + action.getName());
        }

        gameLogger.debug(action.getName() + " was run by " + current.getName());

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
