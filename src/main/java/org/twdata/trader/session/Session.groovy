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
import org.twdata.trader.command.CommandErrors
import java.lang.reflect.Field
import org.twdata.trader.command.NotNull
import org.twdata.trader.util.CommandUtil
import org.twdata.trader.command.CommandException
import org.twdata.trader.command.CommandFormatException
import org.twdata.trader.command.CommandResponse
import org.twdata.trader.event.TraderEventManager
import org.twdata.trader.event.events.CommandExecutedEvent
import org.twdata.trader.event.EventManager

/**
 * 
 */

public class DefaultSession implements Session {
    private final Game game;
    private final Trader player;
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
    }

    public Trader getPlayer() {
        return player;
    }

    public Game getGame() {
        return game;
    }

    public void executeCommand(String name, Map<String,?> cmdArgs) throws CommandException, CommandFormatException
    {
        name = name.toLowerCase();
        def args = [:];
        args["game"] = game;
        args["player"] = player;

        CommandExecutor executor = commands[name];
        if (executor != null) {
            cmdArgs.each {String k,Object v ->
                if (!executor.arguments[k])
                    throw new CommandFormatException("Unexpected parameter: "+ k);
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
                        throw new CommandFormatException("Unable to convert type for argument " + k);
                    }
                }
                args[k] = value;

            }
            def requiredState = executor.commandClass.getAnnotation(RequiredGameState.class);
            if (requiredState && state != requiredState.value()) {
                throw new CommandFormatException("Not in required state: " + requiredState.value());
            }

            // test for not null

            Command cmd = (Command) executor.commandClass.newInstance(args);

            CommandUtil.eachPrivateParamField(executor.commandClass, { Field f ->
                if (f.getAnnotation(NotNull.class)) {
                    if (!f.get(cmd)) {
                        throw new CommandFormatException("Null parameter " + f.getName() + " not allowed");
                    }
                }
            });

            CommandErrors errors = cmd.validate();
            if (!errors.isEmpty()) {
                throw new CommandFormatException("Errors: " + errors);
            }

            game.age += cmd.getTimeCost();
            CommandResponse res = cmd.execute();
            EventManager.broadcast(new CommandExecutedEvent(command: cmd, response: res, session:this));

        }  else {
            throw new CommandFormatException("Invalid command:" + name);
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
class CommandExecutor {
    final Class<Command> commandClass;
    final Map<String,Class> arguments = [:];

    public CommandExecutor(Class<Command> cls) {
        commandClass = cls;
        CommandUtil.eachPrivateParamField(cls, {Field f ->
            arguments[f.name] = f.getType();
        });
    }
}
