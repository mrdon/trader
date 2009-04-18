package org.twdata.trader.command.market

import org.twdata.trader.command.AbstractCommand
import org.twdata.trader.command.GameState
import org.twdata.trader.command.NotNull
import org.twdata.trader.model.Commodity
import org.twdata.trader.model.Market
import org.twdata.trader.command.CommandErrors

/**
 * 
 */

public class SellCommodity extends AbstractCommand {

    @NotNull Commodity commodity;
    @NotNull int quantity;

    public CommandErrors validate()
    {
        CommandErrors errors = super.validate();
        if (player.ship.holds[commodity] >= quantity) {
            errors.add("quantity", "Not " + quantity + " units of " + commodity.name + " to sell from holds");
        }
        return errors;
    }

    private int determinePrice()
    {
        player.city.market.items[commodity].buy * quantity
    }

    public GameState execute()
    {
        Market market = player.city.market;
        int price = market.items[commodity].sell * quantity;
        player.credits += price;
        player.ship.removeHolds(commodity, quantity);
        return GameState.IN_MARKET;
    }

    public String toString()
    {
        return "Sell " + quantity + " units of " + commodity;
    }


}