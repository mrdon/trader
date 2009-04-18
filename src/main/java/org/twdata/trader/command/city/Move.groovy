package org.twdata.trader.command.city

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.NotNull
import org.twdata.trader.model.City
import org.twdata.trader.command.CommandErrors
import org.twdata.trader.command.GameState

/**
 * 
 */
public class Move extends AbstractCommand {
    @NotNull City toCity;

    public int getTurnCost() {
        return 4;
    }

    public CommandErrors validate()
    {
        CommandErrors errs = super.validate();
        if (toCity == player.city)
            errs.add("toCity", "Target city must be different than current city");
    }



    public GameState execute() {
        player.city = toCity;
        return GameState.IN_CITY;
    }

    public String toString()
    {
        return "Move to city: " + toCity.name;
    }


}