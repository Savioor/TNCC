package game;

import bottools.GameWrapper;
import game.actions.action_getters.IActionGetter;
import game.actions.IAction;
import game.actions.action_getters.IReActionGetter;
import game.actions.reactions.Reaction;

import java.util.List;

public class Player implements Cloneable{

    private int[] resources;
    private String name;
    private boolean isAlive;
    protected IActionGetter actor;
    protected IReActionGetter reactor;
    public final boolean isBot;

    public Player(String name, IActionGetter getter, IReActionGetter reactGetter, boolean isBot){
        this.name = name;
        actor = getter;
        reactor = reactGetter;
        isAlive = true;
        resources = new int[Game.Resources.values().length];
        this.isBot = isBot;
    }

    public Player(String name, IActionGetter getter, IReActionGetter reactGetter){
        this(name, getter, reactGetter, false);
    }

    public Player clone(){
        try{
            return (Player)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public Player getDummy(){
        Player dummy = new Player(this.name, null, null);
        dummy.setAlive(this.isAlive);
        dummy.resources = this.resources.clone();
        return dummy;
    }

    public void initialize(Game game){
        for (Game.Resources res : Game.Resources.values()){
            resources[res.ordinal()] = game.getConsts().getStartingValue(res);
        }
    }

    public void addResource(int index, int amount){
        resources[index] += amount;
    }

    public void subtractResource(int index, int amount){
        addResource(index, -amount);
    }

    public int getResource(int index){
        return resources[index];
    }

    public void setResource(int index, int value){
        resources[index] = value;
    }

    public void addResource(Game.Resources index, int amount){
        resources[index.ordinal()] += amount;
    }

    public void subtractResource(Game.Resources index, int amount){
        addResource(index, -amount);
    }

    public int getResource(Game.Resources index){
        return resources[index.ordinal()];
    }

    public void setResource(Game.Resources index, int value){
        resources[index.ordinal()] = value;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public IAction getAction(Game game) {
        return actor.getAction(game, this);
    }

    public <T> Reaction<T> getReaction(List<String> action, Game game){
        return reactor.getReaction(action, game, this);
    }

    public boolean canGoToWar(GameWrapper game){
        return this.getGold() >= game.getConsts().goldForWar && this.isAlive();
    }

    public int getGold(){
        return getResource(Game.Resources.GOLD);
    }

    public int getLand(){
        return getResource(Game.Resources.LAND);
    }

    public int getMilitary(){
        return getResource(Game.Resources.MILITARY);
    }

    public int getPopulation(){
        return getResource(Game.Resources.POPULATION);
    }

    public int getFood(){
        return getResource(Game.Resources.FOOD);
    }

    public String toString(){
        return getName();
    }

    public boolean canAttack(GameWrapper game, Player other){
        return (!other.equals(this)) && other.isAlive() && this.canGoToWar(game);
    }

    public boolean canTradeWith(GameWrapper game, Player other){
        return (!other.equals(this)) && other.isAlive();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(obj == this){
            return true;
        }
        if(obj instanceof Player){
            return getName().equals(((Player)obj).getName());
        }
        return false;
    }
}
