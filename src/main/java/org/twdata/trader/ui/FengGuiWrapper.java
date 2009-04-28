package org.twdata.trader.ui;


import org.fenggui.event.mouse.MouseButton;
import org.fenggui.event.IButtonPressedListener;
import org.fenggui.event.ButtonPressedEvent;
import org.fenggui.example.Everything;
import org.fenggui.render.lwjgl.EventHelper;
import org.fenggui.render.lwjgl.LWJGLBinding;
import org.fenggui.render.Font;
import org.fenggui.composites.Window;
import org.fenggui.layout.StaticLayout;
import org.fenggui.layout.RowLayout;
import org.fenggui.ComboBox;
import org.fenggui.Button;
import org.fenggui.Container;
import org.fenggui.Display;
import org.fenggui.background.PlainBackground;
import org.fenggui.background.GradientBackground;
import org.fenggui.util.Spacing;
import org.fenggui.util.Color;
import org.fenggui.border.BevelBorder;
import org.fenggui.border.PlainBorder;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.InputListener;
import org.newdawn.slick.opengl.SlickCallable;
import groovy.lang.Closure;

/**
 * FengWrapper Example.
 *
 * This class demonstrates a way to use FengGUI inside slick.
 * It uses SlickCallable to handle the rendering, and uses part of FengGUI's LWJGL binding, while converting most of it into a simple
 * InputListener.
 *
 * This was coded fairly quickly and may have bugs. If any are found, post them on the forum please
 * @author Dantarion
 *
 */
public class FengGuiWrapper implements InputListener
{
   org.fenggui.Display desk = null;
   GameContainer container;
   Input input;

   public FengGuiWrapper(GameContainer container, Closure guiBuilder)
   {
      this.container = container;
      container.getInput().addPrimaryListener(this);
      container.getInput().enableKeyRepeat(500, 30);
      LWJGLBinding binding = new LWJGLBinding();
      desk = new org.fenggui.Display(binding);
       guiBuilder.call(desk);
        desk.layout();
   }

    public void destroy() {
        container.getInput().removeListener(this);
    }


    public void render(GameContainer container, Graphics g)
   {
      SlickCallable.enterSafeBlock();
      desk.display();
      SlickCallable.leaveSafeBlock();
   }

   private MouseButton SlickButtonToFeng(int button)
   {
      MouseButton pressed;
      switch (button)
      {
         case (1):
            pressed = MouseButton.RIGHT;
            break;
         case (2):
            pressed = MouseButton.MIDDLE;
            break;
         default:
            pressed = MouseButton.LEFT;
            break;
      }
      return pressed;
   }

   public void controllerButtonPressed(int controller, int button)
   {

   }

   public void controllerButtonReleased(int controller, int button)
   {

   }

   public void controllerDownPressed(int controller)
   {

   }

   public void controllerDownReleased(int controller)
   {

   }

   public void controllerLeftPressed(int controller)
   {

   }

   public void controllerLeftReleased(int controller)
   {

   }

   public void controllerRightPressed(int controller)
   {

   }

   public void controllerRightReleased(int controller)
   {

   }

   public void controllerUpPressed(int controller)
   {

   }

   public void controllerUpReleased(int controller)
   {

   }

   public void inputEnded()
   {

   }

   public boolean isAcceptingInput()
   {
      return true;
   }

   public void keyPressed(int key, char c)
   {
      desk.fireKeyPressedEvent(EventHelper.mapKeyChar(), EventHelper.mapEventKey());
      desk.fireKeyTypedEvent(EventHelper.mapKeyChar());

   }

   public void keyReleased(int key, char c)
   {
      desk.fireKeyReleasedEvent(EventHelper.mapKeyChar(), EventHelper.mapEventKey());

   }

   public void mouseMoved(int oldx, int oldy, int newx, int newy)
   {
      if (!container.getInput().isMouseButtonDown(0))
         desk.fireMouseMovedEvent(newx, container.getHeight() - newy);
      else
         desk.fireMouseDraggedEvent(newx, container.getHeight() - newy, MouseButton.LEFT);
   }

   public void mousePressed(int button, int x, int y)
   {

      desk.fireMousePressedEvent(x, container.getHeight() - y, SlickButtonToFeng(button), 1);

   }

   public void mouseReleased(int button, int x, int y)
   {
      desk.fireMouseReleasedEvent(x, container.getHeight() - y, SlickButtonToFeng(button), 1);
   }

   public void mouseWheelMoved(int change)
   {
      desk.fireMouseWheel(container.getInput().getMouseX(), container.getHeight()
            - container.getInput().getMouseY(), change > 0, 1);

   }

    public void mouseClicked(int button, int x, int y, int clickCount)
    {
        mousePressed(button, x, y);
    }

    public void setInput(Input input)
   {
      this.input = input;
   }
}