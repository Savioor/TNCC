package game.actions;

import game.Game;
import game.Player;
import game.actions.reactions.Reaction;
import game.actions.reactions.WarReaction;
import game.history.HistoricalAction;
import game.history.footnotes.War;
import util.Tuple2;
import util.Tuple3Int;
import util.log.Logger;
import util.log.NamedLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WarAction implements IRespondableAction<WarReaction> {

    private Player attackedPlayer;
    private Tuple3Int attackingForcesDivision;
    private Logger logger;

    public WarAction(Player attacked, Tuple3Int attackingForcesDivision) {
        this.attackedPlayer = attacked;
        this.attackingForcesDivision = attackingForcesDivision;
        this.logger = new NamedLogger("WAR");
    }

    @Override
    public String getInfo() {
        return "attack [payer name/ID] [left flank] [front flank] [right flank]";
    }

    @Override
    public String getName() {
        return "attack";
    }

    @Override
    public IRespondableAction parse(Game game, List<String> data) {
        if (data.size() != 4)
            return new ErrorAction("Expected 4 arguments, received " + data.size());

        Player attacked = game.getPlayerByNameOrId(data.get(0));
        if (attacked == null)
            return new ErrorAction("Player " + data.get(0) + " could not be found");
        Tuple3Int attackingForces;
        try{
            attackingForces = new Tuple3Int(Integer.parseInt(data.get(1)), Integer.parseInt(data.get(2)), Integer.parseInt(data.get(3)));
        } catch (NumberFormatException e){
            return new ErrorAction("Arguments 1-3 was expected to be integers, got " + Arrays.toString(data.subList(1, 3).toArray()));
        }

        return new WarAction(attacked, attackingForces);
    }

    @Override
    public Tuple2<Boolean, Tuple2<Player, List<String>>> execute(Game game, Player actor) {
        if (attackingForcesDivision.size() != 3) return new Tuple2<>(false, null);
        int sum = 0;
        for (Integer I : attackingForcesDivision){
            if (I < 0) return new Tuple2<>(false, null);
            sum += I;
        }
        if (actor.getResource(Game.Resources.MILITARY) < sum) return new Tuple2<>(false, null);
        if (attackedPlayer.equals(actor)) return new Tuple2<>(false, null);
        if (!attackedPlayer.isAlive()) return new Tuple2<>(false, null);
        if (actor.getResource(Game.Resources.GOLD) < game.getConsts().goldForWar) return new Tuple2<>(false, null);

        List<String> message = new ArrayList<>();
        message.add("attack");
        message.add(actor.getName());
        message.add(Integer.toString(sum));

        return new Tuple2<>(true, new Tuple2<>(attackedPlayer, message));
    }

    @Override
    public boolean validateResponse(WarReaction reaction) {
        int tempSum = 0;
        if (reaction.getStatus().equals(Reaction.Status.OK)){
            boolean failed = false;
            for (Integer i : reaction.getReaction()){
                if (i < 0) failed = true;
                tempSum += i;
            }
            if (attackedPlayer.getResource(Game.Resources.MILITARY) < tempSum) failed = true;
            if (!failed)
                return true;
        }
        return false;
    }

    @Override
    public boolean executeWithResponse(Game game, Player actor, Player reactor, WarReaction reaction) {

        logger.info(String.format("%s attacked %s with (%d, %d, %d). %s defended with (%d, %d, %d).",
                actor.getName(), attackedPlayer.getName(), attackingForcesDivision.first, attackingForcesDivision.second,
                attackingForcesDivision.third, attackedPlayer.getName(), reaction.getReaction().first,
                reaction.getReaction().second, reaction.getReaction().third));

        int sum = attackingForcesDivision.sum();

        int tempSum = reaction.getReaction().sum();

        reactor.subtractResource(Game.Resources.MILITARY, tempSum);
        actor.subtractResource(Game.Resources.MILITARY, sum);
        actor.subtractResource(Game.Resources.GOLD, (int)game.getConsts().goldForWar);

        int tempAttacking, tempDefending;
        for (int i = 0; i < 3; i++){
            tempAttacking = attackingForcesDivision.get(i);
            tempDefending = reaction.getReaction().get(i);
            attackingForcesDivision.set(i, Math.max(0, (int)(attackingForcesDivision.get(i) - game.getConsts().defendingWave1Multiplier*tempDefending)));
            reaction.getReaction().set(i, (int)Math.max(Math.min(game.getConsts().defendingWave1Multiplier*tempDefending - tempAttacking, tempDefending), 0));
    }

        int sumAttacking = 0, sumDefending = 0;
        for (int i = 0; i < 3; i++){
            sumAttacking += attackingForcesDivision.get(i);
            sumDefending += reaction.getReaction().get(i);
        }

        Player winner, loser;
        int remainingMilitary;

        if (game.getConsts().attackingWave2Multiplier*sumAttacking > sumDefending){
            winner = actor;
            loser = reactor;
            remainingMilitary = (int)Math.min(game.getConsts().attackingWave2Multiplier*sumAttacking - sumDefending, sumAttacking);
        } else if (game.getConsts().attackingWave2Multiplier*sumAttacking < sumDefending) {
            winner = reactor;
            loser = actor;
            remainingMilitary = (int)(sumDefending - game.getConsts().attackingWave2Multiplier*sumAttacking);
        } else {
            logger.info("There was a tie");
            return true; // Tie
        }

        int subtracted;
        for (Game.Resources res : Game.Resources.values()){
            subtracted = (int)(remainingMilitary * game.getConsts().getStealingFactor(res));
            if (loser.getResource(res) < subtracted) {
                subtracted = loser.getResource(res);
            }
            loser.subtractResource(res, subtracted);
            winner.addResource(res, subtracted);
        }

        winner.addResource(Game.Resources.MILITARY, remainingMilitary);

        if (loser.getResource(Game.Resources.POPULATION) <= 0 || loser.getLand() <= 0)
            loser.setAlive(false);

        logger.info(String.format("%s won with %d remaining.",
                winner.getName(), remainingMilitary));

        return true;
    }

    @Override
    public WarReaction defaultBotResponse() {
        return new WarReaction(new Tuple3Int(0,0,0), Reaction.Status.OK);
    }

    @Override
    public HistoricalAction generateChronicle(Game game, Player actor, Player reactor, WarReaction reaction) {
        return new War(actor, reactor, attackingForcesDivision.sum(), reaction.getReaction().sum());
    }
}
