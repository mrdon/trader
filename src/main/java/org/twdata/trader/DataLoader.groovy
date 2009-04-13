package org.twdata.trader

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

public class DataLoader {

    public Game load() {
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

        def traders = [
                'Donaea' : new Trader( name: 'Donaea', credits: 1000, city: cities['Sol'], ship: new Ship( type: shipTypes['Junker'], holds: [:])),
                'Juena' : new Trader( name: 'Juena', credits: 1000, city: cities['Sol'], ship: new Ship( type: shipTypes['Cruiser'], holds: [:])),
                'Paethe' : new Trader( name: 'Paethe', credits: 1000, city: cities['Brennnat'], ship: new Ship( type: shipTypes['Junker'], holds: [:])),
        ]

        return new Game( cities: cities, traders: traders, commodities: commodities, shipTypes: shipTypes);
    }

}