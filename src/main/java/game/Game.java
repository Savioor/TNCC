package game;

import bottools.Bot;
import bottools.GameWrapper;
import game.actions.IAction;
import game.events.AbstractEvent;
import game.events.ProductionEvent;

import java.util.ArrayList;
import java.util.List;

import util.log.Logger;
import util.log.NamedLogger;

public class Game {

    private List<Player> players;
    private List<AbstractEvent> events;
    private GameConstants consts;
    private Logger logger;
    private int turn;
    private final long BOT_TIMEOUT = 20;

    public Game(List<Player> players, GameConstants conts) {
        this.players = players;
        this.events = new ArrayList<>();
        this.consts = conts;
        events.add(new ProductionEvent(this));
        for (Player p : players)
            p.initialize(this);
        logger = new NamedLogger("GAME");
        logger.setDebug(false);
        logger.setInfo(true);
        logger.setWarn(true);
        turn = 0;
        for (Player p : players) {
            if (p.isBot) {
                ((Bot) p.actor).reset(new GameWrapper(this), p.clone());
            }
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public GameConstants getConsts() {
        return consts;
    }

    public enum Resources {
        GOLD,
        FOOD,
        POPULATION,
        MILITARY,
        LAND
    }

    public List<Player> executeCycle() {
        for (Player p : players) {
            if (p.isAlive()) {
                if (!p.isBot) {
                    executeTurn(p);
                } else {
                    Thread botThread = new Thread(() -> executeTurn(p));
                    botThread.start();
                    long timeStart = System.currentTimeMillis();
                    while (botThread.isAlive()) {
                        if (System.currentTimeMillis() - timeStart > BOT_TIMEOUT) {
                            botThread.interrupt();
                            logger.warn(p.getName() + " took too much time to respond");
                            break;
                        }
                    }
                }
            }
        }

        List<AbstractEvent> eventClone = new ArrayList<>(events);
        for (AbstractEvent event : eventClone) {
            event.execute();
            if (event.isFinished())
                events.remove(event);
        }

        List<Player> alive = new ArrayList<>();
        for (Player p : players)
            if (p.isAlive()) alive.add(p);
        if (alive.size() == 1) {
            return alive;
        }
        if (turn == consts.maxTurns) {
            return alive;
        }
        turn++;
        return null;
    }

    public void executeTurn(Player current) {

        IAction action = current.getAction(this);
        IAction.ActionInfo info = action.execute(this, current);
        if (info.success) {
            logger.debug("Success at action: " + action.getName() + ", " + info.message);
        } else {
            logger.warn(current.getName() + " executed Illegal action " + action.getName() + ": " + info.message);

        }
        logger.debug(action.getName() + " was run by " + current.getName());
    }

    public Player getPlayerByName(String name) {
        Player ret = null;
        for (Player p : this.getPlayers()) {
            if (p.getName().equals(name)) {
                ret = p;
                break;
            }
        }
        if (ret == null) {
            throw new RuntimeException("trying to get player by name: " + name + " , but name not found");
        }
        return ret;
    }

    public Player getPlayerById(int id) {
        if (id >= this.getPlayers().size() || id < 0) {
            throw new RuntimeException("trying to get player that doesn't exist: " + id);
        }
        return this.getPlayers().get(id);
    }

    public Player getPlayerByNameOrId(String input) {
        Player ret = null;
        try {
            int ID = Integer.parseInt(input);
            if (ID >= this.getPlayers().size() || ID < 0)
                throw new NumberFormatException();
            ret = this.getPlayers().get(ID);
        } catch (NumberFormatException e) {
            for (Player p : this.getPlayers()) {
                if (p.getName().equals(input)) {
                    ret = p;
                    break;
                }
            }
        }
        return ret;
    }

}
