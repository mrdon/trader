package org.twdata.trader.model;

@Immutable final class Commodity implements Comparable<Commodity> {

    String name;
    int minPrice;
    int maxPrice;

    public int compareTo(Commodity o)
    {
        return name.compareTo(o.name);
    }
}
