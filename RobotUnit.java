package kleyba.gameDev.turnBased.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import kleyba.gameDev.turnBased.GameConstants;
import kleyba.gameDev.turnBased.logic.*;
import kleyba.gameDev.turnBased.logic.commands.GameCommand;
import kleyba.gameDev.turnBased.logic.commands.MoveCommand;
import kleyba.gameDev.turnBased.logic.commands.VaporizeCommand;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class RobotUnit extends UnitEntity
{

  private Image[] robotWalkingFrames;
  private GameAnimation robotAnimation;
  private int animTime = 7;
  private double totalMoves = 10;
  private double movesLeft = 10;
  private String[] commandsStrings = {"Move", "Attack", "Vaporize", "", "", "", "", "", ""};
  private String[] tooltipStrings = {"Move up to 10 spaces per turn","Deal 1 damage to target",
          "Deal 0.5 damage to all units in radius","","","","","",""};
  private GameCommand currentCommand;
  private GameCommand[] commandArray = new GameCommand[9];
  private UnitManager unitManager;
  private double pixelX;
  private double pixelY;
  private boolean commandExecutable = true;
  private boolean moving;

  public RobotUnit(double x, double y, GamePlayer owner, TileEntity[] grid, UnitManager unitManager)
  {
    super(x,y,owner,grid, unitManager);
    robotWalkingFrames = new Image[4];
    for(int i = 0; i < 4; i++)
    {
      robotWalkingFrames[i] = new Image("file:resources/kleyba/gameDev/turnBased/FileIO/sprites/units/robot/robotUnit" + i + ".png");
    }
    robotAnimation = new GameAnimation(robotWalkingFrames, animTime);
    commandArray = new GameCommand[9];
    for(int i = 0; i < 9; i++)
    {
      commandArray[i] = new MoveCommand(x,y,this,grid);
    }
    commandArray[2] = new VaporizeCommand(this, grid);
    currentCommand = commandArray[0];
    this.unitManager = unitManager;
    setCommandArray(commandArray);
    setCurrentCommand(currentCommand);
  }

  @Override
  public void draw(long deltaTime, GraphicsContext graphicsContext)
  {
    if(this.getMoving())
    {
      robotAnimation.animateUpdate(deltaTime);
    }
    double pixelX = this.getPixelX();
    double pixelY = this.getPixelY();
    graphicsContext.drawImage(robotAnimation.getCurrentFrame(), pixelX, pixelY);
    super.draw(deltaTime,graphicsContext);
  }

  @Override
  public void update(long deltaTime)
  {
    pixelX = GameConstants.gridXtoPixelX(this.getX());
    pixelY = GameConstants.gridYtoPixelY(this.getY());

    if (this.getOwner().getTurn())
    {
      if (this.currentCommand.isActivated())
      {
        this.setMoving(true);
        this.currentCommand.update(deltaTime);
      } else
      {
        this.setMoving(false);
        this.currentCommand.prepare(GameConstants.pixelXtoGridX(GameConstants.mouse.mouseX)
                , GameConstants.pixelYtoGridY(GameConstants.mouse.mouseY)
                , getUnitManager());
      }

      if (GameConstants.mouse.mouseRightClicked)
      {
        if (commandExecutable && this.isSelected())
        {
          currentCommand.execute();
          commandExecutable = false;
        }
      }
      if (!GameConstants.mouse.mouseRightClicked && !commandExecutable)
      {
        commandExecutable = true;
      }
    }
    if (GameConstants.gameButtonController.getButtonPressed() && this.isSelected())
    {
      GameConstants.gameButtonController.setButtonPressed(false);
      setCurrentCommand(getCommandArray()[GameConstants.gameButtonController.getSource()]);
      System.out.println(getCommandArray()[GameConstants.gameButtonController.getSource()]);
    }
  }

  @Override
  public double getMovesLeft()
  {
    return movesLeft;
  }

  @Override
  public void setMovesLeft(double movesLeft)
  {
    this.movesLeft = movesLeft;
  }

  @Override
  public double getTotalMoves()
  {
    return totalMoves;
  }

  @Override
  public void setCommandsStrings(String[] commandsStrings)
  {
    this.commandsStrings = commandsStrings;
  }
  @Override
  public String[] getCommandsStrings()
  {
    return commandsStrings;
  }
  @Override
  public String[] getTooltipStrings()
  {
    return tooltipStrings;
  }

  @Override
  public void setTooltipStrings(String[] tooltipStrings)
  {
    this.tooltipStrings = tooltipStrings;
  }

  @Override
  public void setCurrentCommand(GameCommand currentCommand)
  {
    this.currentCommand = currentCommand;
  }

  @Override
  public GameCommand[] getCommandArray()
  {
    return commandArray;
  }

  @Override
  public void setCommandArray(GameCommand[] commandArray)
  {
    this.commandArray = commandArray;
  }

  public UnitManager getUnitManager()
  {
    return unitManager;
  }

  @Override
  public GameCommand getCurrentCommand()
  {
    return currentCommand;
  }

  @Override
  public double getPixelX()
  {
    return pixelX;
  }

  @Override
  public double getPixelY()
  {
    return pixelY;
  }

  public void setPixelX(double pixelX)
  {
    this.pixelX = pixelX;
  }

  public void setPixelY(double pixelY)
  {
    this.pixelY = pixelY;
  }

  public void setMoving(boolean moving)
  {
    this.moving = moving;
  }
  @Override
  public boolean getMoving()
  {
    return moving;
  }
}
