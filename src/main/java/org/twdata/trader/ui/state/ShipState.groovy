package org.twdata.trader.ui.state

import org.newdawn.slick.state.BasicGameState
import org.newdawn.slick.GameContainer
import org.newdawn.slick.state.StateBasedGame
import org.newdawn.slick.Graphics
import org.newdawn.slick.TrueTypeFont
import org.twdata.trader.ui.Starfield
import org.newdawn.slick.Image
import java.awt.geom.Rectangle2D
import org.newdawn.slick.Color
import org.newdawn.slick.Input
import org.twdata.trader.ui.Hud
import org.newdawn.slick.state.transition.FadeOutTransition
import org.newdawn.slick.state.transition.FadeInTransition
import org.twdata.trader.ui.UniverseMap
import org.twdata.trader.Session
import org.twdata.trader.model.City

/**
 * 
 */

public class ShipState extends BasicGameState {

    private final TrueTypeFont font;
    private final Random rnd = new Random();
    private Starfield starfield;
    private Map<String,Image> planets;
    private final Hud hud;
    private final UniverseMap universeMap;
    private Session session;
    private GameContainer gameContainer;


    def ShipState(TrueTypeFont font, Session session)
    {
        this.font = font;
        this.session = session;
    }

    public int getID()
    {
        return 2;
    }

    public void enter(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        super.enter(gameContainer, stateBasedGame);
        this.hud = new Hud(gameContainer, this, session);
        this.gameContainer = gameContainer;
    }

    public void leave(GameContainer gameContainer, StateBasedGame stateBasedGame) {
        super.leave(gameContainer, stateBasedGame);
        this.hud.destroy();
        hideUniverseMap();
        gameContainer = null;
    }





    public void init(GameContainer gc, StateBasedGame stateBasedGame)
    {
        gameContainer = gc;
        gc.getGraphics().setBackground(Color.black);

        starfield = new Starfield(200, new Rectangle2D.Float(0, 768-710, 1024, 768-170));
        planets = [
                "land" : new Image("planet-land.png"),
                "blue" : new Image("planet-blue.png"),
                "red" : new Image("planet-red.png")
        ] as Map<String,Image>;

    }

    public void displayUniverseMap() {
        universeMap = new UniverseMap(gameContainer, session.game.cities.values() as Set<City>, {String cityName ->
            hideUniverseMap();
            starfield.warp();
            session.executeCommand("move", [toCity: cityName]);

        });
    }

    public void hideUniverseMap() {
        if (universeMap) {
            universeMap.destroy();
            universeMap = null;
        }
    }

    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g)
    {
        g.setBackground(Color.black);
        starfield.draw(g);
        if (!starfield.inWarp())
            planets[session.player.city.imageId].draw(200f, 200f);
        g.setFont(font);
        g.setColor(Color.green); 

        if (hud) hud.render(g);
        //g.drawString(session.player.city.name, 150f, 680f);
        //g.drawString(session.player.credits + " cr", 150f, 580f);
        if (universeMap) {
            universeMap.render(g);
        }
    }

    public void update(GameContainer gc, StateBasedGame game, int i)
    {
        Input input = gc.getInput();

        if (input.isKeyPressed(Input.KEY_ESCAPE))
        {
            if (universeMap) {
                hideUniverseMap();
            } else {
                game.enterState(1, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
            }
        } else if (input.isKeyDown(Input.KEY_W)) {
            starfield.warp();
        } else if (input.isKeyPressed(Input.KEY_M)) {
            if (!universeMap) {
                displayUniverseMap();
            } else {
                hideUniverseMap();
            }
        }
    }

}