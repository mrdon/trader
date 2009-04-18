package org.twdata.trader.session;

import org.twdata.trader.SessionFactory;
import org.twdata.trader.command.Command;
import org.twdata.trader.Session;
import org.twdata.trader.storage.DataLoader;
import org.twdata.trader.model.Game;
import org.twdata.trader.model.Trader;

import java.util.Set;

/**
 * 
 */

public class DefaultSessionFactory implements SessionFactory
{

    private final Set<Class<Command>> commands;
    private final DataLoader dataLoader;

    public DefaultSessionFactory(DataLoader dataLoader, Set<Class<Command>> commands)
    {
        this.commands = commands;
        this.dataLoader = dataLoader;
    }

    public Session create(String name)
    {
        Game game = dataLoader.load(name);
        Trader player = game.getTraders().get(name);
        return new DefaultSession(game, player, commands);
    }
}