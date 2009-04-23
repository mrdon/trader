package org.twdata.trader.model

import org.twdata.trader.model.external.ExternalCity;

class City {
    @External String name;
    @External Market market;

    public String toString()
    {
        return "City '" + name + "':\n" + market;
    }
}
