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
import org.newdawn.slick.Graphics

/**
 *
 */

public class UniverseMap extends HudWindow {

    Closure onCitySelect;
    List<Button> cities = [] as List<Button>;

    protected void populateWindow(Window frame) {
        frame.getContentContainer().setLayoutManager(new StaticLayout()); 
        frame.getContentContainer().getAppearance().add(new PixmapBackground(new Pixmap(org.fenggui.render.Binding.getInstance().getTexture("stars-800x600.png"))));
        session.game.cities.values().each { City c ->
            Button btn = createSystemButton(c.name, c.coordinate.x, c.coordinate.y);
            cities.add(btn);
            if (session.player.city == c) {
                btn.enabled = false;
            }
            frame.getContentContainer().addWidget(btn);
        }
    }

    private Button createSystemButton(String text, int x, int y) {
        Button btn = FengGUI.newInstance().createButton(text);

        btn.getAppearance().setPadding(new Spacing(5, 5));
        btn.getAppearance().setBorder(new PlainBorder(Color.BLACK, 1));
        btn.setSizeToMinSize();
        btn.setX(x - btn.width / 2 as int);
        btn.setY(y - btn.height/ 2 as int);
        btn.setTraversable(true);
        btn.addButtonPressedListener([
                buttonPressed: { onCitySelect(btn.text) }
        ] as IButtonPressedListener);
        return btn;
    }
    public void render(Graphics g) {
        super.render(g);

        /*
        g.color = org.newdawn.slick.Color.white;
        cities.each { Button btn ->
            int radius = session.player.ship.type.warpHops;
            g.drawOval( (btn.displayX + btn.width / 2 as int) - radius, (768 - btn.displayY - btn.height / 2 as int) - radius, radius * 2, radius * 2);
        }
        */
    }

}