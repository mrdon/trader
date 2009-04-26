package org.twdata.trader;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.svg.InkscapeLoader;
import org.twdata.trader.ui.Starfield;
import org.twdata.trader.ui.Hud;
import org.twdata.trader.ui.state.MenuState;
import org.twdata.trader.ui.state.ShipState;

public class TraderGame extends StateBasedGame
{


    public TraderGame()
    {
        super("Space Trader");
    }

    public void initStatesList(GameContainer gameContainer) throws SlickException
    {
        Font basefont = new Font("Verdana", Font.BOLD, 20);
        TrueTypeFont font = new TrueTypeFont(basefont, true);
        Hud hud = new Hud(font);
        addState(new MenuState());
        addState(new ShipState(font, hud));
        
    }

    public static void main(String[] args)
			throws SlickException
    {
        //Renderer.setRenderer(Renderer.VERTEX_ARRAY_RENDERER);
//			Renderer.setLineStripRenderer(Renderer.QUAD_BASED_LINE_STRIP_RENDERER);
         AppGameContainer app =
			new AppGameContainer(new TraderGame());

         app.setDisplayMode(1024, 768, false);
         app.setTargetFrameRate(60);
         app.start();
    }
}