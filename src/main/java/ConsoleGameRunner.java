import botdata.bots.TurtleBot;
import gameengine.Game;
import gameengine.GameConstants;
import gameengine.Player;
import gameengine.actions.*;
import gameengine.actions.action_getters.ConsoleGetter;
import gameengine.actions.action_getters.ConsoleReactor;
import gameengine.actions.reactions.Reaction;
import gameengine.actions.reactions.TradeReaction;
import gameengine.actions.reactions.WarReaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConsoleGameRunner {

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args){

        List<Player> players = new ArrayList<>();

        List<IRespondableAction> actions = new ArrayList<>();
        actions.add(new WaitAction());
        actions.add(new RecruitAction(0));
        actions.add(new TradeAction(null, null, 0, 0, null));
        actions.add(new WarAction(null, null));

        List<Reaction> reactions = new ArrayList<>();
        reactions.add(new TradeReaction(false, Reaction.Status.FAILED));
        reactions.add(new WarReaction(null, Reaction.Status.FAILED));

        ConsoleGetter getter = new ConsoleGetter(actions);
        ConsoleReactor reactor = new ConsoleReactor(reactions);
        String name;

//        players.add(new Player("turtle", new TurtleBot(), new TurtleBot(), true));

        while(true){

            System.out.println("Enter name of player or 'end' to end:");
            name = input.next();
            if (name.equals("end"))
                break;
            players.add(new Player(name, getter, reactor));

        }

        Game game = new Game(players, new GameConstants(GameConstants.ConstantsGroup.DEFAULT));

        while (true){
            game.executeCycle();
        }
    }

}
