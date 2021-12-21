package gameengine.actions;

import gameengine.Game;
import gameengine.Player;
import gameengine.actions.reactions.Reaction;
import gameengine.events.AbstractEvent;
import gameengine.events.CountdownEvent;
import gameengine.history.HistoricalAction;
import gameengine.history.footnotes.Recruit;

import util.Tuple2;
import util.log.Logger;
import util.log.NamedLogger;

import java.util.List;

public class RecruitAction implements IRespondableAction<Reaction> {

    private int toRecruit;
    private Logger logger;

    public RecruitAction(int toRectuit){
        this.logger = new NamedLogger("RECRUIT");
        this.toRecruit = toRectuit;
    }

    @Override
    public String getInfo() {
        return "recruit [amount] | Converts the given amount into military, use negative numbers to covert back to citizen";
    }

    @Override
    public String getName() {
        return "recruit";
    }

    @Override
    public IRespondableAction<Reaction> parse(Game game, List<String> data) {
        if (data.size() != 1)
        return new ErrorAction("Expected 1 argument, received " + data.size());

        try {
            int a = Integer.parseInt(data.get(0));
            return new RecruitAction(a);
        } catch (NumberFormatException e){
            return new ErrorAction("Expected integer argument, got '" + data.get(0) + "'");
        }
    }

    @Override
    public Tuple2<Boolean, Tuple2<Player, List<String>>> execute(Game game, Player actor) {
        if (actor.getResource(Game.Resources.POPULATION.ordinal()) - toRecruit < 0
        || actor.getResource(Game.Resources.MILITARY.ordinal()) + toRecruit < 0 || !actor.canConvert)
            return new Tuple2<>(false, null);

        AbstractEvent ev = new CountdownEvent(game, 2) {
            @Override
            protected void action(Game game) {
                actor.subtractResource(Game.Resources.POPULATION.ordinal(), toRecruit);
                actor.addResource(Game.Resources.MILITARY.ordinal(), toRecruit);
                actor.canConvert = true;
            }
        };
        actor.canConvert = false;
        ev.initialize();
        game.addEvent(ev);

        logger.info(String.format("%s recruited %d military", actor.getName(), toRecruit));

        return new Tuple2<>(true, null);
    }

    @Override
    public boolean validateResponse(Reaction reaction) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public boolean executeWithResponse(Game game, Player actor, Player reactor, Reaction reaction) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Reaction defaultBotResponse() {
        return null;
    }

    @Override
    public HistoricalAction generateChronicle(Game game, Player actor, Player reactor, Reaction reaction) {
        return new Recruit(actor, toRecruit);
    }
}
