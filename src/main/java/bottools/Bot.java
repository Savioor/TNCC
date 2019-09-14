package bottools;

import game.Game;
import game.Player;
import game.actions.IAction;
import game.actions.action_getters.IActionGetter;
import game.actions.action_getters.IReActionGetter;
import game.actions.reactions.FailedReaction;
import game.actions.reactions.Reaction;
import game.actions.reactions.TradeReaction;
import game.actions.reactions.WarReaction;
import util.Logger;
import util.NamedLogger;

import java.util.List;

public abstract class Bot implements IBot, IActionGetter, IReActionGetter {

    private GameWrapper wrapper;
    private TradeReaction dummyReactionTrade;
    private WarReaction dummyReactionWar;
    private Logger logger;

    private void initData(Game game, String name){
        if(logger == null){
            logger = new NamedLogger(name);
        }
        if (wrapper == null) {
            wrapper = new GameWrapper(game);
            dummyReactionTrade = new TradeReaction(false, null);
            dummyReactionWar = new WarReaction(null, null);
        }
    }

    @Override
    public final IAction getAction(Game game, Player player) {
        initData(game, player.getName());
        return getBotAction(wrapper, player.getDummy());
    }

    @Override
    public final <T> Reaction<T> getReAction(List<String> action, Game game, Player player) {
        initData(game, player.getName());
        String reactionType = action.get(0);
        if (reactionType.equals(dummyReactionTrade.getName())){
            Player other = wrapper.getPlayerByNameOrId(action.get(1));
            Game.Resources getting = Game.Resources.valueOf(action.get(2));
            Game.Resources giving = Game.Resources.valueOf(action.get(3));
            int givingAmount = Integer.parseInt(action.get(4));
            int takingAmount = Integer.parseInt(action.get(5));
            return (Reaction<T>) new TradeReaction(acceptTrade(wrapper, player.getDummy(), other, getting, takingAmount, giving, givingAmount), Reaction.Status.OK);
        } else if (reactionType.equals(dummyReactionWar.getName())){
            Player attacker = wrapper.getPlayerByNameOrId(action.get(1));
            int attackingAmount = Integer.parseInt(action.get(2));
            return (Reaction<T>) new WarReaction(fightWar(wrapper, player.getDummy(), attacker, attackingAmount), Reaction.Status.OK);
        }
        return new FailedReaction<>();
    }

    protected final Logger getLogger(){
        return logger;
    }
}
