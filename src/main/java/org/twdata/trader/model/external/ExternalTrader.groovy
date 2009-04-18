package org.twdata.trader.model.external

/**
 * 
 */
public interface ExternalTrader {
    public String getName();
    public int getTurns();
    public ExternalShip getExternalShip();
}