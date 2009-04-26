package org.twdata.trader.ui;

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
        addState(new MenuState(1));
        addState(new ShipState(font, hud));
        
    }

}