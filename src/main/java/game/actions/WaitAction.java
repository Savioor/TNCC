package game.actions;

import game.Game;
import game.Player;

import java.util.List;

public class WaitAction implements IAction {

    public String getInfo() {
        return "wait | Skips the turn";
    }

    @Override
    public String getName() {
        return "wait";
    }

    @Override
    public IAction parse(Game game, List<String> data) {
        if (data.size() != 0)
            return new ErrorAction("Expected 0 arguments, received " + data.size());
        return new WaitAction();
    }

    @Override
    public final ActionInfo execute(Game game, Player actor) {
        return new ActionInfo(true, actor + " is doing nothing this turn");
    }
}
