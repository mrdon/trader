package org.twdata.trader.command.market

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.GameState
import org.twdata.trader.command.NotNull
import org.twdata.trader.model.Commodity
import org.twdata.trader.model.Market
import org.twdata.trader.command.CommandErrors
import org.twdata.trader.command.Param
import org.twdata.trader.command.CommandResponse

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

    public CommandResponse execute()
    {
        player.credits -= determinePrice();
        player.ship.addHolds(commodity, quantity);
        return new CommandResponse(state: GameState.IN_MARKET,
                                   modified: ["player": player, "ship":player.ship]);
    }

    public String toString()
    {
        return "Buy " + quantity + " units of " + commodity;
    }


}