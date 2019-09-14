package game.actions.reactions;

import game.Game;
import util.Tuple3;

import java.util.ArrayList;
import java.util.List;

public class WarReaction extends Reaction<Tuple3> {
    public WarReaction(Tuple3 reaction, Status status) {
        super(reaction, status);
    }

    @Override
    public String getInfo() {
        return "[left flank] [front flank] [right flank]";
    }

    @Override
    public String getName() {
        return "attack";
    }

    @Override
    public Reaction<Tuple3> parse(Game game, List<String> data) {
        if (data.size() != 3)
            return new WarReaction(null, Status.FAILED);
        List<Integer> flanks = new ArrayList<>();
        for (String datum : data) {
            try {
                flanks.add(Integer.parseInt(datum));
            } catch (NumberFormatException e) {
                return new WarReaction(null, Status.FAILED);
            }
        }
        return new WarReaction(new Tuple3(flanks.get(0), flanks.get(1), flanks.get(2)), Status.OK);
    }
}
