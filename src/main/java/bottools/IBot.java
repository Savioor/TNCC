package bottools;

import game.Game;
import game.Player;

import game.actions.IRespondableAction;
import util.Tuple3;

public interface IBot {

    IRespondableAction getBotAction(GameWrapper game, Player self);
    boolean acceptTrade(GameWrapper game, Player self, Player other,
                                        Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount);
    Tuple3<Integer> fightWar(GameWrapper game, Player self, Player other, int attackingForces);

}
