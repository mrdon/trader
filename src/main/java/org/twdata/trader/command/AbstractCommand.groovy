package org.twdata.trader.command

import org.twdata.trader.model.Game
import org.twdata.trader.model.Trader

/**
 * 
 */

public abstract class AbstractCommand implements Command {

    @NotNull Game game;
    @NotNull Trader player;
    
    public int getTurnCost()
    {
        return 0;
    }

    public CommandErrors validate() {
        return new CommandErrors();
    }

}