package org.twdata.trader;

import org.twdata.trader.model.Trader;
import org.twdata.trader.model.Game;
import org.twdata.trader.command.CommandException;
import org.twdata.trader.event.TraderEventManager;

import java.util.Map;

/**
 *
 */
public interface Session
{

    Game getGame();

    Trader getPlayer();

    TraderEventManager getEventManager();

    void executeCommand(String name, Map<String,?> args) throws CommandException;
}
