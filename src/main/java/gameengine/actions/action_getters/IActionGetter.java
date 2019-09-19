package gameengine.actions.action_getters;

import gameengine.Game;
import gameengine.Player;
import gameengine.actions.IRespondableAction;

@FunctionalInterface
public interface IActionGetter {

    IRespondableAction getAction(Game game, Player player);

}
