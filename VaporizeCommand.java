package kleyba.gameDev.turnBased.logic.commands;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import kleyba.gameDev.turnBased.GameConstants;
import kleyba.gameDev.turnBased.entities.TileEntity;
import kleyba.gameDev.turnBased.entities.UnitEntity;
import kleyba.gameDev.turnBased.logic.UnitManager;

import java.util.ArrayList;

/**
 * Created by Kirtus on 4/19/2017.
 */
public class VaporizeCommand extends GameCommand
{

  private UnitEntity unit;
  private TileEntity[] grid;
  private int framesSinceLastStep = 0;
  private int animTime = 10;
  private boolean activated = false;
  private ArrayList<UnitEntity> unitsToDamage = new ArrayList<UnitEntity>();
  private double attackRadius = 3;
  private double moveCost = 4;

  public VaporizeCommand(UnitEntity unit, TileEntity[] grid)
  {
    this.unit = unit;
    this.grid = grid;
  }

  @Override
  public void draw(double deltaTime, GraphicsContext graphicsContext)
  {
    if(unit.isSelected())
    {
      for (UnitEntity unita : unitsToDamage)
      {
        double tX = GameConstants.gridXtoPixelX(unita.getX());
        double tY = GameConstants.gridYtoPixelY(unita.getY());
        graphicsContext.setStroke(Color.AQUA);
        graphicsContext.strokeOval(tX, tY, GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT);
      }
    }
  }

  @Override
  public void update(double deltaTime)
  {
    if(activated && unit.getMovesLeft() >= moveCost)
    {
      unit.setMovesLeft(unit.getMovesLeft() - moveCost);
      for(UnitEntity unita: unitsToDamage)
      {
        unita.setHealth(unita.getHealth() - 1);
      }
      activated = false;
    }
    else
    {
      activated = false;
    }
  }

  @Override
  public void execute()
  {
    activated = true;
  }

  @Override
  public boolean isActivated()
  {
    return activated;
  }

  @Override
  public void setActivated(boolean activated)
  {
    this.activated = activated;
  }

  @Override
  public void prepare(double x, double y, UnitManager unitManager)
  {
    unitsToDamage.clear();
    for(UnitEntity unita: unitManager.getUnits())
    {
      if(GameConstants.distSquaredBetweenPoints(unit.getX(),unit.getY(),unita.getX(), unita.getY()) < attackRadius)
      {
        unitsToDamage.add(unita);
      }
    }
  }
}
