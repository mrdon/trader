package org.twdata.trader.ui

import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.SlickException
import org.fenggui.Display
import org.fenggui.background.GradientBackground
import org.fenggui.Container
import org.fenggui.layout.RowLayout
import org.fenggui.util.Spacing
import org.fenggui.background.PlainBackground
import org.fenggui.border.PlainBorder
import org.newdawn.slick.state.transition.FadeOutTransition
import org.newdawn.slick.state.transition.FadeInTransition
import org.fenggui.util.Color
import org.fenggui.Button
import org.fenggui.event.IButtonPressedListener
import org.fenggui.composites.Window
import org.fenggui.layout.StaticLayout
import org.fenggui.util.Point
import org.newdawn.slick.Graphics
import org.fenggui.FengGUI
import org.fenggui.border.RoundedBorder
import java.awt.geom.Rectangle2D
import org.fenggui.background.PixmapBackground
import org.fenggui.render.Pixmap
import org.fenggui.border.BevelBorder
import org.twdata.trader.model.City

/**
 *
 */

public class UniverseMap {

    private GameContainer gameContainer;
    FengGuiWrapper feng;
    Starfield field;
    Closure selectedClosure;

    public UniverseMap(GameContainer container, Set<City> cities, Closure selected) throws SlickException {
        this.gameContainer = container;
        this.selectedClosure = selected;

        def x = 1024 / 2 - 800 / 2;
        def y = 768 / 2 - 600 / 2;
        this.feng = new FengGuiWrapper(container, {Display desk ->
            Window frame = new Window(true, false, false);
            desk.addWidget(frame);
            frame.setTitle("Universe Map");
            frame.setX((int) x);
            frame.setY((int) y);
            frame.setSize(800, 600);
            frame.getAppearance().setMargin(Spacing.ZERO_SPACING);
		    frame.getAppearance().setPadding(Spacing.ZERO_SPACING);
            frame.getContentContainer().getAppearance().setMargin(Spacing.ZERO_SPACING);
		    frame.getContentContainer().getAppearance().setPadding(Spacing.ZERO_SPACING);
            org.fenggui.render.Binding.getInstance().setUseClassLoader(true);
            frame.getContentContainer().getAppearance().add(new PixmapBackground(new Pixmap(org.fenggui.render.Binding.getInstance().getTexture("stars-800x600.png"))));
            frame.getContentContainer().setLayoutManager(new StaticLayout());
            
            frame.getAppearance().setBorder(new BevelBorder(new Color(0, 0, 0, 0.5f), new Color(255, 255, 255, 0.5f)));

            cities.each { City c ->
                frame.getContentContainer().addWidget(createSystemButton(c.name, c.coordinate.x, c.coordinate.y));
            }
        });
        this.field = new Starfield(400, new Rectangle2D.Float())
    }

    public void render(Graphics g) {
        feng.render(gameContainer, g);
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
                buttonPressed: { selectedClosure(btn.text) }
        ] as IButtonPressedListener);
        return btn;
    }


}