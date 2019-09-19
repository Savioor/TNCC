package gameengine.actions;

import gameengine.Game;
import gameengine.Player;
import gameengine.actions.reactions.Reaction;
import gameengine.history.HistoricalAction;
import util.Tuple2;

import java.util.List;

public interface IRespondableAction<T extends Reaction> {

    String getInfo();
    String getName();
    IRespondableAction<T> parse(Game game, List<String> data);

    Tuple2<Boolean, Tuple2<Player, List<String>>> execute(Game game, Player actor);

    boolean validateResponse(T reaction);
    boolean executeWithResponse(Game game, Player actor, Player reactor, T reaction);
    T defaultBotResponse();

    HistoricalAction generateChronicle(Game game, Player actor, Player reactor, T reaction);

}
