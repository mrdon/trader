package org.twdata.trader.command.market

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.GameState
import org.twdata.trader.command.NotNull
import org.twdata.trader.model.Commodity
import org.twdata.trader.model.Market
import org.twdata.trader.command.CommandErrors
import org.twdata.trader.command.Param

/**
 * 
 */

public class BuyCommodity extends AbstractCommand {

    @NotNull @Param Commodity commodity;
    @NotNull @Param int quantity;

    public CommandErrors validate()
    {
        CommandErrors errors = super.validate();
        if (player.credits < determinePrice()) {
            errors.add("player", "Not enough credits: " + player.credits);
        }
        if (player.ship.freeHolds < quantity) {
            errors.add("player", "Not enough free holds");
        }
        return errors;
    }

    private int determinePrice()
    {
        player.city.market.items[commodity].buy * quantity
    }

    public GameState execute()
    {
        player.credits -= determinePrice();
        player.ship.addHolds(commodity, quantity);
        return GameState.IN_MARKET;
    }

    public String toString()
    {
        return "Buy " + quantity + " units of " + commodity;
    }


}