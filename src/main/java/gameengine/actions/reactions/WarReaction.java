package gameengine.actions.reactions;

import gameengine.Game;
import util.Tuple3Int;

import java.util.ArrayList;
import java.util.List;

public class WarReaction extends Reaction<Tuple3Int> {
    public WarReaction(Tuple3Int reaction, Status status) {
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
    public WarReaction parse(Game game, List<String> data) {
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
        return new WarReaction(new Tuple3Int(flanks.get(0), flanks.get(1), flanks.get(2)), Status.OK);
    }
}
