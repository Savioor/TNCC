package game.actions.action_getters;

import game.Game;
import game.Player;
import game.actions.reactions.Reaction;

import java.util.List;

@FunctionalInterface
public interface IReActionGetter {

    <T> Reaction<T> getReAction(List<String> action, Game game, Player player);

}
