package gameengine.events;

import gameengine.Game;
import gameengine.Player;
import gameengine.actions.RecruitAction;
import util.log.Logger;
import util.log.NamedLogger;


public class ProductionEvent extends AbstractEvent {

    private Logger logger;

    public ProductionEvent(Game game) {
        super(game);
        logger = new NamedLogger("PRODUCTION");
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        for (Player p : game.getPlayers()){
            if (p.isAlive()){
                p.addResource(Game.Resources.FOOD.ordinal(), (int)(game.getConsts().populationFoodProduction *
                        p.getResource(Game.Resources.POPULATION.ordinal())));
                p.addResource(Game.Resources.GOLD.ordinal(), (int)(game.getConsts().populationGoldProduction *
                        p.getResource(Game.Resources.POPULATION.ordinal())));
                p.setResource(Game.Resources.POPULATION.ordinal(),
                        (int)(p.getResource(Game.Resources.POPULATION.ordinal()) * game.getConsts().populationGrowth));

                if (p.getTotalPopulation() >
                p.getLand() * game.getConsts().landCapacity){
                    p.setResource(Game.Resources.POPULATION,(int)(p.getLand() * game.getConsts().landCapacity - p.getMilitary()));
                }
                
                p.subtractResource(Game.Resources.FOOD.ordinal(),
                        (int)(p.getResource(Game.Resources.MILITARY.ordinal())*game.getConsts().armyFoodConsumption));
                p.subtractResource(Game.Resources.GOLD.ordinal(),
                        (int)(p.getResource(Game.Resources.MILITARY.ordinal())*game.getConsts().armyGoldConsumption));

                if (p.getResource(Game.Resources.GOLD.ordinal()) <= 0){
                    p.setResource(Game.Resources.GOLD.ordinal(), 0);
                    new RecruitAction(-p.getResource(Game.Resources.MILITARY.ordinal())).execute(game, p);
                }

                if (p.getResource(Game.Resources.FOOD.ordinal()) <= 0){
                    p.setResource(Game.Resources.FOOD.ordinal(), 0);
                    p.subtractResource(Game.Resources.POPULATION.ordinal(), game.getConsts().hungerDeathRate);
                }

                if (p.getResource(Game.Resources.POPULATION.ordinal()) <= 0){
                    p.setAlive(false);
                    logger.info(p.getName() + " lost the game");
                }

            }
        }
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
