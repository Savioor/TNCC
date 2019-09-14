package game;

import util.log.Logger;
import util.log.NamedLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Competition {

    private Game current;
    private List<Player> players;
    private Logger logger;

    public Competition(List<Player> players) {
        this.players = players;
        this.logger = new NamedLogger("COMPETITION");
    }

    public List<Integer> runGame(List<Integer> order, GameConstants consts){
        List<Player> players = clonePlayers(order);
        logger.info("Running new game with: " + Arrays.toString(players.toArray()));
        current = new Game(players, consts);
        List<Player> winners;
        while(true){
            winners = current.executeCycle();
            if (winners != null){
                break;
            }
        }
        List<Integer> winnerIds = new ArrayList<>();
        for (Player p : winners){
            winnerIds.add(getPlayerId(p));
        }
        return winnerIds;
    }

    public List<Player> clonePlayers(List<Integer> order){
        List<Player> ret = new ArrayList<>();
        for (Integer i : order){
            ret.add(players.get(i).clone());
        }
        return ret;
    }

    public int getPlayerId(Player p){
        for (int i = 0; i < players.size(); i++){
            if (p.getName().equals(players.get(i).getName())){
                return i;
            }
        }
        return -1;
    }

    public String[] getGroupNames(){
        String[] arr = new String[players.size()];
        for (int i = 0; i < players.size(); i++){
            arr[i] = players.get(i).getName();
        }
        return arr;
    }

}
