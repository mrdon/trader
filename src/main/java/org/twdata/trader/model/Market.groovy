package org.twdata.trader.model

import org.twdata.trader.model.simple.SimpleMarketUpdateStrategy
import org.twdata.trader.model.external.ExternalMarket;

class Market implements ExternalMarket {
    Set<Commodity> commodities;
    Map<Commodity,Price> items;
    MarketUpdateStrategy updateStrategy;

    def Market(commodities)
    {
        this.commodities = commodities;
        this.updateStrategy = new SimpleMarketUpdateStrategy();
        items = new TreeMap<Commodity,Price>();
        visit();
    }

    public Market visit() {
        if (updateStrategy.shouldUpdateEachVisit())
            items = updateStrategy.update(commodities);
        return this;
    }

    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        items.each {Commodity c, Price p ->
            sb.append('\t').append(c.name).append(" ").append(p).append('\n');
        };
        return sb.toString();
    }
}

@Immutable final class Price {
    int sell;
    int buy;
}