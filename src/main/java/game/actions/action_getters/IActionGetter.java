package game.actions.action_getters;

import game.Game;
import game.Player;
import game.actions.IRespondableAction;

@FunctionalInterface
public interface IActionGetter {

    IRespondableAction getAction(Game game, Player player);

}
