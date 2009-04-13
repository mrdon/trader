package org.twdata.trader.model;

import java.util.Map;
import java.util.Collection;

/**
 *
 */
public interface MarketUpdateStrategy
{
    public Map<Commodity,Price> update(Collection<Commodity> commodities);

    public boolean shouldUpdateEachVisit();
}
