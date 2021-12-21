package gameengine.events;

import gameengine.Game;

public abstract class CountdownEvent extends TimedEvent {
    public CountdownEvent(Game game, int timeout) {
        super(game, timeout);
    }

    @Override
    public void execute() {
        super.execute();
        if (isFinished()) {
            action(game);
        }
    }

    abstract protected void action(Game game);


}
