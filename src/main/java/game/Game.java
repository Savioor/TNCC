package game;

import game.actions.IRespondableAction;
import game.actions.WaitAction;
import game.actions.reactions.Reaction;
import game.events.AbstractEvent;
import game.events.ProductionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import util.Tuple2;
import util.log.Logger;
import util.log.NamedLogger;

import javax.swing.*;

public class Game {

    private List<Player> players;
    private List<AbstractEvent> events;
    private GameConstants consts;
    private Logger logger;
    private int turn;
    private final long BOT_TIMEOUT = 20;

    public static final String ACTION_OK = "OK", ACTION_FAIL = "FAIL";

    public Game(List<Player> players, GameConstants conts) {
        this.players = players;
        this.events = new ArrayList<>();
        this.consts = conts;
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
        return null;
    }

    public void executeTurn(Player current){

        IRespondableAction action;
        Reaction reaction;
        Tuple2<Boolean, Tuple2<Player, List<String>>> response;

        while(true){

            action = current.getAction(this);
            response = action.execute(this, current);
            if (response.first) {

                if (response.second == null)
                    break;

                while (true) {
                    reaction = response.second.first.getReaction(response.second.second, this);
                    if (action.validateResponse(reaction)){
                        break;
                    }
                    if (response.second.first.isBot){
                        reaction = action.defaultBotResponse();
                        break;
                    }
                }

                if (action.executeWithResponse(this, current, response.second.first, reaction)){
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
