package org.twdata.trader.model.simple

import org.twdata.trader.model.MarketUpdateStrategy
import org.twdata.trader.model.Commodity
import org.twdata.trader.model.Price

/**
 * 
 */

public class SimpleMarketUpdateStrategy implements MarketUpdateStrategy {

    private final Random rnd = new Random();

    public Map<Commodity, Price> update(Collection<Commodity> commodities)
    {
        def items = new TreeMap<Commodity,Price>();
        commodities.each {Commodity c ->
            def price = calcPrice(c);
            items[c] = new Price(sell: price, buy: price);
        };
        return items;
    }

    public boolean shouldUpdateEachVisit()
    {
        return true;
    }

    private calcPrice(Commodity c) {
        return rnd.nextInt(c.maxPrice - c.minPrice) + c.minPrice;
    }
}