package kleyba.gameDev.turnBased.logic;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import kleyba.gameDev.turnBased.entities.UnitEntity;

import java.util.ArrayList;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class GamePlayer
{
  private String playerName;
  private Color playerColor;
  private boolean isMyTurn;
  private ArrayList<UnitEntity> units;
  private Paint miniColor;

  public GamePlayer(String playerName, Color playerColor, Color miniColor)
  {
    this.playerName = playerName;
    this.playerColor = playerColor;
    this.miniColor = miniColor;
    isMyTurn = false;
    units = new ArrayList<UnitEntity>();
  }

  public void addUnit(UnitEntity unit)
  {
    units.add(unit);
  }
  public void removeUnit(UnitEntity unit)
  {
    units.remove(unit);
  }
  public ArrayList<UnitEntity> getUnits()
  {
    return units;
  }


  public String getPlayerName()
  {
    return playerName;
  }
  public Color getPlayerColor()
  {
    return playerColor;
  }

  public void setTurn(boolean turn)
  {
    isMyTurn = turn;
  }
  public boolean getTurn()
  {
    return isMyTurn;
  }

  public Paint getMiniColor()
  {
    return miniColor;
  }
}
