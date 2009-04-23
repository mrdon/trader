package org.twdata.trader.guice

import org.twdata.trader.storage.DataLoader
import org.twdata.trader.storage.DefaultDataLoader
import org.twdata.trader.command.Command
import org.twdata.trader.util.ResolverUtil
import org.twdata.trader.session.DefaultSessionFactory
import org.twdata.trader.SessionFactory
import org.twdata.trader.model.MarketUpdateStrategy
import org.twdata.trader.model.simple.SimpleMarketUpdateStrategy
import org.twdata.trader.web.JettyWebServer

/**
 * 
 */

public class SimpleContainer {
    DataLoader dataLoader;
    SessionFactory sessionFactory;
    MarketUpdateStrategy marketUpdateStrategy;
    JettyWebServer webServer;

    public SimpleContainer() {
        dataLoader = new DefaultDataLoader();

        ResolverUtil<?> resolver = new ResolverUtil<?>();
        resolver.find(new ResolverUtil.IsA(Command.class), "org.twdata.trader.command.city", "org.twdata.trader.command.global", "org.twdata.trader.command.market");
        sessionFactory = new DefaultSessionFactory(dataLoader, resolver.getClasses());
        marketUpdateStrategy = new SimpleMarketUpdateStrategy();
        webServer = new JettyWebServer(sessionFactory);
    }

}