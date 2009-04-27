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
import org.twdata.trader.ui.UniverseMap
import org.twdata.trader.ui.TraderGame
import org.twdata.trader.Session
import org.twdata.trader.model.City

/**
 * 
 */

public class ShipState extends BasicGameState {

    private final TrueTypeFont font;
    private final Random rnd = new Random();
    private Starfield starfield;
    private Image planet;
    private final Hud hud;
    private final UniverseMap universeMap;
    private boolean globalMap = false;
    private Session session;


    def ShipState(TrueTypeFont font, Hud hud, Session session)
    {
        this.font = font;
        this.hud = hud;
        this.session = session;
    }

    public int getID()
    {
        return 2;
    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        super.enter(gameContainer, stateBasedGame);
        globalMap = false;
    }



    public void init(GameContainer gc, StateBasedGame stateBasedGame)
    {
        gc.getGraphics().setBackground(Color.black);

        Font basefont = new Font("Verdana", Font.BOLD, 20);
        font = new TrueTypeFont(basefont, true);
        starfield = new Starfield(200, new Rectangle2D.Float(0, 768-710, 1024, 768-170));
        planet = new Image("planet-blue.png");
        universeMap = new UniverseMap(gc, session.game.cities.values() as Set<City>, {String cityName ->
            globalMap = false;
            starfield.warp();
            session.executeCommand("move", [toCity: cityName]);
        });
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g)
    {
        g.setBackground(Color.black);
        starfield.draw(g);
        if (!starfield.inWarp())
            planet.draw(200f, 200f);
        g.setFont(font);
        g.setColor(Color.green); 

        hud.render(g);
        g.drawString(session.player.city.name, 150f, 680f);
        if (globalMap) {
            universeMap.render(g);
        }
    }

    public void update(GameContainer gc, StateBasedGame game, int i)
    {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_ESCAPE))
        {
            game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
        } else if (input.isKeyDown(Input.KEY_W)) {
            starfield.warp();
        } else if (input.isKeyPressed(Input.KEY_M)) {
            globalMap = !globalMap;
        }
    }

}