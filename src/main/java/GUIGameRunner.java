import game.GameConstants;
import game.Player;
import gui.CompetitionWindow;

import java.util.ArrayList;
import java.util.List;

public class GUIGameRunner {

    private CompetitionWindow gameFrame;

    public static void main(String[] args){
        GUIGameRunner runner = new GUIGameRunner();
    }

    public GUIGameRunner(){
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player("p1", null, null));
        playerList.add(new Player("p2", null, null));
        gameFrame = new CompetitionWindow(playerList, new GameConstants());
        gameFrame.setVisible(true);
        gameFrame.pack();
    }

}
