package org.twdata.trader.session

import java.lang.reflect.Method
import org.twdata.trader.Session
import org.twdata.trader.command.Command
import org.twdata.trader.command.GameState
import org.twdata.trader.command.RequiredGameState
import org.twdata.trader.model.City
import org.twdata.trader.model.Commodity
import org.twdata.trader.model.Game
import org.twdata.trader.model.Trader
import org.twdata.trader.model.external.ExternalGame
import org.twdata.trader.model.external.ExternalTrader

/**
 * 
 */

public class DefaultSession implements Session {
    private final Game game;
    private final Trader player;
    private int turnsLeft;
    private final Map<String,Class<Command>> commands;
    private GameState state;

    public DefaultSession (Game game, Trader player, Set<Class<Command>> commands) {
        def cmds = [:];
        commands.each {Class<Command> it ->
            cmds[it.simpleName.toLowerCase()] = new CommandExecutor(it);
        };
        this.commands = cmds;
        this.player = player;
        this.game = game;
        state = GameState.IN_CITY;
        player.turns = 50;
    }

    public ExternalTrader getPlayer() {
        return player;
    }

    public ExternalGame getGame() {
        return game;
    }

    private boolean consumeTurn(int turn) {
        if (turnsLeft - turn < 0) {
            System.out.println("You are out of turns");
            return false;
        } else {
            System.out.println(turnsLeft + " turns remaining");
            turnsLeft -= turn;
            return true;
        }
    }

    public void executeCommand(String name, Map<String,?> cmdArgs)
    {
        name = name.toLowerCase();
        def args = [:];
        args["game"] = game;
        args["player"] = player;

        CommandExecutor executor = commands[name];
        if (executor != null) {
            cmdArgs.each {String k,Object v ->
                Class type = executor.arguments[k];
                Object value = v;
                if (!type.isAssignableFrom(v.getClass())) {
                    if (type.is(City)) {
                        value = validateCity(v);
                    } else if (type.is(Commodity)) {
                        value = validateCommodity(v);
                    } else if (type == int.class) {
                        value = Integer.parseInt(v.toString());
                    } else {
                        throw new IllegalArgumentException("Unable to convert type for argument " + k);
                    }
                }
                args[k] = value;

            }
            def requiredState = executor.commandClass.getAnnotation(RequiredGameState.class);
            if (requiredState && state != requiredState.value()) {
                throw new IllegalArgumentException("Not in required state: " + requiredState.value());
            }

            // test for not null

            Command cmd = executor.commandClass.newInstance(args);

            // validate

            if (player.turns < cmd.getTurnCost()) {
                throw new IllegalArgumentException("Not enough turns left");
            } else {
                player.turns -= cmd.getTurnCost();
                cmd.execute();
                System.out.println(cmd.toString());
                if (cmd.getTurnCost() > 0) {
                    System.out.println("You have " + player.turns + " turns and " + player.credits + " credits left");
                }
            }
        }
    }

    private Commodity validateCommodity(String name){
        Commodity c = game.commodities[name];
        if (!c)
            throw new IllegalArgumentException("Invalid commodity: " + name);
        return c;
    }

    private City validateCity(String name){
        City c = game.cities[name];
        if (!c)
            throw new IllegalArgumentException("Invalid city: " + name);
        return c;
    }

}
private class CommandExecutor {
    final Class<Command> commandClass;
    final Map<String,Class> arguments = [:];

    public CommandExecutor(Class<Command> cls) {
        commandClass = cls;
        cls.methods.findAll{Method it -> it.name =~ /^get[A-Z]/ }.each{Method it ->
            def name = it.name[3].toLowerCase()+it.name[4..-1];
            arguments[name] = it.getReturnType();
        };
    }
}
