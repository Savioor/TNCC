import bots.*;
import bottools.Bot;
import game.Competition;
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
        playerList.add(createBotPlayer("p1", new NothingBot()));
        playerList.add(createBotPlayer("p2", new TurtleBot()));
        playerList.add(createBotPlayer("p3", new TurtleBot()));
        playerList.add(createBotPlayer("p4", new TurtleBot()));
        playerList.add(createBotPlayer("p5", new WarBot()));
        Competition c = new Competition(playerList);
        gameFrame = new CompetitionWindow(c);
        gameFrame.setVisible(true);
        gameFrame.pack();
    }

}
