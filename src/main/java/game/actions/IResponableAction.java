package game.actions;

import game.Game;
import game.Player;
import game.actions.reactions.Reaction;

public interface IResponableAction <T> extends IAction {

    boolean validateResponse(Reaction<T> reaction);
    boolean executeWithResponse(Game game, Player actor, Player reactor, Reaction<T> reaction);

}
