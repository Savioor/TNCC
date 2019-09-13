import bots.TurtleBot;
import game.Competition;
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
        playerList.add(new Player("p1", new TurtleBot(), new TurtleBot()));
        playerList.add(new Player("p2", new TurtleBot(), new TurtleBot()));
        playerList.add(new Player("p3", new TurtleBot(), new TurtleBot()));
        playerList.add(new Player("p4", new TurtleBot(), new TurtleBot()));
        playerList.add(new Player("p5", new TurtleBot(), new TurtleBot()));
        Competition c = new Competition(playerList);
        gameFrame = new CompetitionWindow(c);
        gameFrame.setVisible(true);
        gameFrame.pack();
    }

}
