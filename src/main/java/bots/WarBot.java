package bots;

import bottools.Bot;
import bottools.GameWrapper;
import game.Game;
import game.Player;
import game.actions.IRespondableAction;
import game.actions.RecruitAction;
import game.actions.WaitAction;
import game.actions.WarAction;
import game.actions.reactions.Reaction;
import util.Tuple3;

import java.util.List;
import java.util.Random;

public class WarBot extends Bot {
    Random rand;
    public WarBot(){
        super();
        rand = new Random();
    }

    @Override
    public IRespondableAction<? extends Reaction> getBotAction(GameWrapper game, Player self) {
        if(self.getResource(Game.Resources.MILITARY) != 0 && self.canGoToWar(game)) {
            List<Player> playerList = game.getPlayers();
            Player attacked = null;
            while (attacked == null) {
                attacked = playerList.get(rand.nextInt(playerList.size()));
                if (attacked == self) {
                    attacked = null;
                }
            }
            int thirdOfArmy = self.getResource(Game.Resources.MILITARY) / 3;
            return new WarAction(attacked, new Tuple3<>(thirdOfArmy, thirdOfArmy, thirdOfArmy));
        }
        else if (self.getMilitary() <= 0){
            return new RecruitAction(self.getResource(Game.Resources.POPULATION) / 2);
        }
        else{
            return new WaitAction();
        }
    }

    @Override
    public boolean acceptTrade(GameWrapper game, Player self, Player other, Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount) {
        return false;
    }

    @Override
    public Tuple3<Integer> fightWar(GameWrapper game, Player self, Player other, int attackingForces) {
        int thirdOfArmy = self.getResource(Game.Resources.MILITARY) / 3;
        return new Tuple3<>(thirdOfArmy, thirdOfArmy, thirdOfArmy);
    }
}
