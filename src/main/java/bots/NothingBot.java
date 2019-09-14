package bots;

import bottools.Bot;
import bottools.GameWrapper;
import bottools.IBot;
import game.Game;
import game.Player;
import game.actions.IAction;
import game.actions.WaitAction;
import game.actions.WarAction;
import game.actions.reactions.Reaction;
import game.actions.reactions.TradeReaction;
import game.actions.reactions.WarReaction;
import util.ThreeTupleInt;

import java.util.ArrayList;
import java.util.List;

public class NothingBot extends Bot {

    @Override
    public IAction getBotAction(GameWrapper game, Player self) {
        return new WaitAction();
    }

    @Override
    public TradeReaction acceptTrade(GameWrapper game, Player self, Player other, Game.Resources getting, int gettingAmount, Game.Resources giving, int givingAmount) {
        return new TradeReaction(false, Reaction.Status.OK);
    }

    @Override
    public WarReaction fightWar(GameWrapper game, Player self, Player other, int attackingForces) {
        return new WarReaction(new ThreeTupleInt(0,0,0), Reaction.Status.OK);
    }
}
