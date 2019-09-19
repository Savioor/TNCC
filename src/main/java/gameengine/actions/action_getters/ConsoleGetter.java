package gameengine.actions.action_getters;

import gameengine.Game;
import gameengine.Player;
import gameengine.actions.ErrorAction;
import gameengine.actions.IRespondableAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ConsoleGetter implements IActionGetter {

    private Scanner output = new Scanner(System.in);
    private List<IRespondableAction> possibleActions;

    public ConsoleGetter(List<IRespondableAction> actions) {
        possibleActions = actions;
    }

    @Override
    public IRespondableAction getAction(Game game, Player player) {
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
        for (IRespondableAction action : possibleActions){
            System.out.println(action.getInfo());
        }
        System.out.println();
        System.out.print(" >>> ");
        String data = output.nextLine();
        ArrayList<String> dataSpalt = new ArrayList<>(Arrays.asList(data.split(" ")));
        String name = dataSpalt.get(0).toLowerCase();
        dataSpalt.remove(0);
        for (IRespondableAction action : possibleActions){
            if (name.equals(action.getName()))
                return action.parse(game, dataSpalt);
        }

        return new ErrorAction(name + " is not a valid action.");
    }
}
