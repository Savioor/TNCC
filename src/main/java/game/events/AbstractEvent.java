package game.events;

import game.Game;

public abstract class AbstractEvent {

    protected Game game;

    public AbstractEvent(Game game){
        this.game = game;
    }

    public abstract void initialize();
    public abstract void execute();
    public abstract boolean isFinished();

}
