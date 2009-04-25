package org.twdata.trader;

import java.awt.Font;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.svg.SimpleDiagramRenderer;
import org.newdawn.slick.svg.InkscapeLoader;
import org.newdawn.slick.svg.Diagram;
import org.twdata.trader.ui.Starfield;

public class SimpleGame extends BasicGame
{

    Image hud;
    private TrueTypeFont font;
    private final Random rnd = new Random();
    private Starfield starfield;
    private Image planet;

    public SimpleGame()
    {
        super("Slick2DPath2Glory - SimpleGame");
    }

    @Override
    public void init(GameContainer gc)
			throws SlickException
    {
        gc.getGraphics().setBackground(Color.black);

		InkscapeLoader.RADIAL_TRIANGULATION_LEVEL = 2;

        hud = new Image("hud.png");
        Font basefont = new Font("Verdana", Font.BOLD, 20);
        font = new TrueTypeFont(basefont, true);
        starfield = new Starfield(200, new Rectangle2D.Float(0, 768-710, 1024, 768-170));
        planet = new Image("planet-red.png");


    }

    @Override
    public void update(GameContainer gc, int delta)
			throws SlickException
    {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_ESCAPE))
        {
            gc.exit();
        } else if (input.isKeyDown(Input.KEY_W)) {
            starfield.warp();
        }


    }

    public void render(GameContainer gc, Graphics g)
			throws SlickException 
    {
        //g.scale(.1f,.1f);
        g.setBackground(Color.black);
        starfield.draw(g);
        planet.draw(200f, 200f);
        

        font.drawString(150f, 768f - 175f, "0123456");

        hud.draw(0,0);
        //g.resetTransform();

    }

    public static void main(String[] args)
			throws SlickException
    {
        //Renderer.setRenderer(Renderer.VERTEX_ARRAY_RENDERER);
//			Renderer.setLineStripRenderer(Renderer.QUAD_BASED_LINE_STRIP_RENDERER);
         AppGameContainer app =
			new AppGameContainer(new SimpleGame());

         app.setDisplayMode(1024, 768, false);
         app.setTargetFrameRate(60);
         app.start();
    }
}