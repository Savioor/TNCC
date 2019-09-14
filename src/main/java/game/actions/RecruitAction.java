package game.actions;

import game.Game;
import game.Player;

import java.util.List;

public class RecruitAction implements IAction {

    private int toRecruit;

    public RecruitAction(int toRectuit){
        this.toRecruit = toRectuit;
    }

    @Override
    public String getInfo() {
        return "recruit [amount] | Converts the given amount into military, use negative numbers to covert back to citizen";
    }

    @Override
    public String getName() {
        return "recruit";
    }

    @Override
    public IAction parse(Game game, List<String> data) {
        if (data.size() != 1)
            return new ErrorAction("Expected 1 argument, received " + data.size());

        try {
            int a = Integer.parseInt(data.get(0));
            return new RecruitAction(a);
        } catch (NumberFormatException e){
            return new ErrorAction("Expected integer argument, got '" + data.get(0) + "'");
        }
    }

    @Override
    public final ActionInfo execute(Game game, Player actor) {
        if(toRecruit < 0) {
            return new ActionInfo(false, "trying to recruit negative amount: " + toRecruit);
        }
        if (actor.getPopulation() - toRecruit < 0){
            return new ActionInfo(false, "trying to recruit " + toRecruit +" but population is only " + actor.getPopulation());
        }

        actor.subtractResource(Game.Resources.POPULATION.ordinal(), toRecruit);
        actor.addResource(Game.Resources.MILITARY.ordinal(), toRecruit);

        return new ActionInfo(true, actor + " recruited " + toRecruit);
    }
}
