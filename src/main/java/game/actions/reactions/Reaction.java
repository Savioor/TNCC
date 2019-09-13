package game.actions.reactions;

import game.Game;

import java.util.List;

public abstract class Reaction<T> {

    private T reaction;
    private Status status;

    public Reaction(T reaction, Status status) {
        this.reaction = reaction;
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public T getReaction() {
        return reaction;
    }

    public abstract String getInfo();
    public abstract String getName();
    public abstract Reaction<T> parse(Game game, List<String> data);

    public enum Status {
        OK,
        FAILED
    }

}
