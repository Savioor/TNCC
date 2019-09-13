package game.actions;

import game.Game;
import game.Player;

import java.util.List;

public interface IAction {

    String getInfo();
    String getName();
    IAction parse(Game game, List<String> data);

    boolean execute(Game game, Player actor);

}
