package org.twdata.trader;

import java.awt.Font;
import org.newdawn.slick.*;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.svg.SimpleDiagramRenderer;
import org.newdawn.slick.svg.InkscapeLoader;
import org.newdawn.slick.svg.Diagram;

public class SimpleGame extends BasicGame
{

    Image hud;
    private TrueTypeFont font;

    public SimpleGame()
    {
        super("Slick2DPath2Glory - SimpleGame");
    }

    @Override
    public void init(GameContainer gc)
			throws SlickException
    {
        gc.getGraphics().setBackground(Color.white);

		InkscapeLoader.RADIAL_TRIANGULATION_LEVEL = 2;

        hud = new Image("hud.png");
        gc.getGraphics().setBackground(Color.black);
        Font basefont = new Font("Verdana", Font.BOLD, 20);
        font = new TrueTypeFont(basefont, true);


    }

    @Override
    public void update(GameContainer gc, int delta)
			throws SlickException
    {
        Input input = gc.getInput();

        if (input.isKeyDown(Input.KEY_ESCAPE))
        {
            System.exit(0);
        }


    }

    public void render(GameContainer gc, Graphics g)
			throws SlickException 
    {
        //g.scale(.1f,.1f);

        hud.draw(0,0);
        font.drawString(150f, 768f - 175f, "0123456");

        //g.resetTransform();

    }

    public static void main(String[] args)
			throws SlickException
    {
        Renderer.setRenderer(Renderer.VERTEX_ARRAY_RENDERER);
			Renderer.setLineStripRenderer(Renderer.QUAD_BASED_LINE_STRIP_RENDERER);
         AppGameContainer app =
			new AppGameContainer(new SimpleGame());

         app.setDisplayMode(1024, 768, true);
         app.start();
    }
}