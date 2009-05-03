package org.twdata.trader.guice

import org.twdata.trader.storage.DataLoader
import org.twdata.trader.storage.DefaultDataLoader
import org.twdata.trader.command.Command
import org.twdata.trader.util.ResolverUtil
import org.twdata.trader.session.DefaultSessionFactory
import org.twdata.trader.SessionFactory

/**
 * 
 */

public class SimpleContainer {
    DataLoader dataLoader;
    SessionFactory sessionFactory;

    public SimpleContainer() {
        this.dataLoader = new DefaultDataLoader();

        ResolverUtil<Command> resolver = new ResolverUtil<Command>();
        resolver.find(new ResolverUtil.IsA(Command.class), "org.twdata.trader.command.city", "org.twdata.trader.command.global", "org.twdata.trader.command.market");
        sessionFactory = new DefaultSessionFactory(this.dataLoader, resolver.getClasses());
    }

}