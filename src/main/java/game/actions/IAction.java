package game.actions;

import game.Game;
import game.Player;

import java.util.List;

public interface IAction {

    public static class ActionInfo{
        public final boolean success;
        public final String message;
        public ActionInfo(boolean success, String message){
            this.success = success;
            this.message = message;
        }
    }

    String getInfo();
    String getName();
    IAction parse(Game game, List<String> data);

    ActionInfo execute(Game game, Player actor);

}
