package org.twdata.trader.model;

@Immutable final class Commodity implements Comparable {

    String name;
    int minPrice;
    int maxPrice;

    public int compareTo(Object o)
    {
        return name.compareTo(((Commodity)o).name);
    }
}
