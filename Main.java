package kleyba.gameDev.turnBased;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import kleyba.gameDev.turnBased.controls.KeyboardController;
import kleyba.gameDev.turnBased.controls.MouseController;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Main Class Entry Point for the game
 */
public class Main extends Application
{

  private GraphicsContext graphicsContext;
  private GameLoop gameLoop;
  private MouseController mouse = GameConstants.mouse;
  private KeyboardController kBoard = GameConstants.kBoard;

  public static void main(String[] args)
  {
    launch(args);
  }


  @Override
  public void start(Stage primaryStage)
  {

    GameConstants.gameCanvas.setWidth(GameConstants.DEFAULT_WIDTH);
    GameConstants.gameCanvas.setHeight(GameConstants.DEFAULT_HEIGHT);
    graphicsContext = GameConstants.gameCanvas.getGraphicsContext2D();
    GameConstants.layers.getChildren().add(GameConstants.gameCanvas);
    GameConstants.mainScene.setRoot(GameConstants.layers);
    setMouseListener();
    setKeyListener();

    primaryStage.setScene(GameConstants.mainScene);
    primaryStage.setResizable(false);
    primaryStage.sizeToScene();
    primaryStage.setTitle("Turn Based Game");
    primaryStage.show();

    GameConstants.gameCanvas.heightProperty().bind(GameConstants.mainScene.heightProperty());
    GameConstants.gameCanvas.widthProperty().bind(GameConstants.mainScene.widthProperty());

    gameLoop = new GameLoop(graphicsContext, primaryStage);
    gameLoop.start();

  }

  private void setKeyListener()
  {
    GameConstants.mainScene.setOnKeyPressed(kBoard.kBoardPress);
    GameConstants.mainScene.setOnKeyReleased(kBoard.kBoardRelease);
  }

  private void setMouseListener()
  {
    GameConstants.layers.setOnMousePressed(mouse.mousePress);
    GameConstants.layers.setOnMouseReleased(mouse.mouseRelease);
    GameConstants.layers.setOnMouseMoved(mouse.mouseMove);
    GameConstants.layers.setOnMouseDragged(mouse.mouseMove);
  }

}
