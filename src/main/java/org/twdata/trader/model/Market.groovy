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
    private final List<PriceEvent> priceEvents;
    PriceEvent activeEvent;

    public Market(Set<Commodity> commodities, List<PriceEvent> priceEvents)
    {
        this.commodities = commodities;
        items = new TreeMap<Commodity,Price>();
        updatePeriod = rnd.nextInt(20) + 10;
        EventManager.register(this);
        this.priceEvents = priceEvents;
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
         if (rnd.nextInt(2) == 0) {
            activeEvent = priceEvents.get(rnd.nextInt(priceEvents.size()));
        } else {
            activeEvent = null;
        }
        commodities.each {Commodity c ->

            def price1 = calcPrice(c);
            def price2 = calcPrice(c);

            if (activeEvent && activeEvent.commodity == c) {
                if (activeEvent.modifier > 0) {
                    price1 *= activeEvent.modifier;
                    price2 *= activeEvent.modifier;
                } else {
                    price1 /= -1 * activeEvent.modifier;
                    price2 /= -1 * activeEvent.modifier;
                }
            }
            if (price1 >= price2) {
                items[c] = new Price(sell: price1, buy: price2);
            } else {
                items[c] = new Price(sell: price2, buy: price1);
            }
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