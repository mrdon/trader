package org.twdata.trader.command.city

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.GameState
import org.twdata.trader.command.CommandResponse

/**
 * 
 */

public class VisitMarket extends AbstractCommand {

    public int getTimeCost()
    {
        return 1;
    }


    public CommandResponse execute()
    {
        return new CommandResponse(state: GameState.IN_MARKET);
    }

    public String toString()
    {
        return "Visit market";
    }




}