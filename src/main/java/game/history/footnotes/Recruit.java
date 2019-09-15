package game.history.footnotes;

import game.Player;
import game.history.HistoricalAction;

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
