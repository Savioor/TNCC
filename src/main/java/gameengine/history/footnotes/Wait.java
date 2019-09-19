package gameengine.history.footnotes;

import gameengine.Player;
import gameengine.history.HistoricalAction;

public class Wait extends HistoricalAction {

    public Wait(Player p){
        actor = p.getName();
        type = Type.WAIT;
    }

}
