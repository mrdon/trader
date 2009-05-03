package org.twdata.trader.model

import org.twdata.trader.event.EventManager
import org.twdata.trader.event.events.CommandExecutedEvent
import org.twdata.trader.event.TraderEventListener

public class Market {
    Set<Commodity> commodities;
    Map<Commodity,Price> items;
    private final MarketUpdateStrategy strategy;
    private final Random rnd = new Random();
    private final int updatePeriod;
    private int lastUpdate;

    public Market(Set<Commodity> commodities)
    {
        this.commodities = commodities;
        items = new TreeMap<Commodity,Price>();
        updatePeriod = rnd.nextInt(20) + 10;
        EventManager.register(this);
        items = update(commodities);
    }

    @TraderEventListener
    public void onCommand(CommandExecutedEvent evt) {
        if (evt.getSession().game.age >= lastUpdate + updatePeriod) {
            items = update(commodities);
            lastUpdate = evt.session.game.age + updatePeriod;
        }
    }

    public String toString()
    {
        final StringBuilder sb = new StringBuilder();
        items.each {Commodity c, Price p ->
            sb.append('\t').append(c.name).append(" - ").append(p.buy).append(' cr\n');
        };
        return sb.toString();
    }

    protected Map<Commodity, Price> update(Collection<Commodity> commodities)
    {
        def items = new TreeMap<Commodity,Price>();
        commodities.each {Commodity c ->
            def price = calcPrice(c);
            items[c] = new Price(sell: price, buy: price);
        };
        return items;
    }

    private int calcPrice(Commodity c) {
        double gaus = rnd.nextGaussian();
        float delta = Math.max(-1f, Math.min(gaus / 3f, 1f));
        int mid = (c.maxPrice - c.minPrice)/2;
        return (delta * mid) + c.minPrice + mid ;
    }
}

@Immutable final class Price {
    int sell;
    int buy;
}