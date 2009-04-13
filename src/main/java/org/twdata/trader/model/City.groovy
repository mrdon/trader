package org.twdata.trader.model

import org.twdata.trader.model.external.ExternalCity;

class City implements ExternalCity {
    String name;
    Market market;

    public String toString()
    {
        return "City '" + name + "':\n" + market;
    }
}
