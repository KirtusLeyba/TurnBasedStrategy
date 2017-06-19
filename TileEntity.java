package kleyba.gameDev.turnBased.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import kleyba.gameDev.turnBased.GameConstants;
import kleyba.gameDev.turnBased.Main;
import kleyba.gameDev.turnBased.controls.MouseController;


/**
 * Created by Kirtus on 4/15/2017.
 */
public class TileEntity extends GameEntity
{

  private Image sprite;
  private double x;
  private double y;
  private double pixelX;
  private double pixelY;
  private boolean passable;

  private MouseController mouse = GameConstants.mouse;

  private boolean mousedOver = false;

  public TileEntity(double x, double y, Image sprite)
  {
    this.x = x;
    this.y = y;
    pixelX = GameConstants.gridXtoPixelX(x);
    pixelY = GameConstants.gridYtoPixelY(y);
    this.sprite = sprite;
  }

  @Override
  public void update(long deltaTime)
  {
    pixelX = GameConstants.gridXtoPixelX(x);
    pixelY = GameConstants.gridYtoPixelY(y);
    mousedOver = GameConstants.pointInRect(mouse.mouseX, mouse.mouseY, pixelX, pixelY, GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT);
  }

  @Override
  public void draw(long deltaTime, GraphicsContext graphicsContext)
  {

    graphicsContext.drawImage(sprite, pixelX, pixelY);
    if(mousedOver)
    {
      graphicsContext.setFill(Color.color(1.0, 1.0, 1.0, 0.2));
      graphicsContext.fillRect(pixelX, pixelY,GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT);
    }
  }

  @Override
  public void move(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public boolean getPassable()
  {
    return passable;
  }

  public double getX()
  {
    return x;
  }
  public double getY()
  {
    return y;
  }
}
