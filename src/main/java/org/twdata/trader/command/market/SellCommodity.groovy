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

public class SellCommodity extends AbstractCommand {

    @NotNull @Param Commodity commodity;
    @NotNull @Param int quantity;

    public CommandErrors validate()
    {
        CommandErrors errors = super.validate();
        if (player.ship.holds[commodity] < quantity) {
            errors.add("quantity", "Not " + quantity + " units of " + commodity.name + " to sell from holds");
        }
        return errors;
    }

    private int determinePrice()
    {
        player.city.market.items[commodity].buy * quantity
    }

    public CommandResponse execute()
    {
        Market market = player.city.market;
        int price = market.items[commodity].sell * quantity;
        player.credits += price;
        player.ship.removeHolds(commodity, quantity);
        return new CommandResponse(state: GameState.IN_MARKET,
                                   modified: ["player": player, "ship":player.ship]);
    }

    public String toString()
    {
        return "Sell " + quantity + " units of " + commodity.name;
    }


}