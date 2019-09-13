package game;

import game.actions.IAction;
import game.events.AbstractEvent;
import game.events.ProductionEvent;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private List<Player> players;
    private List<AbstractEvent> events;
    private GameConstants consts;

    public Game(List<Player> players, GameConstants conts) {
        this.players = players;
        this.events = new ArrayList<>();
        this.consts = conts;
        events.add(new ProductionEvent(this));
        for (Player p : players)
            p.initialize(this);
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

    public void executeCycle(){
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
    }

    public void executeTurn(Player current){

        IAction action;
        System.out.println("HMmmmmmt");

        while(true){
            action = current.getAction(this);
            if (action.execute(this, current))
                break;
        }

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
