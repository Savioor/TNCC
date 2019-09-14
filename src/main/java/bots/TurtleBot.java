package bots;

import bottools.Bot;
import bottools.GameWrapper;
import game.Game;
import game.Player;
import game.actions.IAction;
import game.actions.RecruitAction;
import game.actions.reactions.Reaction;
import game.actions.reactions.TradeReaction;
import game.actions.reactions.WarReaction;
import util.ThreeTupleInt;

import java.util.ArrayList;
import java.util.List;

public class TurtleBot extends Bot {
    @Override
    public IAction getBotAction(GameWrapper game, Player self) {
        double foodPerArmy = game.getConsts().armyFoodConsumption;
        double goldForArmy = game.getConsts().goldForWar;
        int largestArmy = 0;
        for (Player p : game.getPlayers()){
            if (p.getResource(Game.Resources.GOLD) >= goldForArmy){
                if (p.getName().equals(self.getName()))
                    continue;
                if (p.getResource(Game.Resources.MILITARY) > largestArmy)
                    largestArmy = p.getResource(Game.Resources.MILITARY);
            }
        }
        if (largestArmy == 0) {
            return new RecruitAction(-self.getResource(Game.Resources.MILITARY));
        }

        int maxRecruit = (int)(self.getResource(Game.Resources.POPULATION)*(1 - (foodPerArmy / (foodPerArmy + 1.0))));
        int toRecruit = Math.min(largestArmy - self.getResource(Game.Resources.MILITARY), maxRecruit);
        return new RecruitAction(toRecruit);
    }

    @Override
    public TradeReaction acceptTrade(GameWrapper game, Player self, Player other, Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount) {
        return new TradeReaction(false, Reaction.Status.OK);
    }

    @Override
    public WarReaction fightWar(GameWrapper game, Player self, Player other, int attackingForces) {
        int thirdOfArmy = self.getResource(Game.Resources.MILITARY)/3;
        return new WarReaction(new ThreeTupleInt(thirdOfArmy,thirdOfArmy,thirdOfArmy), Reaction.Status.OK);
    }
}
