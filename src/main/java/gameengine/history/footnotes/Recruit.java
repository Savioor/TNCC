package gameengine.history.footnotes;

import gameengine.Player;
import gameengine.history.HistoricalAction;

public class Recruit extends HistoricalAction {

    public Recruit(Player p, int amount){
        actor = p.getName();
        type = Type.RECRUIT;
        parameters = new int[]{amount};
    }

    public int getAmount(){
        return parameters[0];
    }

}
