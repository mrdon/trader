package org.twdata.trader.storage

import org.twdata.trader.model.ShipType
import org.twdata.trader.model.Game
import org.twdata.trader.model.Commodity
import org.twdata.trader.model.City
import org.twdata.trader.model.Market
import org.twdata.trader.model.Trader
import org.twdata.trader.model.Ship

/**
 * 
 */

public class DefaultDataLoader implements DataLoader {

    private Game create() {
        def shipTypes = [
                'Junker' : new ShipType( name: "Junker", holds: 10),
                'Cruiser' :  new ShipType( name: "Cruiser", holds: 40),
                'Battleship': new ShipType( name: "Battleship", holds: 100)];

        def commodities = [
                'Food' : new Commodity( name: 'Food', minPrice: 10, maxPrice: 50),
                'Beer' : new Commodity( name: 'Beer', minPrice: 30, maxPrice: 70),
                'Wine' : new Commodity( name: 'Wine', minPrice: 20, maxPrice: 150),
                'Cocaine' : new Commodity( name: 'Cocaine', minPrice: 100, maxPrice: 300)];

        def cities = [
                'Sol' : new City( name: 'Sol', market: new Market(commodities.values())),
                'Justa' : new City( name: 'Justa', market: new Market(commodities.values())),
                'Brennnat' : new City( name: 'Brennnat', market: new Market(commodities.values())),
                'Othega' : new City( name: 'Othega', market: new Market(commodities.values()))];

        def traders = [:]

        return new Game( cities: cities, traders: traders, commodities: commodities, shipTypes: shipTypes);
    }

    public Game load(String playerName)
    {
        Game game = create();
        def player = new Trader( name: playerName, credits: 1000, city: game.cities['Sol'], ship: new Ship( type: game.shipTypes['Junker'], holds: [:]));
        game.traders[playerName] = player;
        return game;
    }

    public void save(Game game)
    {
    }
}