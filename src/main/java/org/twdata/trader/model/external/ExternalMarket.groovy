package org.twdata.trader.model.external

import org.twdata.trader.model.Commodity
import org.twdata.trader.model.Price

/**
 * 
 */
public interface ExternalMarket {
    public Map<Commodity,Price> getItems();
}