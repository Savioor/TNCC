package botdata.bots;

import botdata.bottools.Bot;
import botdata.bottools.GameWrapper;
import gameengine.Game;
import gameengine.Player;
import gameengine.actions.IRespondableAction;
import gameengine.actions.RecruitAction;
import util.Tuple3Int;

public class TurtleBot extends Bot {
    @Override
    public IRespondableAction getBotAction(GameWrapper game, Player self) {
        double foodProd = game.getConsts().populationFoodProduction;
        double popGoldPord = game.getConsts().populationGoldProduction;
        double armyGoldUse = game.getConsts().armyGoldConsumption;
        double foodPerArmy = game.getConsts().armyFoodConsumption;
        double goldForArmy = game.getConsts().goldForWar;
        int largestArmy = 0;
        for (Player p : game.getPlayers()) {
            if (p.getResource(Game.Resources.GOLD) + popGoldPord*p.getPopulation() - p.getMilitary()*armyGoldUse >= goldForArmy) {
                if (p.getName().equals(self.getName()) || !p.isAlive())
                    continue;
                if (p.getResource(Game.Resources.MILITARY) > largestArmy)
                    largestArmy = p.getResource(Game.Resources.MILITARY);
            }
        }
        if (largestArmy == 0) {
            return new RecruitAction(-self.getResource(Game.Resources.MILITARY));
        }

        int minimumPop = (int) ((self.getTotalPopulation()*foodPerArmy) / (foodProd + foodPerArmy)) + 1;
        int toRecruit = Math.min(largestArmy - self.getResource(Game.Resources.MILITARY), self.getPopulation() - minimumPop);
        return new RecruitAction(toRecruit);
    }

    @Override
    public boolean acceptTrade(GameWrapper game, Player self, Player other, Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount) {
        return false;
    }

    @Override
    public Tuple3Int fightWar(GameWrapper game, Player self, Player other, int attackingForces) {
        int thirdOfArmy = self.getResource(Game.Resources.MILITARY) / 3;
        return new Tuple3Int(thirdOfArmy, thirdOfArmy, thirdOfArmy);
    }

    @Override
    public void reset(GameWrapper game, Player self) {

    }
}
