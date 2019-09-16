package game.history.footnotes;

import game.Player;
import game.history.HistoricalAction;

public class Wait extends HistoricalAction {

    public Wait(Player p){
        actor = p.getName();
        type = Type.WAIT;
    }

}
