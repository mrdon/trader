package org.twdata.trader.ui.state;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import de.lessvoid.nifty.slick.NiftyGameState;

/**
 * A simple test state to display an image and rotate it
 *
 * @author kevin
 */
public class MenuState extends NiftyGameState
{
    /**
     * create the nifty game state.
     *
     * @param slickGameStateId the slick gamestate id for this state
     */
    public MenuState(final int slickGameStateId)
    {
        super(slickGameStateId);
    }

    private enum Option
    {
        START_GAME("Start Game"),
        CREDITS("Credits"),
        INSTRUCTIONS("Instructions"),
        EXIT("Exit");
        private final String text;

        Option(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }

	/** The ID given to this state */
	public static final int ID = 1;
	/** The font to write the message with */
	private Font font;
	/** The index of the selected option */
	private int selected;
	/** The game holding this state */
	private StateBasedGame game;
    private GameContainer gameContainer;

    /**
	 * @see org.newdawn.slick.state.BasicGameState#getID()
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @see org.newdawn.slick.state.BasicGameState#init(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame)
	 */
	public void init(GameContainer container, StateBasedGame game) throws SlickException {
		font = new AngelCodeFont("demo2.fnt","demo2_00.tga");
		this.game = game;
        this.gameContainer = container;
	}

	/**
	 * @see org.newdawn.slick.state.BasicGameState#render(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, org.newdawn.slick.Graphics)
	 */
	public void render(GameContainer container, StateBasedGame game, Graphics g) {
		g.setFont(font);
		//g.setColor(Color.blue);
		//g.drawString("This is State 3", 200, 50);
		g.setColor(Color.white);

		for (int i=0;i< Option.values().length; i++) {
			g.drawString(Option.values()[i].getText(), 400 - (font.getWidth(Option.values()[i].getText())/2), 200+(i*50));
			if (selected == i) {
				g.drawRect(200,190+(i*50),400,50);
			}
		}
	}

	/**
	 * @see org.newdawn.slick.state.BasicGameState#update(org.newdawn.slick.GameContainer, org.newdawn.slick.state.StateBasedGame, int)
	 */
	public void update(GameContainer container, StateBasedGame game, int delta) {
	}

    /**
	 * @see org.newdawn.slick.state.BasicGameState#keyReleased(int, char)
	 */
	public void keyReleased(int key, char c) {
		if (key == Input.KEY_DOWN) {
			selected++;
			if (selected >= Option.values().length) {
				selected = 0;
			}
		}
		if (key == Input.KEY_UP) {
			selected--;
			if (selected < 0) {
				selected = Option.values().length - 1;
			}
		}
        if (key == Input.KEY_RETURN) {
            Option option = Option.values()[selected];
            switch (option) {
                case START_GAME : game.enterState(2, new FadeOutTransition(Color.black), new FadeInTransition(Color.black));
                                  break;
                case EXIT:        gameContainer.exit();
                                  break;
            }
        }
        if (key == Input.KEY_ESCAPE)
        {
            gameContainer.exit();
        }
	}

}
