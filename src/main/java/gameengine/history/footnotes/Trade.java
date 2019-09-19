package gameengine.history.footnotes;

import gameengine.Game;
import gameengine.Player;
import gameengine.history.HistoricalAction;

public class Trade extends HistoricalAction {

    private String other;

    public Trade(Player p, Player o, int amountG, int resG, int amountT, int resT, boolean accepted){
        actor = p.getName();
        other = o.getName();
        parameters = new int[] {amountG, resG, amountT, resT, accepted ? 1 : 0};
        type = Type.TRADE;
    }

    public String getOther() {
        return other;
    }

    public int getAmountOffering(){
        return parameters[0];
    }

    public Game.Resources getOffering(){
        return Game.Resources.values()[parameters[1]];
    }

    public int getAmountRequesting(){
        return parameters[2];
    }

    public Game.Resources getRequest(){
        return Game.Resources.values()[parameters[3]];
    }

    public boolean didGetAccepted(){
        return parameters[4] == 1;
    }

}
