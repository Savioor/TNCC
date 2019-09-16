package bottools;

import game.Game;
import game.GameConstants;
import game.Player;
import game.history.HistoricalAction;

import java.util.ArrayList;
import java.util.List;

public class GameWrapper {

    private Game game;

    public GameWrapper(Game game){
        this.game = game;
    }

    public List<Player> getPlayers() {
        List<Player> players = game.getPlayers();
        List<Player> playersCopy = new ArrayList<>();
        for (Player p : players){
            playersCopy.add(p.getDummy());
        }
        return playersCopy;
    }

    public HistoricalAction getChronicle(int number){
        return game.getHistory().get(number);
    }

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
