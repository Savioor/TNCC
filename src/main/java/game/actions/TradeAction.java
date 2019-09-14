package game.actions;

import game.Game;
import game.Player;
import game.actions.reactions.Reaction;
import game.actions.reactions.TradeReaction;
import util.Tuple2;
import util.log.Logger;
import util.log.NamedLogger;

import java.util.ArrayList;
import java.util.List;

public class TradeAction implements IRespondableAction<TradeReaction> {

    private Game.Resources giving, taking;
    private int givingAmount, takingAmount;
    private Player secondActor;
    private Logger logger;


    @Override
    public String getInfo() {
        return "trade [with (name or ID)] [sell/buy] [resource name] [amount] [payment] | Trade with the other player";
    }

    @Override
    public String getName() {
        return "trade";
    }

    @Override
    public IRespondableAction parse(Game game, List<String> data) {
        if (data.size() != 5)
            return new ErrorAction("Expected 5 arguments, received " + data.size());
        String tradeWith = data.get(0);
        Player tradingWith = null;

        try {
            int ID = Integer.parseInt(tradeWith);
            if (ID >= game.getPlayers().size() || ID < 0)
                throw new NumberFormatException();
            tradingWith = game.getPlayers().get(ID);
        } catch (NumberFormatException e){
            for (Player p : game.getPlayers()){
                if (p.getName().equals(tradeWith)){
                    tradingWith = p;
                    break;
                }
            }
        }

        if (tradingWith == null)
            return new ErrorAction("Player '" + data.get(0) + "' could not be found");

        Game.Resources beingSold = null;
        String beingSoldString = data.get(2);
        for (Game.Resources res : Game.Resources.values()){
            if (beingSoldString.toLowerCase().equals(res.name().toLowerCase())){
                beingSold = res;
                break;
            }
        }
        if (beingSold == null)
            return new ErrorAction("Resource '" + data.get(2) + "' could not be found");

        int amount, payment;

        try{
            amount = Integer.parseInt(data.get(3));
        } catch (NumberFormatException e){
            return new ErrorAction("Argument 3 was expected to be integer, got " + data.get(3));
        }
        try{
            payment = Integer.parseInt(data.get(4));
        } catch (NumberFormatException e){
            return new ErrorAction("Argument 4 was expected to be integer, got " + data.get(4));
        }

        String status = data.get(1).toLowerCase();

        if (status.equals("sell")){
            return new TradeAction(beingSold, Game.Resources.GOLD, amount, payment, tradingWith);
        } else if (status.equals("buy")) {
            return new TradeAction(Game.Resources.GOLD, beingSold, payment, amount, tradingWith);
        }
        return new ErrorAction("Buying status '" + data.get(1) + "' not valid");
    }

    @Override
    public Tuple2<Boolean, Tuple2<Player, List<String>>> execute(Game game, Player actor) {
        List<String> message = new ArrayList<>();

        if (actor.equals(secondActor))
            return new Tuple2<>(false, null);
        if (!secondActor.isAlive())
            return new Tuple2<>(false, null);;
        if (givingAmount < 0 || takingAmount < 0)
            return new Tuple2<>(false, null);;
        if (!(giving.equals(Game.Resources.GOLD) || taking.equals(Game.Resources.GOLD)))
            return new Tuple2<>(false, null);;
        if (actor.getResource(giving) - givingAmount < 0)
            return new Tuple2<>(false, null);;
        if (secondActor.getResource(taking) - takingAmount < 0)
            return new Tuple2<>(false, null);;

        message.add("trade");
        message.add(actor.getName());
        message.add(taking.name());
        message.add(giving.name());
        message.add(Integer.toString(takingAmount));
        message.add(Integer.toString(givingAmount));

        return new Tuple2<>(true, new Tuple2<>(secondActor, message));
    }

    public TradeAction(Game.Resources giving, Game.Resources taking, int givingAmount, int takingAmount, Player secondActor) {
        this.giving = giving;
        this.taking = taking;
        this.givingAmount = givingAmount;
        this.takingAmount = takingAmount;
        this.secondActor = secondActor;
        this.logger = new NamedLogger("TRADE");
    }

    @Override
    public boolean validateResponse(TradeReaction reaction) {
        return reaction.getStatus().equals(Reaction.Status.OK) && reaction.getReaction() != null;
    }

    @Override
    public boolean executeWithResponse(Game game, Player actor, Player reactor, TradeReaction reaction) {
        if (!reaction.getReaction()) {
            logger.info(String.format("%s refused trade of %d %s for %d %s from %s",
                    reactor.getName(), givingAmount, giving.name(), takingAmount, taking.name(), actor.getName()));
            return true;
        }

        actor.subtractResource(giving, givingAmount);
        reactor.addResource(giving, givingAmount);
        actor.addResource(taking, takingAmount);
        reactor.subtractResource(taking, takingAmount);

        logger.info(String.format("%s accepted trade of %d %s for %d %s from %s",
                reactor.getName(), givingAmount, giving.name(), takingAmount, taking.name(), actor.getName()));
        return true;
    }

    @Override
    public TradeReaction defaultBotResponse() {
        return new TradeReaction(true, Reaction.Status.OK);
    }
}
