package kleyba.gameDev.turnBased.entities;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class WaterTile extends TileEntity
{
  private boolean passable = false;

  public WaterTile(double x, double y, Image sprite)
  {
    super(x, y, sprite);
  }

  public boolean getPassable()
  {
    return passable;
  }

  @Override
  public Color getMiniColor()
  {
    return Color.color(0.04,0.70,0.62,0.75);
  }
}
