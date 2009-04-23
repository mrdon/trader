package org.twdata.trader.model

import org.twdata.trader.model.external.ExternalGame
import org.twdata.trader.model.external.ExternalCity

/**
 * 
 */

public class Game implements ExternalGame {

    @External Map<String,City> cities;
    @External Map<String,Trader> traders;
    @External Map<String,Commodity> commodities;
    @External Map<String,ShipType> shipTypes;

    public Map<String,ExternalCity> getExternalCities() {
        return new TreeMap<String,ExternalCity>(cities);
    }
}