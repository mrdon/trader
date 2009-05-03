package org.twdata.trader.command.city

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.NotNull
import org.twdata.trader.model.City
import org.twdata.trader.command.CommandErrors
import org.twdata.trader.command.GameState
import org.twdata.trader.command.Param
import org.twdata.trader.command.CommandResponse

/**
 * 
 */
public class Move extends AbstractCommand {
    @NotNull @Param City toCity;

    public int getTimeCost() {
        return 4;
    }

    public CommandErrors validate()
    {
        CommandErrors errs = super.validate();
        if (toCity == player.city)
            errs.add("toCity", "Target city must be different than current city");
        return errs;
    }



    public CommandResponse execute() {
        player.city = toCity;
        return new CommandResponse(state: GameState.IN_CITY,
                                   modified: ["player": player]);
    }

    public String toString()
    {
        return "Move to city: " + toCity.name;
    }


}