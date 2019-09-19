package botdata.bottools;

import gameengine.Game;
import gameengine.GameConstants;
import gameengine.Player;
import gameengine.history.HistoricalAction;

import java.util.ArrayList;
import java.util.List;

/**
 * An object allowing to get all the relevant information about a game without having the ability to change anything
 * about the actual game,
 */
public class GameWrapper {

    private Game game;

    public GameWrapper(Game game){
        this.game = game;
    }

    /**
     *
     * @return A list of all the players in the same order as the turn order
     */
    public List<Player> getPlayers() {
        List<Player> players = game.getPlayers();
        List<Player> playersCopy = new ArrayList<>();
        for (Player p : players){
            playersCopy.add(p.getDummy());
        }
        return playersCopy;
    }

    /**
     *
     * @param i the index of the chronicle
     * @return the (i+1)-th <code>IHistoricalAction</code> recorded.
     */
    public HistoricalAction getChronicle(int i){
        return game.getHistory().get(i);
    }

    /**
     *
     * @param cycle The cycle index
     * @param player The player index
     * @return The <code>IHistoricalAction</code> performed at that cycle by that player (given by the indices).
     */
    public HistoricalAction getChronicle(int cycle, int player){
        return getChronicle(cycle*game.getPlayers().size() + player);
    }

    public int getChronicleAmount(){
        return game.getHistory().size();
    }

    public GameConstants getConsts() {
        return game.getConsts();
    }

    public Player getPlayerByNameOrId(String input){
        Player p = game.getPlayerByNameOrId(input);
        if (p == null) return null;
        return p.getDummy();
    }

}
