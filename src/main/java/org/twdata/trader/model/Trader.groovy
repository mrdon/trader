package org.twdata.trader.model

import org.twdata.trader.model.external.ExternalTrader
import org.twdata.trader.model.external.ExternalShip;

class Trader implements ExternalTrader {

    @External String name;
    @External long credits;
    @External City city;
    @External Ship ship;
    @External int turns;

    public ExternalShip getExternalShip()
    {
        return ship;
    }
}
