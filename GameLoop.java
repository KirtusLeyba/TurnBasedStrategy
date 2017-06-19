package kleyba.gameDev.turnBased;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kleyba.gameDev.turnBased.FileIO.GridBuilder;
import kleyba.gameDev.turnBased.FileIO.OOBBuilder;
import kleyba.gameDev.turnBased.controls.GameButtonController;
import kleyba.gameDev.turnBased.controls.KeyboardController;
import kleyba.gameDev.turnBased.controls.MouseController;
import kleyba.gameDev.turnBased.entities.*;
import kleyba.gameDev.turnBased.logic.GamePlayer;
import kleyba.gameDev.turnBased.logic.UnitManager;
import kleyba.gameDev.turnBased.logic.PlayerManager;

import java.util.ArrayList;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class GameLoop extends AnimationTimer
{

  private long lastLoopTime;
  private GraphicsContext graphicsContext;

  //Game Items: Think of somewhere else to put these
  private TileEntity[] tiles;
  private PlayerManager playerManager;
  private GamePlayer playerOne;
  private GamePlayer playerTwo;
  private boolean changeTurn = true;
  private boolean changeSelection = true;

  //Unit Items:
  private ArrayList<UnitEntity> units = new ArrayList<UnitEntity>();
  private UnitEntity selectedUnit;

  //FileIO items
  private GridBuilder gridBuilder = new GridBuilder();
  private OOBBuilder oobBuilder;
  private UnitManager unitManager;

  //Interface items
  private MouseController mouse = GameConstants.mouse;
  private KeyboardController kBoard = GameConstants.kBoard;
  private Stage primaryStage;

  public GameLoop(GraphicsContext graphicsContext, Stage primaryStage)
  {
    lastLoopTime = System.nanoTime();
    this.graphicsContext = graphicsContext;
    this.primaryStage = primaryStage;
    gameLoad();
  }

  @Override
  public void handle(long now)
  {

    long deltaTime = now - lastLoopTime;

    if(deltaTime > GameConstants.frameLengthNano)
    {
      gameUpdate(deltaTime);
      gameDraw(deltaTime);
      lastLoopTime = now;
    }

  }

  private void gameLoad()
  {
    playerManager = new PlayerManager();
    playerOne = new GamePlayer("Pirates", Color.color(0.8,0.2,0.2), Color.color(0.8,0.2,0.2, 0.75));
    playerTwo = new GamePlayer("Robots", Color.color(0.2,0.2,0.8), Color.color(0.2,0.2,0.75));
    playerManager.add(playerOne);
    playerManager.add(playerTwo);
    playerManager.setCurrentPlayer(0);
    oobBuilder = new OOBBuilder(playerManager.getPlayers());
    loadGUI();
    loadTiles();
    loadUnits();
  }

  private void loadGUI()
  {
    GameConstants.gameBorder.setBottom(GameConstants.overlayImageView);
    GameConstants.mainScene.setRoot(GameConstants.layers);
    GameConstants.layers.getChildren().addAll(GameConstants.gameBorder, GameConstants.gameButtonBox);
    GameConstants.gameButtonBox.getChildren().add(GameConstants.endTurnButton);
    //Game Buttons
    GameConstants.endTurnButton.setTranslateY(GameConstants.DEFAULT_HEIGHT - 220);
    GameConstants.endTurnButton.setPrefWidth(170);
    GameConstants.endTurnButton.setPrefHeight(27);
    GameConstants.endTurnButton.setTranslateX(721);
    GameConstants.endTurnButton.setStyle("-fx-base: #63b0e8");
    //Command Card
    GameConstants.gameButtonBox.getChildren().addAll(GameConstants.commandCardButtons);
    buildCommandCard();
  }

  private void buildCommandCard()
  {
    for(int i = 0; i < 3; i++)
    {
      Button button = GameConstants.commandCardButtons[i];
      button.setPrefHeight(57);
      button.setPrefWidth(69);
      button.setTranslateY(GameConstants.DEFAULT_HEIGHT - 181 + (i/3)*57);
      button.setTranslateX(506);
      button.setOnAction(GameConstants.gameButtonController);
    }
    for(int i = 3; i < 6; i++)
    {
      Button button = GameConstants.commandCardButtons[i];
      button.setPrefHeight(57);
      button.setPrefWidth(69);
      button.setTranslateY(GameConstants.DEFAULT_HEIGHT - (181-57) + ((i-3)/3)*57);
      button.setTranslateX(506 - 207);
      button.setOnAction(GameConstants.gameButtonController);
    }
    for(int i = 6; i < 9; i++)
    {
      Button button = GameConstants.commandCardButtons[i];
      button.setPrefHeight(57);
      button.setPrefWidth(69);
      button.setTranslateY(GameConstants.DEFAULT_HEIGHT - (181-114) + ((i-6)/3)*57);
      button.setTranslateX(506 - 414);
      button.setOnAction(GameConstants.gameButtonController);
    }
  }

  private void gameUpdate(long deltaTime)
  {
    //Put game updates here!
    updateTiles(deltaTime);
    updateMouse(deltaTime);
    updateKeys(deltaTime);
    updateUnits(deltaTime);
    updateInterface();
    cleanUpUnits();
  }

  private void updateInterface()
  {
    if(selectedUnit != null)
    {
      for(int i = 0; i < GameConstants.commandCardButtons.length; i++)
      {
        GameConstants.commandCardButtons[i].setText(selectedUnit.getCommandsStrings()[i]);
      }
    }
    else
    {
      for(int i = 0; i < GameConstants.commandCardButtons.length; i++)
      {
        GameConstants.commandCardButtons[i].setText("");
      }
    }
  }

  private void updateUnits(long deltaTime)
  {
    for(UnitEntity unit: units)
    {
      unit.update(deltaTime);
    }
  }

  private void updateKeys(long deltaTime)
  {
    if(kBoard.enterPressed && changeTurn)
    {
      System.out.println(playerManager.getCurrentPlayer().getPlayerName() + " Turn Ended");
      playerManager.setNextPlayer();
      System.out.println("Player: " + playerManager.getCurrentPlayer().getPlayerName() + " new turn.");
      changeTurn = false;
      resetUnitTurns(units);
    }
    if(!kBoard.enterPressed)
    {
      changeTurn = true;
    }
  }

  private void resetUnitTurns(ArrayList<UnitEntity> units)
  {
    for(UnitEntity unit: units)
    {
      unit.resetMovesLeft();
    }
  }

  private void updateMouse(long deltaTime)
  {
    int mouseGridX = GameConstants.pixelXtoGridX(mouse.mouseX);
    int mouseGridY = GameConstants.pixelYtoGridY(mouse.mouseY);

    if(mouse.mouseLeftClicked && changeSelection)
    {
      System.out.println("(" + mouseGridX + ", " + mouseGridY + ")");
      selectedUnit = getUnitAt(mouseGridX, mouseGridY);
      deselectAllUnits();
      if(selectedUnit != null)
      {
        selectedUnit.setSelected(!selectedUnit.isSelected());
      }
      changeSelection = false;
    }
    if(!mouse.mouseLeftClicked)
    {
      changeSelection = true;
    }
    if(mouse.mouseRightClicked)
    {
      if(selectedUnit != null && !selectedUnit.getMoving() && playerManager.getCurrentPlayer().getUnits().contains(selectedUnit))
      {
        selectedUnit.move(mouseGridX, mouseGridY);
      }
    }

    //screen scroll
    if(GameConstants.DEFAULT_WIDTH - mouse.mouseX <= 100)
    {
      GameConstants.screenX = GameConstants.screenX - 2.5;
    }
    if(mouse.mouseX <= 100)
    {
      GameConstants.screenX = GameConstants.screenX + 2.5;
    }
    if(GameConstants.DEFAULT_HEIGHT - mouse.mouseY <= 100)
    {
      GameConstants.screenY = GameConstants.screenY - 2.5;
    }
    if(mouse.mouseY <= 100)
    {
      GameConstants.screenY = GameConstants.screenY + 2.5;
    }
  }

  private void deselectAllUnits()
  {
    for(UnitEntity unit: units)
    {
      unit.setSelected(false);
    }
  }

  private UnitEntity getUnitAt(int mouseGridX, int mouseGridY)
  {
    for(UnitEntity unit: units)
    {
      if(unit.getX() == mouseGridX && unit.getY() == mouseGridY)
      {
        return unit;
      }
    }
    return null;
  }

  private void gameDraw(long deltaTime)
  {
    //Put drawing updates here!
    //Drawing the background
    graphicsContext.setFill(Color.BLACK);
    graphicsContext.fillRect(0,0,graphicsContext.getCanvas().getWidth(), graphicsContext.getCanvas().getHeight());

    //Draw the tiles
    drawTiles(deltaTime);


    //Drawing units
    drawUnits(deltaTime);

    //Drawing interface stuff
    drawMiniMap(deltaTime);
  }

  private void drawMiniMap(long deltaTime)
  {
    double mapX = 12;
    double mapY = GameConstants.DEFAULT_HEIGHT - 181;
    double mapWidth = 171;
    double mapHeight = 171;
    double mapGridWidth = gridBuilder.getGridWidth();
    double mapGridHeight = gridBuilder.getGridHeight();

    for(TileEntity tile: tiles)
    {
      double tileX = tile.getX();
      double tileY = tile.getY();
      double miniX = mapX + tileX*(mapWidth/mapGridWidth);
      double miniY = mapY + tileY*(mapHeight/mapGridHeight);
      graphicsContext.setFill(tile.getMiniColor());
      graphicsContext.fillRect(miniX, miniY, mapWidth/mapGridWidth, mapHeight/mapGridHeight);
    }

    for(UnitEntity unit: units)
    {
      double unitX = unit.getX();
      double unitY = unit.getY();
      double miniX = mapX + unitX*(mapWidth/mapGridWidth);
      double miniY = mapY + unitY*(mapHeight/mapGridHeight);
      graphicsContext.setFill(unit.getOwner().getMiniColor());
      graphicsContext.fillRect(miniX+2, miniY+2, mapWidth/mapGridWidth, mapHeight/mapGridHeight);
    }

    graphicsContext.setStroke(Color.WHITE);
    graphicsContext.setLineWidth(2);
    graphicsContext.strokeRect(mapX -
                    GameConstants.screenX*(mapWidth/(gridBuilder.getGridWidth()*GameConstants.TILE_WIDTH/GameConstants.DEFAULT_WIDTH))/GameConstants.DEFAULT_WIDTH,
            mapY -
                    GameConstants.screenY*(mapHeight/(gridBuilder.getGridHeight()*GameConstants.TILE_HEIGHT/GameConstants.DEFAULT_HEIGHT))/GameConstants.DEFAULT_HEIGHT,
            mapWidth/(gridBuilder.getGridWidth()*GameConstants.TILE_WIDTH/GameConstants.DEFAULT_WIDTH),
            mapHeight/(gridBuilder.getGridHeight()*GameConstants.TILE_HEIGHT/GameConstants.DEFAULT_HEIGHT));

  }

  private void loadTiles()
  {
    gridBuilder.readMapFile("resources/kleyba/gameDev/turnBased/FileIO/maps/simpleMap.grid");
    tiles = gridBuilder.getGrid();
    unitManager = new UnitManager(gridBuilder.getGridWidth(), gridBuilder.getGridHeight(), tiles, units);
  }

  private void loadUnits()
  {
    oobBuilder.readOOBFile("resources/kleyba/gameDev/turnBased/FileIO/OOB/simpleOOB.oob",
            "resources/kleyba/gameDev/turnBased/FileIO/OOB/simpleOOBOwners.own", tiles, gridBuilder.getGridWidth(), gridBuilder.getGridHeight(),
            unitManager);
    units = oobBuilder.getoOB();
    unitManager.setUnits(units);
    unitManager.setPlayerManager(playerManager);
  }

  private void updateTiles(long deltaTime)
  {
    for(TileEntity tile: tiles)
    {
      tile.update(deltaTime);
    }
  }

  private void drawTiles(long deltaTime)
  {
    for(TileEntity tile: tiles)
    {
      tile.draw(deltaTime, graphicsContext);
    }
  }

  private void drawUnits(long deltaTime)
  {
    for(UnitEntity unit: units)
    {
      unit.draw(deltaTime, graphicsContext);
    }
  }

  private void cleanUpUnits()
  {
    ArrayList<UnitEntity> unitsToRemove = new ArrayList<UnitEntity>();
    for(UnitEntity unit: units)
    {
      if(unit.getHealth() <= 0)
      {
        GamePlayer owner = unit.getOwner();
        owner.removeUnit(unit);
        unitsToRemove.add(unit);
      }
    }
    units.removeAll(unitsToRemove);
  }
}
