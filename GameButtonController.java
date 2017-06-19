package kleyba.gameDev.turnBased.controls;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import kleyba.gameDev.turnBased.GameConstants;

/**
 * Created by Kirtus on 4/19/2017.
 */
public class GameButtonController implements EventHandler<ActionEvent>
{
  private boolean buttonPressed;
  private int source = 0;

  @Override
  public void handle(ActionEvent event)
  {
    buttonPressed = true;
    for(int i = 0; i< GameConstants.commandCardButtons.length; i++)
    {
      if(event.getSource().equals(GameConstants.commandCardButtons[i]))
      {
        source = i;
      }
    }
  }

  public boolean getButtonPressed()
  {
    return buttonPressed;
  }
  public int getSource()
  {
    return source;
  }
  public void setButtonPressed(boolean buttonPressed)
  {
    this.buttonPressed = buttonPressed;
  }
}
