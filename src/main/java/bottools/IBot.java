package bottools;

import game.Game;
import game.Player;

import game.actions.IRespondableAction;
import util.Tuple3Int;

public interface IBot {

    IRespondableAction getBotAction(GameWrapper game, Player self);

    /**
     *
     * @param game
     * @param self
     * @param other
     * @param getting
     * @param gettingAmount
     * @param giving
     * @param givingAmount
     * @return true of false for accepting or denying trade
     */
    boolean acceptTrade(GameWrapper game, Player self, Player other,
                                        Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount);

    /**
     *
     * @param game
     * @param self
     * @param other
     * @param attackingForces The total amount of army fighting you
     * @return Three tuple of left flank, front and right flank
     */
    Tuple3Int fightWar(GameWrapper game, Player self, Player other, int attackingForces);
    void reset(GameWrapper game, Player self);

}
