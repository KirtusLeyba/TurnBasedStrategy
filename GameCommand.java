package kleyba.gameDev.turnBased.logic.commands;

import javafx.scene.canvas.GraphicsContext;
import kleyba.gameDev.turnBased.entities.TileEntity;
import kleyba.gameDev.turnBased.entities.UnitEntity;
import kleyba.gameDev.turnBased.logic.UnitManager;

import java.util.ArrayList;

/**
 * Created by Kirtus on 4/18/2017.
 * The Game Command Class represents a command or action that a unit may take.
 */
public abstract class GameCommand
{
  private ArrayList<UnitEntity> targets;
  private double damageDealt;
  private boolean active;
  private ArrayList<TileEntity> tilesEffected;

  public abstract void draw(double deltaTime, GraphicsContext graphicsContext);
  public abstract void update(double deltaTime);
  public abstract void execute();

  public abstract boolean isActivated();
  public abstract void setActivated(boolean activated);
  public abstract void prepare(double x, double y, UnitManager unitManager);
}
