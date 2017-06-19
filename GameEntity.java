package kleyba.gameDev.turnBased.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import kleyba.gameDev.turnBased.controls.MouseController;

/**
 * Created by Kirtus on 4/15/2017.
 * Abstract class representing any 'entity' in the game
 */
public abstract class GameEntity
{
  private double x;
  private double y;
  private Paint miniColor;

  /**
   * Update every step
   * @param deltaTime
   * time since last step
   */
  public abstract void update(long deltaTime);

  /**
   * draw every step
   * @param deltaTime
   * time since last step
   */
  public abstract void draw(long deltaTime, GraphicsContext graphicsContext);

  /**
   * Simple jump movement
   * @param x new x location
   * @param y new y location
   */
  public abstract void move(double x, double y);

  public Paint getMiniColor()
  {
    return miniColor;
  }
}
