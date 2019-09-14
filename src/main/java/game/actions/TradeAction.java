package game.actions;

import game.Game;
import game.Player;
import game.actions.reactions.Reaction;

import java.util.ArrayList;
import java.util.List;

public class TradeAction implements IAction {

    private Game.Resources giving, taking;
    private int givingAmount, takingAmount;
    private String secondActorName;


    @Override
    public String getInfo() {
        return "trade [with (name or ID)] [sell/buy] [resource name] [amount] [payment] | Trade with the other player";
    }

    @Override
    public String getName() {
        return "trade";
    }

    @Override
    public IAction parse(Game game, List<String> data) {
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
    public final ActionInfo execute(Game game, Player actor) {
        Player secondActor = game.getPlayerByName(secondActorName);
        if (actor.equals(secondActor))
            return new ActionInfo(false, "trying to trade with self");
        if (!secondActor.isAlive())
            return new ActionInfo(false, "trying to trade with dead actor: " + secondActor);
        if (givingAmount < 0 || takingAmount < 0)
            return new ActionInfo(false, "trying to trade negative amount: giving => " + givingAmount + " & taking => " + takingAmount);
        if (!(giving.equals(Game.Resources.GOLD) || taking.equals(Game.Resources.GOLD)))
            return new ActionInfo(false, "trying to trade " + giving + " for " + taking + ", but none of those resources is gold");
        if (actor.getResource(giving) - givingAmount < 0)
            return new ActionInfo(false, "trying to give " + givingAmount + ", but has only " + actor.getResource(giving));
        if (secondActor.getResource(taking) - takingAmount < 0)
            return new ActionInfo(false, "trying to take " + takingAmount + ", but " + secondActor + " has only " + secondActor.getResource(taking));

        List<String> message = new ArrayList<>();
        message.add("trade");
        message.add(actor.getName());
        message.add(taking.name());
        message.add(giving.name());
        message.add(Integer.toString(takingAmount));
        message.add(Integer.toString(givingAmount));

        Reaction<Boolean> reaction;
        while (true){
            reaction = secondActor.getReaction(message, game);
            if (reaction.getStatus().equals(Reaction.Status.OK))
                break;
        }

        if (!reaction.getReaction())
            return new ActionInfo(true, secondActorName + " rejected trade from " + actor);

        actor.subtractResource(giving, givingAmount);
        secondActor.addResource(giving, givingAmount);
        actor.addResource(taking, takingAmount);
        secondActor.subtractResource(taking, takingAmount);
        return new ActionInfo(true, secondActorName + " accepted trade from " + actor);
    }

    public TradeAction(Game.Resources giving, Game.Resources taking, int givingAmount, int takingAmount, Player secondActor) {
        this.giving = giving;
        this.taking = taking;
        this.givingAmount = givingAmount;
        this.takingAmount = takingAmount;
        this.secondActorName = secondActor.getName();
    }
}
