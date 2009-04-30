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
import org.fenggui.event.IWindowClosedListener
import org.twdata.trader.Session
import org.fenggui.layout.BorderLayout

/**
 *
 */

public abstract class HudWindow {

    protected GameContainer gameContainer;
    protected FengGuiWrapper feng;
    protected Closure onClose;
    protected Session session;

    public void init() {
        def x = 1024 / 2 - 800 / 2;
        def y = 768 / 2 - 600 / 2;
        this.feng = new FengGuiWrapper(gameContainer, {Display desk ->
            Window frame = new Window(true, false, false);
            desk.addWidget(frame);
            frame.setTitle("Market");
            frame.setX((int) x);
            frame.setY((int) y);
            frame.setSize(800, 600);
            frame.setResizable(false);
            frame.getAppearance().setMargin(Spacing.ZERO_SPACING);
		    frame.getAppearance().setPadding(Spacing.ZERO_SPACING);
            frame.getContentContainer().getAppearance().setMargin(Spacing.ZERO_SPACING);
		    frame.getContentContainer().getAppearance().setPadding(Spacing.ZERO_SPACING);
            frame.getContentContainer().getAppearance().add(new PlainBackground(new Color(255, 255, 255, 0.8f)));
            org.fenggui.render.Binding.getInstance().setUseClassLoader(true); 

            frame.getAppearance().setBorder(new BevelBorder(new Color(0, 0, 0, 0.5f), new Color(255, 255, 255, 0.5f)));
            frame.addWindowClosedListener({
                windowClosed : {
                    feng.loseFocus();
                    onClose();
                }
            } as IWindowClosedListener);
            populateWindow(frame);
            frame.layout();
        });
    }

    protected abstract void populateWindow(Window frame);


    public void render(Graphics g) {
        feng.render(gameContainer, g);
    }

    public void destroy() {
        feng.destroy();
    }


}