package kleyba.gameDev.turnBased.controls;

import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class MouseController
{
  public double mouseX;
  public double mouseY;
  public boolean mouseLeftClicked = false;
  public boolean mouseRightClicked = false;
  public MousePressedHandler mousePress = new MousePressedHandler();
  public MouseReleasedHandler mouseRelease = new MouseReleasedHandler();
  public MouseMoveHandler mouseMove = new MouseMoveHandler();

  private class MousePressedHandler implements EventHandler<MouseEvent>
  {
    @Override
    public void handle(MouseEvent event)
    {
      if(event.getButton().equals(MouseButton.PRIMARY))
      {
        mouseLeftClicked = true;
      }
      if(event.getButton().equals(MouseButton.SECONDARY))
      {
        mouseRightClicked = true;
      }
    }
  }

  private class MouseReleasedHandler implements EventHandler<MouseEvent>
  {
    @Override
    public void handle(MouseEvent event)
    {
      if(event.getButton().equals(MouseButton.PRIMARY))
      {
        mouseLeftClicked = false;
      }
      if(event.getButton().equals(MouseButton.SECONDARY))
      {
        mouseRightClicked = false;
      }
    }
  }

  private class MouseMoveHandler implements EventHandler<MouseEvent>
  {

    @Override
    public void handle(MouseEvent event)
    {
      mouseX = event.getX();
      mouseY = event.getY();
    }
  }

}
