package org.twdata.trader.ui

import org.newdawn.slick.Image
import org.newdawn.slick.Graphics
import org.newdawn.slick.TrueTypeFont

/**
 * 
 */

public class Hud {
    private final Image hud;
    private final TrueTypeFont font;

    public Hud(TrueTypeFont font) {
        hud = new Image("hud.png");
        this.font = font;
    }

    public void render(Graphics g) {
        hud.draw(0,0);
    }

}