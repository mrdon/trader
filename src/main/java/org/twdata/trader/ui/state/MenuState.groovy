package org.twdata.trader.ui.state

import org.fenggui.Container
import org.fenggui.Display
import org.fenggui.background.GradientBackground
import org.fenggui.background.PlainBackground
import org.fenggui.border.PlainBorder
import org.fenggui.layout.RowLayout
import org.fenggui.util.Spacing
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Input
import org.newdawn.slick.SlickException
import org.newdawn.slick.state.BasicGameState
import org.newdawn.slick.state.StateBasedGame
import org.twdata.trader.ui.FengGuiWrapper
import org.fenggui.util.Color
import org.fenggui.Button
import org.fenggui.event.IActivationListener
import org.fenggui.event.ActivationEvent
import org.fenggui.event.IButtonPressedListener
import org.newdawn.slick.state.transition.FadeOutTransition
import org.newdawn.slick.state.transition.FadeInTransition;

/**
 * A simple test state to display an image and rotate it
 *
 * @author kevin
 */
public class MenuState extends BasicGameState {
    private FengGuiWrapper feng;

    /**
     * create the nifty game state.
     *
     * @param slickGameStateId the slick gamestate id for this state
     */
    public MenuState(final int slickGameStateId) {
        //super(slickGameStateId);
    }

    /** The ID given to this state   */
    public static final int ID = 1;

    /** The game holding this state   */
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
        this.game = game;
        this.gameContainer = container;
        this.feng = new FengGuiWrapper(container, {Display desk ->
            Container frame = new Container(new RowLayout(false));
            desk.addWidget(frame);
            frame.setX((int) (1024 / 2 - 500 / 2));
            frame.setY((int) (768 / 2 - 500 / 2));
            frame.setSize(500, 500);

            frame.addWidget(createMenuButton("Start Game", {
                game.enterState(2, new FadeOutTransition(org.newdawn.slick.Color.black), new FadeInTransition(org.newdawn.slick.Color.black));
            }));
            frame.addWidget(createMenuButton("Load Game"));
            frame.addWidget(createMenuButton("Save Game"));
            frame.addWidget(createMenuButton("Credits"));
            frame.addWidget(createMenuButton("Exit", {
                gameContainer.exit();
            }));
        });
        //fromXml("test.xml");
    }

    private Button createMenuButton(String text, Closure listener = null) {
        Button btn = new Button(text);
        btn.getAppearance().setMargin(new Spacing(5, 0));
        btn.setTraversable(true);
        if (listener)
            btn.addButtonPressedListener([buttonPressed: listener] as IButtonPressedListener);
        return btn;
    }


    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        feng.render(gameContainer, graphics);
    }

    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {

    }


    /**
     * @see org.newdawn.slick.state.BasicGameState#keyReleased(int, char)
     */
    public void keyReleased(int key, char c) {
        super.keyReleased(key, c);
        if (key == Input.KEY_ESCAPE) {
            gameContainer.exit();
        }
    }

}
