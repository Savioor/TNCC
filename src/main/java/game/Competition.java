package game;

import util.Tuple;

import java.util.List;

public class Competition {

    private Game current;
    private List<Tuple<Player,Integer>> players;

    public Competition(List<Tuple<Player, Integer>> players) {
        this.players = players;
    }


    public Tuple<Player,Integer> getPlayerByNameOrId(String input){
        Tuple<Player,Integer> ret = null;
        try {
            int ID = Integer.parseInt(input);
            if (ID >= this.players.size() || ID < 0)
                throw new NumberFormatException();
            ret = this.players.get(ID);
        } catch (NumberFormatException e){
            for (Tuple<Player,Integer> p : this.players){
                if (p.first.getName().equals(input)){
                    ret = p;
                    break;
                }
            }
        }
        return ret;
    }

}
