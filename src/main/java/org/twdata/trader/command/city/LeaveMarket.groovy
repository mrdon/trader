package org.twdata.trader.command.city

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.GameState

/**
 * 
 */

public class LeaveMarket extends AbstractCommand {

    public GameState execute()
    {
        return GameState.IN_CITY;
    }

    public String toString()
    {
        return "Leave market";
    }




}