package game;

import java.util.HashMap;

public class GameConstants {

    public final double populationFoodProduction,
            populationGoldProduction,
            armyFoodConsumption,
            armyGoldConsumption,
            populationGrowth,
            landCapacity,
            goldForWar,
            defendingWave1Multiplier,
            attackingWave2Multiplier;

    public final int maxTurns;

    private HashMap<Game.Resources, Integer> startingValues = new HashMap<>();
    private HashMap<Game.Resources, Double> stealingFactor = new HashMap<>();

    public int getStartingValue(Game.Resources res){
        return startingValues.get(res);
    }

    public double getStealingFactor(Game.Resources res){
        return stealingFactor.get(res);
    }

    public GameConstants() {
        startingValues.put(Game.Resources.GOLD, 600);
        startingValues.put(Game.Resources.LAND, 150);
        startingValues.put(Game.Resources.POPULATION, 1000);
        startingValues.put(Game.Resources.MILITARY, 0);
        startingValues.put(Game.Resources.FOOD, 800);

        stealingFactor.put(Game.Resources.GOLD, 1.0);
        stealingFactor.put(Game.Resources.LAND, .2);
        stealingFactor.put(Game.Resources.POPULATION, .5);
        stealingFactor.put(Game.Resources.MILITARY, 0.0);
        stealingFactor.put(Game.Resources.FOOD, 2.0);

        populationFoodProduction = 0.5;
        populationGoldProduction = 0.25;
        armyFoodConsumption = 1;
        armyGoldConsumption = 0.1;
        populationGrowth = 1.08;
        landCapacity = 10;
        goldForWar = 1600;
        defendingWave1Multiplier = 2;
        attackingWave2Multiplier = 2;
        maxTurns = 1000;
    }

}
