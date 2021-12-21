package game;

import java.util.HashMap;

public class GameConstants {

    public enum ConstantsGroup{
        DEFAULT,
        TRIBE,
        CRAZY,
        ROBOT_ARMY,
        MEXICAN_STANDOFF,
        DECAY
    }

    public final double populationFoodProduction,
            populationGoldProduction,
            armyFoodConsumption,
            armyGoldConsumption,
            populationGrowth,
            landCapacity,
            goldForWar,
            defendingWave1Multiplier, // Has to be >=1
            attackingWave2Multiplier; // Has to be >= 1

    public final int maxTurns, hungerDeathRate;

    private HashMap<Game.Resources, Integer> startingValues = new HashMap<>();
    private HashMap<Game.Resources, Double> stealingFactor = new HashMap<>();

    public int getStartingValue(Game.Resources res){
        return startingValues.get(res);
    }

    public double getStealingFactor(Game.Resources res){
        return stealingFactor.get(res);
    }

    public GameConstants(ConstantsGroup type){
        switch (type){
            case DECAY:
                startingValues.put(Game.Resources.GOLD, 600);
                startingValues.put(Game.Resources.LAND, 1500);
                startingValues.put(Game.Resources.POPULATION, 15000);
                startingValues.put(Game.Resources.MILITARY, 0);
                startingValues.put(Game.Resources.FOOD, 2000);

                stealingFactor.put(Game.Resources.GOLD, 1.0);
                stealingFactor.put(Game.Resources.LAND, .2);
                stealingFactor.put(Game.Resources.POPULATION, .5);
                stealingFactor.put(Game.Resources.MILITARY, 0.0);
                stealingFactor.put(Game.Resources.FOOD, 2.0);

                populationFoodProduction = -0.1;
                populationGoldProduction = 0.01;
                armyFoodConsumption = 1;
                armyGoldConsumption = 0.1;
                populationGrowth = 1;
                landCapacity = 10;
                goldForWar = 1600;
                defendingWave1Multiplier = 2;
                attackingWave2Multiplier = 2;
                hungerDeathRate = 200;
                maxTurns = 100;
                break;
            case CRAZY:
                startingValues.put(Game.Resources.GOLD, 6000);
                startingValues.put(Game.Resources.LAND, 1500);
                startingValues.put(Game.Resources.POPULATION, 10000);
                startingValues.put(Game.Resources.MILITARY, 0);
                startingValues.put(Game.Resources.FOOD, 8000);

                stealingFactor.put(Game.Resources.GOLD, 5.0);
                stealingFactor.put(Game.Resources.LAND, 1.0);
                stealingFactor.put(Game.Resources.POPULATION, 2.5);
                stealingFactor.put(Game.Resources.MILITARY, 0.0);
                stealingFactor.put(Game.Resources.FOOD, 10.0);

                populationFoodProduction = 2;
                populationGoldProduction = 1;
                armyFoodConsumption = 1;
                armyGoldConsumption = 0.1;
                populationGrowth = 1.2;
                landCapacity = 20;
                goldForWar = 10000;
                defendingWave1Multiplier = 2;
                attackingWave2Multiplier = 2;
                hungerDeathRate = 200;

                maxTurns = 100;
                break;
            case MEXICAN_STANDOFF:
                startingValues.put(Game.Resources.GOLD, 50000);
                startingValues.put(Game.Resources.LAND, 150);
                startingValues.put(Game.Resources.POPULATION, 1);
                startingValues.put(Game.Resources.MILITARY, 1499);
                startingValues.put(Game.Resources.FOOD, 1500);

                stealingFactor.put(Game.Resources.GOLD, 2.0);
                stealingFactor.put(Game.Resources.LAND, .05);
                stealingFactor.put(Game.Resources.POPULATION, 1.0);
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
                hungerDeathRate = 200;

                maxTurns = 100;
                break;
            case ROBOT_ARMY:
                startingValues.put(Game.Resources.GOLD, 600);
                startingValues.put(Game.Resources.LAND, 200);
                startingValues.put(Game.Resources.POPULATION, 1000);
                startingValues.put(Game.Resources.MILITARY, 0);
                startingValues.put(Game.Resources.FOOD, 800);

                stealingFactor.put(Game.Resources.GOLD, 1.0);
                stealingFactor.put(Game.Resources.LAND, .2);
                stealingFactor.put(Game.Resources.POPULATION, .5);
                stealingFactor.put(Game.Resources.MILITARY, 0.0);
                stealingFactor.put(Game.Resources.FOOD, 2.0);

                populationFoodProduction = 0.01;
                populationGoldProduction = 0.25;
                armyFoodConsumption = 0;
                armyGoldConsumption = 1;
                populationGrowth = 1.02;
                landCapacity = 10;
                goldForWar = 2000;
                defendingWave1Multiplier = 1;
                attackingWave2Multiplier = 1;
                hungerDeathRate = 200;

                maxTurns = 100;
                break;
            case TRIBE:
                startingValues.put(Game.Resources.GOLD, 50);
                startingValues.put(Game.Resources.LAND, 50);
                startingValues.put(Game.Resources.POPULATION, 100);
                startingValues.put(Game.Resources.MILITARY, 10);
                startingValues.put(Game.Resources.FOOD, 400);

                stealingFactor.put(Game.Resources.GOLD, 1.0);
                stealingFactor.put(Game.Resources.LAND, .2);
                stealingFactor.put(Game.Resources.POPULATION, .5);
                stealingFactor.put(Game.Resources.MILITARY, 0.0);
                stealingFactor.put(Game.Resources.FOOD, 2.0);

                populationFoodProduction = 1;
                populationGoldProduction = 0.1;
                armyFoodConsumption = 1;
                armyGoldConsumption = 0.1;
                populationGrowth = 1.05;
                landCapacity = 10;
                goldForWar = 1000;
                defendingWave1Multiplier = 2;
                attackingWave2Multiplier = 2;
                hungerDeathRate = 200;

                maxTurns = 200;
                break;
            default:
                startingValues.put(Game.Resources.GOLD, 600);
                startingValues.put(Game.Resources.LAND, 150);
                startingValues.put(Game.Resources.POPULATION, 800);
                startingValues.put(Game.Resources.MILITARY, 0);
                startingValues.put(Game.Resources.FOOD, 800);

                stealingFactor.put(Game.Resources.GOLD, 0.5);
                stealingFactor.put(Game.Resources.LAND, .025);
                stealingFactor.put(Game.Resources.POPULATION, 0.0);
                stealingFactor.put(Game.Resources.MILITARY, 0.0);
                stealingFactor.put(Game.Resources.FOOD, 2.0);

                populationFoodProduction = 0.5;
                populationGoldProduction = 0.25;
                armyFoodConsumption = .8;
                armyGoldConsumption = .2;
                populationGrowth = 1.06;
                landCapacity = 10;
                goldForWar = 1600;
                defendingWave1Multiplier = 2;
                attackingWave2Multiplier = 1.9;
                hungerDeathRate = 200;

                maxTurns = 100;
                break;
        }
    }


}
