package game.actions.reactions;

import game.Game;

import java.util.ArrayList;
import java.util.List;

public class WarReaction extends Reaction<List<Integer>> {
    public WarReaction(List<Integer> reaction, Status status) {
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
    public Reaction<List<Integer>> parse(Game game, List<String> data) {
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
        return new WarReaction(flanks, Status.OK);
    }
}
