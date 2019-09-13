package bottools;

import game.Game;
import game.Player;
import game.actions.IAction;
import game.actions.reactions.Reaction;
import game.actions.reactions.TradeReaction;
import game.actions.reactions.WarReaction;

public interface IBot {

    IAction getBotAction(GameWrapper game, Player self);
    TradeReaction acceptTrade(GameWrapper game, Player self, Player other,
                                        Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount);
    WarReaction fightWar(GameWrapper game, Player self, Player other, int attackingForces);

}
