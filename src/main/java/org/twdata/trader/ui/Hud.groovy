package org.twdata.trader.ui

import org.fenggui.Button
import org.fenggui.Container
import org.fenggui.Display
import org.fenggui.FengGUI
import org.fenggui.layout.RowLayout
import org.fenggui.util.Spacing
import org.newdawn.slick.GameContainer
import org.newdawn.slick.Graphics
import org.newdawn.slick.Image
import org.newdawn.slick.TrueTypeFont
import org.twdata.trader.Session
import org.twdata.trader.ui.FengGuiWrapper
import org.fenggui.layout.BorderLayout
import org.fenggui.layout.BorderLayoutData
import org.twdata.trader.ui.state.ShipState
import org.fenggui.event.IButtonPressedListener
import java.awt.Font
import org.fenggui.TextEditor
import org.twdata.trader.event.events.CommandExecutedEvent
import org.twdata.trader.event.TraderEventListener
import org.fenggui.composites.TextArea
import org.fenggui.ScrollContainer

/**
 * 
 */

public class Hud {
    private final Image hud;
    private FengGuiWrapper feng;
    private GameContainer container;
    private ShipState state;
    private TextEditor console;

    public Hud(GameContainer container, ShipState state, Session session) {
        hud = new Image("hud.png");
        this.container = container;
        this.state = state;
        this.feng = new FengGuiWrapper(container, {Display desk ->
            Container panel = FengGUI.createContainer(desk);
            panel.setX(160);
            panel.setY(10);
            panel.setSize(710, 185);
            panel.setLayoutManager(new BorderLayout());
            Container toolbar = FengGUI.createContainer(panel);
            toolbar.setLayoutData(BorderLayoutData.NORTH);
            toolbar.setLayoutManager(new RowLayout(true));
            Button warp = FengGUI.createButton(toolbar, "Warp");
            warp.addButtonPressedListener([
                            buttonPressed: { state.displayUniverseMap(); }
                    ] as IButtonPressedListener);
            warp.getAppearance().setPadding(new Spacing(5, 5));

            Button market = FengGUI.createButton(toolbar, "Market");
            market.addButtonPressedListener([
                            buttonPressed: { state.displayMarket(); }
                    ] as IButtonPressedListener);
            market.getAppearance().setPadding(new Spacing(5, 5));

            console = new TextEditor(true);
            session.eventManager.register(new CommandListener(onEvent: { CommandExecutedEvent evt ->
                    console.appendText("- ${evt.command.toString()}\n");
                    console.setCursorIndex(console.getText().length());
            }));
            console.setWordWarp(true);
            console.setEnabled(false); 
            ScrollContainer sc = new ScrollContainer();
            sc.addWidget(console); 
            sc.setLayoutData(BorderLayoutData.CENTER);
		    panel.addWidget(sc);

        });
    }

    public void loseFocus() {
        this.feng.loseFocus();
    }

    public void gainFocus() {
        this.feng.gainFocus();
    }
    
    public void destroy() {
        this.feng.destroy();
    }

    public void render(Graphics g) {
        hud.draw(0,0);

        feng.render(container, g);
    }

}

class CommandListener {
    Closure onEvent;
    @TraderEventListener
    public void onCommand(CommandExecutedEvent event) {
        onEvent(event);
    }
}