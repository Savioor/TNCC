package game.events;

import game.Game;

public abstract class TimedEvent extends AbstractEvent {

    private int timeout;
    private int timer;

    public TimedEvent(Game game, int timeout) {
        super(game);
        this.timeout = timeout;
    }

    @Override
    public void initialize(){
        timer = timeout;
    }

    @Override
    public void execute(){
        timer--;
    }

    @Override
    public boolean isFinished(){
        return timer <= 0;
    }

}
