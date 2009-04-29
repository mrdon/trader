package org.twdata.trader.ui

import org.fenggui.background.PixmapBackground
import org.fenggui.composites.Window
import org.fenggui.render.Binding
import org.fenggui.render.Pixmap
import org.twdata.trader.model.City
import org.twdata.trader.ui.HudWindow
import org.twdata.trader.model.Market
import org.fenggui.Container
import org.fenggui.layout.BorderLayout
import org.fenggui.layout.GridLayout
import org.fenggui.layout.BorderLayoutData
import org.twdata.trader.model.Commodity
import org.twdata.trader.model.Price
import org.fenggui.Label
import org.fenggui.TextEditor
import org.fenggui.util.Spacing
import org.fenggui.Button
import org.fenggui.layout.RowLayout

/**
 *
 */

public class MarketWindow extends HudWindow {

    protected void populateWindow(Window frame) {
        Market market = session.player.city.market;

        Container main = frame.getContentContainer();
        main.setLayoutManager(new BorderLayout());

        Container table = new Container();
        table.setLayoutData(BorderLayoutData.NORTH);
        table.getAppearance().setMargin(new Spacing(10, 10));
        table.getAppearance().setPadding(new Spacing(5, 5));
        main.addWidget(table);

        table.setLayoutManager(new GridLayout(market.commodities.size() + 1, 3));
        table.addWidget(new Label("Commodity"));
        table.addWidget(new Label("Sell"));
        table.addWidget(new Label("Buy"));
        market.items.each { Commodity c, Price p ->
            table.addWidget(new Label(c.name));

            Container sell = new Container();
            sell.addWidget(new TextEditor(false));
            sell.addWidget(new Button("All"));
            table.addWidget(sell);

            Container buy = new Container();
            buy.addWidget(new TextEditor(false));
            buy.addWidget(new Button("Max"));
            table.addWidget(buy);
        }

        Container filling = new Container();
        filling.setLayoutData(BorderLayoutData.CENTER);
        main.addWidget(filling);

        Container okbar = new Container();
        okbar.setLayoutData(BorderLayoutData.SOUTH);
        main.addWidget(okbar);

        okbar.addWidget(new Button("Cancel"));
        okbar.addWidget(new Button("OK"));
        okbar.getAppearance().setPadding(new Spacing(5, 30));
    }


}