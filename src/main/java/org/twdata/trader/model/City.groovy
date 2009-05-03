package org.twdata.trader.model

import org.twdata.trader.model.Coordinate
import org.twdata.trader.model.Market

public class City {
    String name;
    Market market;
    Coordinate coordinate;
    String imageId;
    

    public String toString()
    {
        return "City '" + name + "':\n" + market;
    }
}
