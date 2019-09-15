package game.history.footnotes;

import game.Player;
import game.history.HistoricalAction;

public class War extends HistoricalAction {

    private String other;

    public War(Player p, Player o, int a, int d){
        actor = p.getName();
        other = o.getName();
        parameters = new int[] {a, d};
        type = Type.WAR;
    }

    public String getOther() {
        return other;
    }

    public int getAttackingForce(){
        return parameters[0];
    }

    public int getDefendingForce(){
        return parameters[1];
    }
}
