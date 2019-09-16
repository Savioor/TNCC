import bots.NothingBot;
import bots.TurtleBot;
import game.Game;
import game.GameConstants;
import game.Player;
import game.actions.*;
import game.actions.action_getters.ConsoleGetter;
import game.actions.action_getters.ConsoleReactor;
import game.actions.reactions.Reaction;
import game.actions.reactions.TradeReaction;
import game.actions.reactions.WarReaction;
import game.history.HistoricalAction;
import game.history.footnotes.Trade;

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

        players.add(new Player("turtle", new TurtleBot(), new TurtleBot()));

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
