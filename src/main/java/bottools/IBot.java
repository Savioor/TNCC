package bottools;

import game.Game;
import game.Player;
import game.actions.IAction;

import util.ThreeTupleInt;

public interface IBot {

    IAction getBotAction(GameWrapper game, Player self);
    boolean acceptTrade(GameWrapper game, Player self, Player other,
                                        Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount);
    ThreeTupleInt fightWar(GameWrapper game, Player self, Player other, int attackingForces);

}
