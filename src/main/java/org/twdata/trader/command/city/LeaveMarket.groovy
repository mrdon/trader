package org.twdata.trader.command.city

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.GameState
import org.twdata.trader.command.CommandResponse

/**
 * 
 */

public class LeaveMarket extends AbstractCommand {

    public CommandResponse execute()
    {
        return new CommandResponse(state: GameState.IN_CITY);
    }

    public String toString()
    {
        return "Leave market";
    }




}