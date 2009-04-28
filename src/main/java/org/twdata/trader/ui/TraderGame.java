package org.twdata.trader.ui;

import java.awt.Font;

import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;
import org.twdata.trader.ui.Hud;
import org.twdata.trader.ui.state.MenuState;
import org.twdata.trader.ui.state.ShipState;
import org.twdata.trader.guice.SimpleContainer;
import org.twdata.trader.Session;

public class TraderGame extends StateBasedGame
{

    private final Session session;
    private final SimpleContainer container;

    public TraderGame()
    {
        super("Space Trader");
        container = new SimpleContainer();
        session = container.getSessionFactory().create("mrdon");
    }

    public void initStatesList(GameContainer gameContainer) throws SlickException
    {
        Font basefont = new Font("Verdana", Font.BOLD, 20);
        TrueTypeFont font = new TrueTypeFont(basefont, true);

        addState(new MenuState(1));
        addState(new ShipState(font, session));
        
    }

    public Session getSession() {
        return session;
    }

    public SimpleContainer getSimpleContainer() {
        return container;
    }
}