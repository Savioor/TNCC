package game.actions.action_getters;

import game.Game;
import game.Player;
import game.actions.reactions.FailedReaction;
import game.actions.reactions.Reaction;

import java.util.*;

public class ConsoleReactor implements IReActionGetter {

    private Scanner output = new Scanner(System.in);

    public ConsoleReactor(List<Reaction> reactions) {
        this.reactions = reactions;
    }

    private List<Reaction> reactions;

    @Override
    public <T> Reaction<T> getReAction(List<String> action, Game game, Player player) {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("------------------");
        System.out.println(player.getName() + ", Your reaction is required!");
        System.out.println("Message received was:\n");
        System.out.println(action);
        System.out.println();
        System.out.println("Please react.");
        System.out.print(" >>> ");
        String data = output.nextLine();
        ArrayList<String> dataSpalt = new ArrayList<>(Arrays.asList(data.split(" ")));
        String name = action.get(0);
        for (Reaction react : reactions){
            if (name.equals(react.getName())) {
                Reaction rec = react.parse(game, dataSpalt);
                try {
                    return (Reaction<T>)rec;
                } catch (ClassCastException e) {}
            }
        }
        return new FailedReaction<>();
    }
}
