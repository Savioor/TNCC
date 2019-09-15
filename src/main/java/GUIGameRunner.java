import bots.*;
import bottools.Bot;
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

    private static Player createBotPlayer(String name, Bot bot){
        return new Player(name, bot, bot, true);
    }

    public GUIGameRunner(){
        List<Player> playerList = new ArrayList<>();
        playerList.add(createBotPlayer("War1", new WarBot()));
        playerList.add(createBotPlayer("War2", new WarBot()));

        playerList.add(createBotPlayer("Turtle1", new TurtleBot()));
        playerList.add(createBotPlayer("Turtle2", new TurtleBot()));

        playerList.add(createBotPlayer("Blitz1", new BlitzBot()));
        playerList.add(createBotPlayer("Blitz2", new BlitzBot()));


        Competition c = new Competition(playerList);
        gameFrame = new CompetitionWindow(c, new GameConstants(GameConstants.ConstantsGroup.CRAZY));
        gameFrame.setVisible(true);
        gameFrame.pack();
    }

}
