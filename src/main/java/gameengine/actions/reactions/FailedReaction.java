package gameengine.actions.reactions;

import gameengine.Game;

import java.util.List;

public class FailedReaction<T> extends Reaction<T> {
    public FailedReaction() {
        super(null, Status.FAILED);
    }

    @Override
    public String getInfo() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public Reaction parse(Game game, List data) {
        return new FailedReaction();
    }
}
