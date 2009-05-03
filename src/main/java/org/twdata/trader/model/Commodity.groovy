package org.twdata.trader.model;

@Immutable final public class Commodity implements Comparable {

    String name;
    int minPrice;
    int maxPrice;

    public int compareTo(Object o)
    {
        return name.compareTo(((Commodity)o).name);
    }
}
