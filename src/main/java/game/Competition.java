package game;

import util.Tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Competition {

    private Game current;
    private List<Player> players;

    public Competition(List<Player> players) {
        this.players = players;
    }

    public List<Integer> runGame(List<Integer> order, GameConstants consts){
        current = new Game(clonePlayers(order), consts);

    }

    public List<Player> clonePlayers(List<Integer> order){
        List<Player> ret = new ArrayList<>();
        for (Integer i : order){
            ret.add(players.get(i).clone());
        }
    }

    public Player getPlayerByNameOrId(String input){
        Player ret = null;
        try {
            int ID = Integer.parseInt(input);
            if (ID >= this.players.size() || ID < 0)
                throw new NumberFormatException();
            ret = this.players.get(ID);
        } catch (NumberFormatException e){
            for (Player p : this.players){
                if (p.getName().equals(input)){
                    ret = p;
                    break;
                }
            }
        }
        return ret;
    }

    public String[] getGroupNames(){
        String[] arr = new String[players.size()];
        for (int i = 0; i < players.size(); i++){
            arr[i] = players.get(i).getName();
        }
        return arr;
    }

}
