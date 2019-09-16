# TNCC

TNCC is a strategy game currently in development.

## Rules
In the game you control a country. Each country has 5 resources. The game is turn based, and each turn you can do 
one of 4 actions. When everybody does their turn once a _cycle_ is finished.
### Resources
#### Gold
Gold is consumed by military and produced by citizen. If you run out of gold your entire army will
automatically convert to citizen. In addition spending gold is required to go to war. Gold is also useful
for trading.
#### Food
Food is consumed by military and produced by citizen. When you are out of food, your citizen start
dying.
#### Citizen/Population
Citizens reproduce automatically. They also produce gold and food. When you are out of citizen you lose
the game. Can be converted into military
#### Army/Military/Soldiers
Army is used when attacking other countries and defending your own. Can be converted to citizen.
#### Land
Defines the cap of the _total population_ (i.e. citizen + military) in your country. Your citizen won't
reproduce if they don't have enough land.
### Cycle
When a cycle finishes, all of the citizen produce gold and food. The army consumes the gold and food, and the 
citizen will reproduce. Alongside that, if you are out of food you citizen will die each cycle.
### Actions
#### Wait
You skip your turn
#### Recruit
You convert some of your citizen to army. You can also use this action with negative numbers to convert
army into citizen. This action is executed instantly (as opposed to at the end of the cycle).
#### Trade
Offer an exchange of resources with another player. One of the resources in the exchange must be
gold. If the other player agrees then the transaction is performed, otherwise you skip your turn. 
This action is executed instantly.
#### War
When declaring war both you (the attacker) divide your army between three flanks (you can leave some behind.
Any army unit left behind won't participate in the war and can't die). When the person you attack receives the attack
they also divide the army between three flanks, when doing so the defender doesn't know how the attacked
divided his forces but he does know the total amount of army units attacking him. Of course, like the attacker
the defender can also leave army units outside the battle and those units won't participate.

After this, the battle is fought in two waves.
##### Wave One
The army of the attacker and defender fight each other, but only in their respective flanks. In this wave
the defenders have the advantage. Each attacking soldier kills one defending soldier and each defending soldier kills `advatange`
attacking soldiers. Note that `advantage` may be non whole, e.g. when `advantage = 1.5` every two defenders
will kill three attackers.
##### Wave Two
All the survivors from each side gather and now the attackers have the advantage. Except that this wave
is identical to wave one.
##### Looting Stage
 If no soldiers survived then it's a tie. Otherwise the winner is the side with surviving soldiers.
 The winner can steal resources from the defender in a manner proportional to the amount of surviving
 soldiers. Note that all surviving soldiers will return back to the winner, and any dead soldier is gone.
 
 All of this is executed instantly.
## Bot API
In order to write a bot you *must* inherit from the `Bot` class and implement the give functions (see relevant
documentation).
Note that the function `getBotAction` returns a `IRespondableAction`. This includes only the following classes:
* `RecruitAction(int amountToRecruit)`
* `TradeAction(Game.Resources giving, Game.Resources taking, int givingAmount, int takingAmount, Player secondActor)`
* `WaitAction()`
* `WarAction(Player attacked, Tuple3Int attackingForcesDivision)`
### GameWrapper
The bots can use the `GameWrapper` object in order to access game constants and data about other players.
Note that you can't use this object to change any of the data given. Using `GameWrapper` you can also
access the history of actions that were executed. You will get a class of type `HistoricalAction` and you will
need to cast it to the relevant class yourself. For example:

    HistoricalAction act = game.getChronicle(5);
    Trade tradeAct;
    if (act.getType() == HistoricalAction.Type.TRADE){
        tradeAct = (Trade) act;
    } 
### Bot Default Action\Reaction
When your bot take too long to respond or raises an exception a default action will be performed for him.
* The default action for a turn is waiting
* The default reply to a trade is true (agreeing)
* The default reply to war is `(0,0,0)` (not soldiers in any flank)