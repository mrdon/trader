package org.twdata.trader.ui.state

import org.newdawn.slick.state.BasicGameState
import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.Graphics
import org.newdawn.slick.TrueTypeFont
import org.twdata.trader.ui.Starfield
import org.newdawn.slick.Image
import java.awt.Font
import java.awt.geom.Rectangle2D
import org.newdawn.slick.Color
import org.newdawn.slick.Input
import org.twdata.trader.ui.Hud
import org.newdawn.slick.state.transition.FadeOutTransition
import org.newdawn.slick.state.transition.FadeInTransition

/**
 * 
 */

public class ShipState extends BasicGameState {

    private final TrueTypeFont font;
    private final Random rnd = new Random();
    private Starfield starfield;
    private Image planet;
    private final Hud hud;


    def ShipState(TrueTypeFont font, Hud hud)
    {
        this.font = font;
        this.hud = hud;
    }

    public int getID()
    {
        return 2;
    }

    public void init(GameContainer gc, StateBasedGame stateBasedGame)
    {
        gc.getGraphics().setBackground(Color.black);

        Font basefont = new Font("Verdana", Font.BOLD, 20);
        font = new TrueTypeFont(basefont, true);
        starfield = new Starfield(200, new Rectangle2D.Float(0, 768-710, 1024, 768-170));
        planet = new Image("planet-blue.png");
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g)
    {
        g.setBackground(Color.black);
        starfield.draw(g);
        planet.draw(200f, 200f);


        font.drawString(150f, (float)(768f - 175f), "0123456");

        hud.render(g);
    }

    public void update(GameContainer gc, StateBasedGame game, int i)
    {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_ESCAPE))
        {
            game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        } else if (input.isKeyDown(Input.KEY_W)) {
            starfield.warp();
        }
    }

}