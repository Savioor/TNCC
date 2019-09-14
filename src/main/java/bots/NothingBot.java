package bots;

import bottools.Bot;
import bottools.GameWrapper;
import game.Game;
import game.Player;
import game.actions.IRespondableAction;
import game.actions.WaitAction;
import util.Tuple3;

public class NothingBot extends Bot {

    @Override
    public IRespondableAction getBotAction(GameWrapper game, Player self) {
        return new WaitAction();
    }

    @Override
    public boolean acceptTrade(GameWrapper game, Player self, Player other, Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount) {
        return false;
    }

    @Override
    public Tuple3<Integer> fightWar(GameWrapper game, Player self, Player other, int attackingForces) {
        return new Tuple3<>(0,0,0);
    }

    @Override
    public void reset(GameWrapper game, Player self) {

    }
}
