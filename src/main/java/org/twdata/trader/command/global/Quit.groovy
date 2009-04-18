package org.twdata.trader.command.global

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.GameState

/**
 * 
 */

public class Quit extends AbstractCommand {
    public GameState execute()
    {
        return GameState.END;
    }

    public String toString()
    {
        return "Exit game with " + player.credits + " credits and " + player.turns + " turns";
    }


}