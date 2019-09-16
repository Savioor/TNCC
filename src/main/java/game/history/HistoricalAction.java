package game.history;

import game.Player;

import java.util.ArrayList;

public abstract class HistoricalAction {

    protected Type type;
    protected String actor;
    protected int[] parameters;

    public enum Type {
        WAR,
        TRADE,
        RECRUIT,
        WAIT
    }

    public String getActor(){
        return actor;
    }


    public Type getType(){
        return type;
    }

}
