package botdata.bottools;

import gameengine.Game;
import gameengine.Player;
import gameengine.actions.IRespondableAction;
import gameengine.actions.action_getters.IActionGetter;
import gameengine.actions.action_getters.IReActionGetter;
import gameengine.actions.reactions.FailedReaction;
import gameengine.actions.reactions.Reaction;
import gameengine.actions.reactions.TradeReaction;
import gameengine.actions.reactions.WarReaction;
import util.log.Logger;
import util.log.NamedLogger;

import java.util.List;

public abstract class Bot implements IBot, IActionGetter, IReActionGetter {

    private GameWrapper wrapper;
    private TradeReaction dummyReactionTrade;
    private WarReaction dummyReactionWar;
    private Logger logger;

    private final void initData(Game game, String name){
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
    public final IRespondableAction getAction(Game game, Player player) {
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

    public final void updateBot(Game game, Player self){
        wrapper = null;
        initData(game, self.getName());
        reset(wrapper, self);
    }

    protected final Logger getLogger(){
        return logger;
    }
}
