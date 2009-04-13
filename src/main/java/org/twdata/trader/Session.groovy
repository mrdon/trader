package org.twdata.trader

import org.twdata.trader.model.Game
import org.twdata.trader.model.Trader
import org.twdata.trader.model.Ship
import org.twdata.trader.model.City
import org.twdata.trader.model.Market
import org.twdata.trader.model.external.ExternalCity
import org.twdata.trader.model.external.ExternalMarket
import org.twdata.trader.model.external.ExternalShip
import org.twdata.trader.model.external.ExternalTrader
import org.twdata.trader.model.external.ExternalGame
import org.twdata.trader.model.Commodity

/**
 * 
 */

public class Session {

    private Game game;
    private Trader player;
    private State state;

    public ExternalGame start(String playerName) {
        game = new DataLoader().load();
        player = game.traders[playerName];
        if (!player) {
            player = new Trader( name: playerName, credits: 1000, city: game.cities['Sol'], ship: new Ship( type: game.shipTypes['Junker'], holds: [:]));
            game.traders[playerName] = player;
            System.out.println("Created new game for " + playerName);
        } else {
            System.out.println("Loaded game for " + playerName);
        }
        state = State.IN_CITY;
        return game;
    }

    public ExternalCity move(String cityName) {
        validateState(State.IN_CITY);
        City city = validateCity(cityName);
        System.out.println("Moving to city: " + city.name);
        player.city = city;
        return (ExternalCity) city;
    }

    public ExternalMarket visitMarket() {
        state = state.check(state.IN_MARKET);
        Market market = player.city.market.visit();
        System.out.println("Visiting market: \n" + market);
        return market;
    }

    public ExternalCity leaveMarket() {
        state = state.check(State.IN_CITY);
        System.out.println("Leaving market");
        return player.city;
    }

    public ExternalShip buyCommodity(String name, int quantity) {
        validateState(State.IN_MARKET);
        Commodity com = validateCommodity(name);
        Market market = player.city.market;
        int price = market.items[com].buy * quantity;
        if (player.credits >= price && player.ship.freeHolds >= quantity) {
            player.credits -= price;
            def prev = player.ship.holds[com];
            if (!prev) prev = 0;
            player.ship.holds[com] = prev + quantity;
            System.out.println("Bought " + com.name + " for ship " + player.ship + "You have " + player.credits + " credits.");
        } else {
            System.out.println("Not enough money or space to buy " + com.name);
        }
        return player.ship;
    }

    public ExternalShip sellCommodity(String name, int quantity) {
        validateState(State.IN_MARKET);
        Commodity com = validateCommodity(name);
        Market market = player.city.market;
        int price = market.items[com].sell * quantity;
        if (player.ship.holds[com] >= quantity) {
            player.credits += price;
            player.ship.holds[com] -= quantity;
            System.out.println("Sold " + com.name + " for ship " + player.ship + "You have " + player.credits + " credits.");
        } else {
            System.out.println("Not enough money or space to buy " + com.name);
        }
        return player.ship;
    }

    public ExternalTrader getPlayer() {
        return player;
    }

    public ExternalGame getGame() {
        return game;
    }

    private void validateState(State testState) {
        if (state != testState) {
            throw new IllegalStateException("Invalid state: " + state);
        }
    }

    private Commodity validateCommodity(String name){
        Commodity c = game.commodities[name];
        if (!c)
            throw new IllegalArgumentException("Invalid commodity: " + name);
        return c;
    }

    private City validateCity(String name){
        City c = game.cities[name];
        if (!c)
            throw new IllegalArgumentException("Invalid city: " + name);
        return c;
    }
}
private enum State {
    IN_CITY,
    IN_MARKET;

    State check(final State newState)
    {
        return newState;
    }

    void illegalState(final State newState)
    {
        throw new IllegalStateException("Cannot go from State: " + this + " to: " + newState);
    }
}
