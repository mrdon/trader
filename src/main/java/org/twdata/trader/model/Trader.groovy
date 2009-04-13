package org.twdata.trader.model

import org.twdata.trader.model.external.ExternalTrader
import org.twdata.trader.model.external.ExternalShip;

class Trader implements ExternalTrader {

    String name;
    long credits;
    City city;
    Ship ship;

    public ExternalShip getExternalShip()
    {
        return ship;
    }
}
