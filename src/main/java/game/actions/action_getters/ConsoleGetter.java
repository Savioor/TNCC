package game.actions.action_getters;

import game.Game;
import game.Player;
import game.actions.ErrorAction;
import game.actions.IAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleGetter implements IActionGetter {

    private Scanner output = new Scanner(System.in);
    private List<IAction> possibleActions;

    public ConsoleGetter(List<IAction> actions) {
        possibleActions = actions;
    }

    @Override
    public IAction getAction(Game game, Player player) {
        System.out.println("------------------");
        System.out.println("Current Board: ");
        StringBuilder title = new StringBuilder("NAME\t");
        for (Game.Resources res : Game.Resources.values()){
            title.append(res.name());
            title.append("\t");
        }
        title.append("ALIVE");
        System.out.println(title.toString());
        for (Player p : game.getPlayers()){
            StringBuilder resourseBuilder = new StringBuilder(p.getName() + "\t");
            for (Game.Resources res : Game.Resources.values()){
                resourseBuilder.append(p.getResource(res.ordinal()));
                resourseBuilder.append("\t\t");
            }
            resourseBuilder.append(p.isAlive());
            System.out.println(resourseBuilder.toString());
        }
        System.out.println("------------------");
        System.out.println("It's " + player.getName() + "'s turn. What will be his action?");
        System.out.println("Options: ");
        for (IAction action : possibleActions){
            System.out.println(action.getInfo());
        }
        System.out.println();
        System.out.print(" >>> ");
        String data = output.nextLine();
        ArrayList<String> dataSpalt = new ArrayList<>(Arrays.asList(data.split(" ")));
        String name = dataSpalt.get(0).toLowerCase();
        dataSpalt.remove(0);
        for (IAction action : possibleActions){
            if (name.equals(action.getName()))
                return action.parse(game, dataSpalt);
        }

        return new ErrorAction(name + " is not a valid action.");
    }
}
