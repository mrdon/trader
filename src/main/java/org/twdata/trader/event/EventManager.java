package org.twdata.trader.event;

import org.twdata.trader.event.impl.DefaultTraderEventManager;
import groovy.lang.Closure;

/**
 *
 */
public class EventManager {
    private static final TraderEventManager self = new DefaultTraderEventManager();

    public static void register(Object obj) {
        self.register(obj);
    }

    public static void unregister(Object obj) {
        self.unregister(obj);
    }

    public static void broadcast(Object msg) {
        self.broadcast(msg);
    }
}
