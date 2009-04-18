package org.twdata.trader;

import org.twdata.trader.model.external.ExternalGame;
import org.twdata.trader.model.external.ExternalTrader;

import java.util.Map;

/**
 *
 */
public interface Session
{

    ExternalGame getGame();

    ExternalTrader getPlayer();

    void executeCommand(String name, Map<String,?> args);
}
