package org.twdata.trader;

import org.twdata.trader.model.external.ExternalGame;
import org.twdata.trader.model.external.ExternalTrader;
import org.twdata.trader.model.Trader;
import org.twdata.trader.model.Game;
import org.twdata.trader.command.CommandException;

import java.util.Map;

/**
 *
 */
public interface Session
{

    Game getGame();

    Trader getPlayer();

    void executeCommand(String name, Map<String,?> args) throws CommandException;
}
