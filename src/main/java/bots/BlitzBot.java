package bots;

import bottools.Bot;
import bottools.GameWrapper;
import game.Game;
import game.Player;
import game.actions.IAction;
import game.actions.reactions.Reaction;
import game.actions.reactions.TradeReaction;
import game.actions.reactions.WarReaction;
import util.ThreeTupleInt;

import java.util.ArrayList;
import java.util.List;

public class BlitzBot extends Bot {
    @Override
    public IAction getBotAction(GameWrapper game, Player self) {
        return null;
    }

    @Override
    public TradeReaction acceptTrade(GameWrapper game, Player self, Player other, Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount) {
        return new TradeReaction(false, Reaction.Status.OK);
    }

    @Override
    public WarReaction fightWar(GameWrapper game, Player self, Player other, int attackingForces) {
        int thirdOfArmy = self.getResource(Game.Resources.MILITARY)/3;
        return new WarReaction(new ThreeTupleInt(thirdOfArmy,thirdOfArmy,thirdOfArmy), Reaction.Status.OK);
    }
}
