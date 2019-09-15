package bots;

import bottools.Bot;
import bottools.GameWrapper;
import game.Game;
import game.Player;
import game.actions.IRespondableAction;
import game.actions.RecruitAction;
import game.actions.WaitAction;
import game.actions.WarAction;
import util.Tuple3Int;

import java.util.Random;

public class BlitzRank2Bot extends Bot {

    private BlitzBot babyBlitzBot = new BlitzBot();
    private TurtleBot babyTurtleBot = new TurtleBot();
    private static final double CRITICAL_RATIO = 0.7;
    private boolean waited = false;
    private String blitzer = "";
    private Random random;

    public BlitzRank2Bot(){
        random = new Random();
    }

    // Plan of action:
    // Wait 1 extra turn
    @Override
    public IRespondableAction getBotAction(GameWrapper game, Player self) {
        double popGoldPord = game.getConsts().populationGoldProduction;
        double armyGoldUse = game.getConsts().armyGoldConsumption;
        double goldForArmy = game.getConsts().goldForWar;
        double largestArmyProportion = 0;
        Player largestArmyPorpOwner = null;
        for (Player p : game.getPlayers()) {
            if (p.getResource(Game.Resources.GOLD) + popGoldPord*p.getPopulation() - p.getMilitary()*armyGoldUse >= goldForArmy) {
                if (p.getName().equals(self.getName()) || !p.isAlive() || p.getMilitary() < self.getMilitary() + 200)
                    continue;
                if (p.getResource(Game.Resources.MILITARY)/((double)p.getTotalPopulation()) > largestArmyProportion) {
                    largestArmyProportion = p.getResource(Game.Resources.MILITARY) / ((double) p.getTotalPopulation());
                    largestArmyPorpOwner = p;
                }
            }
        }
        if (largestArmyProportion < CRITICAL_RATIO && blitzer.equals(""))
            return babyTurtleBot.getBotAction(game, self);

        blitzer = largestArmyPorpOwner.getName();

        if (!waited) {
            waited = true;
            return babyTurtleBot.getBotAction(game, self);
        }

        if (self.getPopulation() > 5) return new RecruitAction(self.getPopulation() - 1);
        if (2*game.getPlayerByNameOrId(blitzer).getMilitary() > self.getMilitary()) return new WaitAction();

        Tuple3Int forces = new Tuple3Int(0,0,0);
        forces.set(random.nextInt(3), self.getMilitary());

        waited = false;
        String temp = blitzer;
        blitzer = "";

        return new WarAction(game.getPlayerByNameOrId(temp), forces);
    }

    @Override
    public boolean acceptTrade(GameWrapper game, Player self, Player other, Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount) {
        return false;
    }

    @Override
    public Tuple3Int fightWar(GameWrapper game, Player self, Player other, int attackingForces) {
        return babyBlitzBot.fightWar(game, self, other, attackingForces);
    }

    @Override
    public void reset(GameWrapper game, Player self) {
        babyBlitzBot.reset(game, self);
        babyTurtleBot.reset(game, self);
    }
}
