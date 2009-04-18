package org.twdata.trader.model

import org.twdata.trader.model.external.ExternalTrader
import org.twdata.trader.model.external.ExternalShip;

class Trader implements ExternalTrader {

    String name;
    long credits;
    City city;
    Ship ship;
    int turns;

    public ExternalShip getExternalShip()
    {
        return ship;
    }
}
