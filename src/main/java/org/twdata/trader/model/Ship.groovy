package org.twdata.trader.model

import org.twdata.trader.model.external.ExternalShip;

class Ship implements ExternalShip {

    ShipType type;
    Map<Commodity,Integer> holds;

    public int getFreeHolds() {
        int total = type.holds;
        holds.values().each {val ->
            total -= val;
        }
        return total;
    }

    public void removeHolds(Commodity c, int quantity) {
        holds[c] -= quantity;
        if (holds[c] == 0) holds.remove(c);
    }

    public void addHolds(Commodity c, int quantity) {
        if (!holds.containsKey(c)) holds[c] = 0;
        holds[c] += quantity;
    }

    public String toString()
    {
        final StringBuilder sb = new StringBuilder(type.name);
        sb.append('\n');
        holds.each {Commodity c, Integer q ->
            sb.append('\t').append(c.name).append(" ").append(q).append('\n');
        };
        return sb.toString();
    }


}
