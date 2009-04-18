package org.twdata.trader.storage;

import org.twdata.trader.model.Game;

/**
 *
 */
public interface DataLoader
{
    Game load(String name);

    void save(Game game);
}
