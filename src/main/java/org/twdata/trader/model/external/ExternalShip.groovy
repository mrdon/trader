package org.twdata.trader.model.external

import org.twdata.trader.model.ShipType
import org.twdata.trader.model.Commodity

/**
 * 
 */

public interface ExternalShip {
    public ShipType getType;
    public Map<Commodity,Integer> getHolds();
}