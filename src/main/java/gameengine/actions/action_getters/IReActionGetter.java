package gameengine.actions.action_getters;

import gameengine.Game;
import gameengine.Player;
import gameengine.actions.reactions.Reaction;

import java.util.List;

@FunctionalInterface
public interface IReActionGetter {

    <T> Reaction<T> getReAction(List<String> action, Game game, Player player);

}
