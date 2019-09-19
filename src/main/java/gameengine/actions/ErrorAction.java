package gameengine.actions;

import gameengine.Game;
import gameengine.Player;
import gameengine.actions.reactions.Reaction;
import gameengine.history.HistoricalAction;
import util.Tuple2;

import java.util.List;

public class ErrorAction implements IRespondableAction<Reaction> {

    private String reason;

    @Override
    public String getInfo() {
        return "error | Always fails, prompting another action to be asked";
    }

    @Override
    public String getName() {
        return "error";
    }

    @Override
    public IRespondableAction<Reaction> parse(Game game, List<String> data) {
        return new ErrorAction("");
    }

    @Override
    public Tuple2<Boolean, Tuple2<Player, List<String>>> execute(Game game, Player actor) {
        System.err.println(reason);
        return new Tuple2<>(false, null);
    }

    public ErrorAction(String reason){
        this.reason = reason;
    }

    public String getReason() {
        return reason;
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

    @Override
    public HistoricalAction generateChronicle(Game game, Player actor, Player reactor, Reaction reaction) {
        return null;
    }
}
