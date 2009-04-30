package org.twdata.trader.ui

import org.fenggui.Button
import org.fenggui.FengGUI
import org.fenggui.background.PixmapBackground
import org.fenggui.border.PlainBorder
import org.fenggui.composites.Window
import org.fenggui.event.IButtonPressedListener
import org.fenggui.render.Binding
import org.fenggui.render.Pixmap
import org.fenggui.util.Color
import org.fenggui.util.Spacing
import org.twdata.trader.model.City
import org.twdata.trader.ui.HudWindow
import org.fenggui.layout.StaticLayout
import org.fenggui.Label
import org.fenggui.background.PlainBackground
import org.fenggui.background.GradientBackground
import org.fenggui.layout.Alignment

/**
 *
 */

public class UniverseMap extends HudWindow {

    Closure onCitySelect;

    protected void populateWindow(Window frame) {
        frame.getContentContainer().setLayoutManager(new StaticLayout()); 
        frame.getContentContainer().getAppearance().add(new PixmapBackground(new Pixmap(org.fenggui.render.Binding.getInstance().getTexture("stars-800x600.png"))));
        session.game.cities.values().each { City c ->
            Button btn = createSystemButton(c.name, c.coordinate.x, c.coordinate.y);
            if (session.player.city == c) {
                btn.enabled = false;
            }
            frame.getContentContainer().addWidget(btn);
        }
    }

    private Button createSystemButton(String text, int x, int y) {
        Button btn = FengGUI.newInstance().createButton(text);
        btn.setX(x);
        btn.setY(y);
        btn.getAppearance().setPadding(new Spacing(5, 5));
        btn.getAppearance().setBorder(new PlainBorder(Color.BLACK, 1));
        btn.setSizeToMinSize();
        btn.setTraversable(true);
        btn.addButtonPressedListener([
                buttonPressed: { onCitySelect(btn.text) }
        ] as IButtonPressedListener);
        return btn;
    }
}