package game.actions.action_getters;

import game.Game;
import game.Player;
import game.actions.IAction;

@FunctionalInterface
public interface IActionGetter {

    IAction getAction(Game game, Player player);

}
