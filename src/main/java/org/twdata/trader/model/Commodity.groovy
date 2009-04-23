package org.twdata.trader.model;

@Immutable final class Commodity implements Comparable<Commodity> {

    @External String name;
    @External int minPrice;
    @External int maxPrice;

    public int compareTo(Commodity o)
    {
        return name.compareTo(o.name);
    }
}
