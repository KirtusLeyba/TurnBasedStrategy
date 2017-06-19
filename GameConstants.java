package kleyba.gameDev.turnBased;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import kleyba.gameDev.turnBased.controls.GameButtonController;
import kleyba.gameDev.turnBased.controls.KeyboardController;
import kleyba.gameDev.turnBased.controls.MouseController;


/**
 * Created by Kirtus on 4/15/2017.
 */
public class GameConstants
{
  //Interface Constants
  public static final double DEFAULT_WIDTH = 896;
  public static final double DEFAULT_HEIGHT = 768;
  public static final MouseController mouse = new MouseController();
  public static final KeyboardController kBoard = new KeyboardController();
  public static final StackPane layers = new StackPane();
  public static final Canvas gameCanvas = new Canvas();
  public static final BorderPane gameBorder = new BorderPane();
  public static final HBox gameButtonBox = new HBox();
  public static final ImageView overlayImageView = new ImageView(new Image("file:resources/kleyba/gameDev/turnBased/FileIO/GUI/overlay.png"));
  public static final Scene mainScene = new Scene(layers);
  public static double screenX = 0;
  public static double screenY = 0;

  //Buttons
  public static final Button endTurnButton = new Button("End Turn");
  //Command Card
  public static final Button commandCardNW = new Button("");
  public static final Button commandCardN = new Button("");
  public static final Button commandCardNE = new Button("");
  public static final Button commandCardE = new Button("");
  public static final Button commandCardSE = new Button("");
  public static final Button commandCardS = new Button("");
  public static final Button commandCardSW = new Button("");
  public static final Button commandCardW = new Button("");
  public static final Button commandCardCenter = new Button("");
  public static final Button[] commandCardButtons = {commandCardNW, commandCardN, commandCardNE, commandCardW, commandCardCenter
                                                      , commandCardE, commandCardSW, commandCardS, commandCardSE};
  public static final GameButtonController gameButtonController = new GameButtonController();

  //Game Optimization Constants
  public static final double frameLengthNano = (1/60)*(10e9);

  //Gameplay Constants
  public static final double TILE_WIDTH = 32;
  public static final double TILE_HEIGHT = 32;

  //Constant Methods

  /**
   * returns true if a point is within a given rectangle
   * @param x
   * @param y
   * @param xRect
   * @param yRect
   * @param width
   * @param height
   * @return
   */
  public static boolean pointInRect(double x,double y,double xRect,double yRect, double width, double height)
  {
    boolean output = false;
    if(x >= xRect && x < xRect + width && y >= yRect && y < yRect + height)
    {
      output = true;
    }
    return output;
  }

  public static double gridXtoPixelX(double gridX)
  {
    return gridX*TILE_WIDTH + screenX;
  }
  public static double gridYtoPixelY(double gridY)
  {
    return gridY*TILE_HEIGHT + screenY;
  }

  public static int pixelXtoGridX(double pixelX)
  {
    return (int)((pixelX - screenX)/TILE_HEIGHT);
  }
  public static int pixelYtoGridY(double pixelY)
  {
    return (int)((pixelY - screenY)/TILE_WIDTH);
  }

  public static double distSquaredBetweenPoints(double x, double y, double x1, double y1)
  {
    return Math.sqrt((x-x1)*(x-x1) + (y-y1)*(y-y1));
  }

  public static int getCurrentBoardWidth()
  {
    return (int)(DEFAULT_WIDTH/TILE_WIDTH);
  }
  public static int getCurrentBoardHeight()
  {
    return (int)(DEFAULT_HEIGHT/TILE_HEIGHT);
  }
}
