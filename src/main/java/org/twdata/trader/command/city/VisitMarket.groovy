package org.twdata.trader.command.city

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.GameState

/**
 * 
 */

public class VisitMarket extends AbstractCommand {

    public int getTurnCost()
    {
        return 1;
    }


    public GameState execute()
    {
        return GameState.IN_MARKET;
    }

    public String toString()
    {
        return "Visit market: \n" + player.city.market;
    }




}