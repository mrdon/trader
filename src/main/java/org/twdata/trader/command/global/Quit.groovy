package org.twdata.trader.command.global

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.GameState
import org.twdata.trader.command.CommandResponse

/**
 * 
 */

public class Quit extends AbstractCommand {
    public CommandResponse execute()
    {
        return new CommandResponse(state: GameState.END);
    }

    public String toString()
    {
        return "Exit game with " + player.credits + " credits";
    }


}