package org.twdata.trader.model

import org.twdata.trader.model.external.ExternalGame
import org.twdata.trader.model.external.ExternalCity

/**
 * 
 */

public class Game  {

    Map<String,City> cities;
    Map<String,Trader> traders;
    Map<String,Commodity> commodities;
    Map<String,ShipType> shipTypes;

}