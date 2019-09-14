package game.actions;

import game.Game;
import game.Player;
import game.actions.reactions.Reaction;
import util.Tuple2;

import java.util.List;

public class WaitAction implements IRespondableAction<Reaction> {

    public String getInfo() {
        return "wait | Skips the turn";
    }

    @Override
    public String getName() {
        return "wait";
    }

    @Override
    public IRespondableAction<Reaction> parse(Game game, List<String> data) {
        if (data.size() != 0)
            return new ErrorAction("Expected 0 arguments, received " + data.size());
        return new WaitAction();
    }

    @Override
    public Tuple2<Boolean, Tuple2<Player, List<String>>> execute(Game game, Player actor) {
        return new Tuple2<>(true, null);
    }

    @Override
    public boolean validateResponse(Reaction reaction) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean executeWithResponse(Game game, Player actor, Player reactor, Reaction reaction) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Reaction defaultBotResponse() {
        return null;
    }
}
