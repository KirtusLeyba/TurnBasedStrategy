package kleyba.gameDev.turnBased.controls;

import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class KeyboardController
{
  public boolean enterPressed = false;
  public KeyBoardPressedHandler kBoardPress;
  public KeyBoardReleasedHandler kBoardRelease;

  public KeyboardController()
  {
    kBoardPress = new KeyBoardPressedHandler();
    kBoardRelease = new KeyBoardReleasedHandler();
  }

  private class KeyBoardPressedHandler implements EventHandler<KeyEvent>
  {
    @Override
    public void handle(KeyEvent event)
    {
      if(event.getCode() == KeyCode.ENTER)
      {
        enterPressed = true;
      }
    }
  }

  private class KeyBoardReleasedHandler implements EventHandler<KeyEvent>
  {

    @Override
    public void handle(KeyEvent event)
    {
      if(event.getCode() == KeyCode.ENTER)
      {
        enterPressed = false;
      }
    }
  }

}
