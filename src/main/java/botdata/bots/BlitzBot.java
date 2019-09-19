package botdata.bots;

import botdata.bottools.Bot;
import botdata.bottools.GameWrapper;
import gameengine.Game;
import gameengine.Player;
import gameengine.actions.IRespondableAction;
import gameengine.actions.RecruitAction;
import gameengine.actions.WarAction;
import util.Tuple3Int;

import java.util.Random;

public class BlitzBot extends Bot {

    private static final double FOOD_SAFETY_FACTOR = 2;
    private static final double GOLD_SAFETY_FACTOR = 1;
    private Random random;
    private boolean determined;

    public BlitzBot(){
        random = new Random();
        determined = false;
    }

    @Override
    public IRespondableAction getBotAction(GameWrapper game, Player self) {

        double foodProd = game.getConsts().populationFoodProduction;
        double foodPerArmy = game.getConsts().armyFoodConsumption;
        double goldProd = game.getConsts().populationGoldProduction;
        double goldPerSoldier = game.getConsts().armyGoldConsumption;
        double goldForArmy = game.getConsts().goldForWar;
        if ((self.getGold() - self.getTotalPopulation()*goldPerSoldier < GOLD_SAFETY_FACTOR*goldForArmy
                || self.getFood() < FOOD_SAFETY_FACTOR*foodPerArmy*self.getTotalPopulation()) && !determined) {
            int largestArmy = 0;
            for (Player p : game.getPlayers()) {
                if (p.getResource(Game.Resources.GOLD) + p.getPopulation()*goldProd - p.getMilitary()*goldPerSoldier >= goldForArmy) {
                    if (p.getName().equals(self.getName()) || !p.isAlive())
                        continue;
                    if (p.getResource(Game.Resources.MILITARY) > largestArmy)
                        largestArmy = p.getResource(Game.Resources.MILITARY);
                }
            }
            if (largestArmy == 0) {
                return new RecruitAction(-self.getResource(Game.Resources.MILITARY));
            }

            Player smallestArmyOwner = game.getPlayers().get(0);
            for (Player p : game.getPlayers()) {
                if (p.getName().equals(self.getName()) || !p.isAlive())
                    continue;
                if (p.getResource(Game.Resources.MILITARY) < smallestArmyOwner.getMilitary())
                    smallestArmyOwner = p;

            }

            int minimumPop = (int) ((self.getTotalPopulation()*foodPerArmy) / (foodProd + foodPerArmy)) + 1;
            int toRecruit = Math.min(Math.min(largestArmy - self.getResource(Game.Resources.MILITARY),
                    self.getPopulation() - minimumPop),
                    smallestArmyOwner.getMilitary() - self.getMilitary() + 1);
            return new RecruitAction(toRecruit);
        }

        determined = true;

        if (self.getPopulation() > 5) return new RecruitAction(self.getPopulation() - 1);

        Player smallestArmyOwner = self;
        for (Player p : game.getPlayers()){
            if (p.isAlive() && !p.getName().equals(self.getName())) {
                smallestArmyOwner = p;
                break;
            }
        }
        for (Player p : game.getPlayers()) {
            if (p.getName().equals(self.getName()) || !p.isAlive())
                continue;
            if (p.getResource(Game.Resources.MILITARY) < smallestArmyOwner.getMilitary())
                smallestArmyOwner = p;
        }
        Tuple3Int forces = new Tuple3Int(0,0,0);
        forces.set(random.nextInt(3), self.getMilitary());

        determined = false;

        return new WarAction(smallestArmyOwner, forces);
    }

    @Override
    public boolean acceptTrade(GameWrapper game, Player self, Player other, Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount) {
        return false;
    }

    @Override
    public Tuple3Int fightWar(GameWrapper game, Player self, Player other, int attackingForces) {
        int thirdOfArmy = self.getResource(Game.Resources.MILITARY) / 3;
        int halfOfArmy = self.getMilitary() / 2;
        if (attackingForces <= game.getConsts().defendingWave1Multiplier*thirdOfArmy) {
            return new Tuple3Int(thirdOfArmy, thirdOfArmy, thirdOfArmy);
        } else {
            Tuple3Int defence = new Tuple3Int(halfOfArmy, halfOfArmy, halfOfArmy);
            defence.set(random.nextInt(3), 0);
            return defence;
        }
    }

    @Override
    public void reset(GameWrapper game, Player self) {
        determined = false;
    }
}
