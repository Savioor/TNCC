package game.actions;

import game.Game;
import game.Player;

import java.util.List;

public class ErrorAction implements IAction {

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
    public IAction parse(Game game, List<String> data) {
        return new ErrorAction("");
    }

    @Override
    public boolean execute(Game game, Player actor) {
        System.err.println(reason);
        return false;
    }

    public ErrorAction(String reason){
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
