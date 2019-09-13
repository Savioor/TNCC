package game.actions.reactions;

import game.Game;

import java.util.List;

public class TradeReaction extends Reaction<Boolean> {

    public TradeReaction(Boolean reaction, Status status) {
        super(reaction, status);
    }

    @Override
    public String getInfo() {
        return "[yes/no]";
    }

    @Override
    public String getName() {
        return "trade";
    }

    @Override
    public Reaction<Boolean> parse(Game game, List<String> data) {
        if (data.size() != 1)
            return new TradeReaction(false, Status.FAILED);
        String answer = data.get(0).toLowerCase();
        if (answer.equals("yes")){
            return new TradeReaction(true, Status.OK);
        } else if (answer.equals("no")){
            return new TradeReaction(false, Status.OK);
        }
        return new TradeReaction(false, Status.FAILED);
    }

}
