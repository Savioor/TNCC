package botdata.bots;

import botdata.bottools.Bot;
import botdata.bottools.GameWrapper;
import gameengine.Game;
import gameengine.Player;
import gameengine.actions.IRespondableAction;
import gameengine.actions.WaitAction;
import util.Tuple3Int;

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
    public Tuple3Int fightWar(GameWrapper game, Player self, Player other, int attackingForces) {
        return new Tuple3Int(0,0,0);
    }

    @Override
    public void reset(GameWrapper game, Player self) {

    }
}
