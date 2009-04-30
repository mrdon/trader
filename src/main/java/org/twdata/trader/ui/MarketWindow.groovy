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
import org.fenggui.event.IButtonPressedListener
import org.fenggui.composites.TextArea
import org.fenggui.layout.Alignment
import org.fenggui.layout.StaticLayout
import org.fenggui.event.IWindowClosedListener

/**
 *
 */

public class MarketWindow extends HudWindow {

    private Map<Commodity,TextEditor> sellFields = new HashMap<Commodity,TextEditor>();
    private Map<Commodity,TextEditor> buyFields = new HashMap<Commodity,TextEditor>();

    Closure onOk;

    protected void populateWindow(Window frame) {
        Market market = session.player.city.market;

        /*frame.getContentContainer().setLayoutManager(new StaticLayout());
        Container main = new Container();
        frame.getContentContainer().addWidget(main);
        main.x = 0;
        main.y = 0;
        main.setMinSize(frame.width, frame.height);
        */

        Container main = frame.getContentContainer();
        
        main.setLayoutManager(new BorderLayout());

        Container table = new Container();
        table.setLayoutData(BorderLayoutData.NORTH);
        table.getAppearance().setMargin(new Spacing(10, 10));
        table.getAppearance().setPadding(new Spacing(5, 5));
        main.addWidget(table);

        table.setLayoutManager(new GridLayout(market.commodities.size() + 1, 3));
        table.getAppearance().setPadding(new Spacing(20, 30));
        table.addWidget(heading("Commodity"));
        table.addWidget(heading("Sell"));
        table.addWidget(heading("Buy"));

        Label lbl = new Label("999999 cr");
        lbl.updateMinSize();
        int minLabelSize = lbl.getMinWidth();
        market.items.each { Commodity c, Price p ->
            table.addWidget(new Label(c.name));

            Container sell = new Container();
            sell.setLayoutManager(new RowLayout());
            sell.getAppearance().setPadding(new Spacing(0, 10));
            Label sellcr = new Label(p.sell + " cr");
            sellcr.setMinSize(minLabelSize, 0);
            sell.addWidget(sellcr);
            sellFields[c] = textEditor();
            sell.addWidget(sellFields[c]);
            Button all = new Button("All");
            all.addButtonPressedListener({
                if (session.player.ship.holds[c])
                    sellFields.get(c).text = session.player.ship.holds[c] as String;
            } as IButtonPressedListener);
            sell.addWidget(all);
            table.addWidget(sell);

            Container buy = new Container();
            buy.setLayoutManager(new RowLayout());
            buy.getAppearance().setPadding(new Spacing(0, 10));
            Label buycr = new Label(p.buy + " cr");
            buycr.setMinSize(minLabelSize, 0);
            buy.addWidget(buycr);
            buyFields[c] = textEditor();
            buy.addWidget(buyFields[c]);
            Button max = new Button("Max");
            max.addButtonPressedListener({
                buyFields.get(c).text = Math.min(session.player.ship.freeHolds, (session.player.credits / p.sell) as int) as String;
            } as IButtonPressedListener);
            buy.addWidget(max);
            table.addWidget(buy);
        }

        Container filling = new Container();
        filling.setLayoutData(BorderLayoutData.CENTER);
        main.addWidget(filling);

        Container okbar = new Container();
        okbar.setLayoutData(BorderLayoutData.SOUTH);
        okbar.getAppearance().setPadding(new Spacing(20, 50));
        okbar.setLayoutManager(new RowLayout(true));
        okbar.setMinSize(0, 100);
        main.addWidget(okbar);

        Button cancel = new Button("Cancel");
        cancel.setMinSize(0, 50);
        cancel.addButtonPressedListener({
            onClose();
        } as IButtonPressedListener);

        okbar.addWidget(cancel);

        Button ok = new Button("OK");
        ok.setMinSize(0, 50);
        ok.addButtonPressedListener({
            if (!validate(frame)) {
                return;
            }
            Map<Commodity,Integer> sellValues = [:];
            sellFields.each { Commodity c, TextEditor editor ->
                sellValues[c] = intValue(editor);
            }
            Map<Commodity,Integer> buyValues = [:];
            buyFields.each { Commodity c, TextEditor editor ->
                buyValues[c] = intValue(editor);
            }
            onOk(sellValues, buyValues);
        } as IButtonPressedListener);

        okbar.addWidget(ok);
    }

    private boolean validate(Window window) {
        boolean valid = true;
        int credits = session.player.credits;
        sellFields.each { Commodity c, TextEditor editor ->
            def val = intValue(editor);
            if (valid && val > session.player.ship.getHolds(c)) {
                new MessageWindow(
                        message: "Cannot sell ${val} units of ${c.name} that you don't have",
                        title: "Validation error",
                        container: window
                );
                valid = false;
            } else {
                credits += val * session.player.city.market.items.get(c).buy;
            }
        };
        if (!valid) return false;
        int count = 0;
        buyFields.each { Commodity c, TextEditor editor ->
            int val = intValue(editor);
            count += val;
            if (valid) {
                credits -= val * session.player.city.market.items.get(c).sell;
                if (credits < 0) {
                    new MessageWindow(
                            message: "Cannot afford ${val} units of ${c.name}",
                            title: "Validation error",
                            container: window
                    );
                    valid = false;
                }
            }
        };
        if (valid && session.player.ship.freeHolds < count) {
            new MessageWindow(
                    message: "Cannot buy more than you can hold",
                    title: "Validation error",
                    container: window
            );
            valid = false;
        }
        return valid;
    }

    private Label heading(String val) {
        Label label = new Label(val);
        label.getAppearance().setAlignment(Alignment.MIDDLE);
        return label;
    }

    private TextEditor textEditor() {
        TextEditor editor = new TextEditor(false);
        editor.setMaxCharacters(4);
        editor.setRestrict("[0-9]*");
        return editor;
    }

    private int intValue(TextEditor editor) {
        if (editor.text && editor.text.length() > 0) {
            return editor.text as int;
        } else {
            return 0;
        }
    }


}