package kleyba.gameDev.turnBased.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import kleyba.gameDev.turnBased.GameConstants;
import kleyba.gameDev.turnBased.logic.*;
import kleyba.gameDev.turnBased.logic.commands.GameCommand;
import kleyba.gameDev.turnBased.logic.commands.MoveCommand;

import java.util.ArrayList;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class UnitEntity extends GameEntity
{

  private double x;
  private double y;
  private double pixelX;
  private double pixelY;
  private GamePlayer owner;
  private double totalMoves = 5;
  private double movesLeft = 5;
  private int animTime = 15;
  private UnitManager unitManager;
  private ArrayList<PathNode> movePath = new ArrayList<PathNode>();
  private ArrayList<PathNode> emptyPath = new ArrayList<PathNode>();
  private boolean moving = false;
  private boolean attacking = true;
  private PathNode currentMoveNode;
  private int framesSinceLastMove = 0;
  private int currentPathIndex = 0;
  private boolean selected = false;
  private TileEntity[] grid;
  private double maxHP = 10;
  private double health = 10;
  private double attackPoints = 1;
  private String[] commandsStrings;
  private String[] tooltipStrings;
  private GameCommand currentCommand;
  private GameCommand[] commandArray;
  private boolean commandExecutable = true;


  public UnitEntity(double x, double y, GamePlayer owner, TileEntity[] grid, UnitManager unitManager)
  {
    this.x = x;
    this.y = y;
    this.owner = owner;
    owner.addUnit(this);
    this.pixelX = GameConstants.gridXtoPixelX(x);
    this.pixelY = GameConstants.gridYtoPixelY(y);
    this.grid = grid;
    this.unitManager = unitManager;
    commandArray = new GameCommand[9];
    for(int i = 0; i < 9; i++)
    {
      commandArray[i] = new MoveCommand(x,y,this,grid);
    }
    currentCommand = commandArray[0];
  }

  @Override
  public void update(long deltaTime)
  {
    pixelX = GameConstants.gridXtoPixelX(x);
    pixelY = GameConstants.gridYtoPixelY(y);

    if (owner.getTurn())
    {
      if (currentCommand.isActivated())
      {
        currentCommand.update(deltaTime);
      } else
      {
        currentCommand.prepare(GameConstants.pixelXtoGridX(GameConstants.mouse.mouseX)
                , GameConstants.pixelYtoGridY(GameConstants.mouse.mouseY)
                , unitManager);
      }


      if (GameConstants.mouse.mouseRightClicked)
      {
        if(commandExecutable && selected)
        {
          currentCommand.execute();
        }
      }
      if(!GameConstants.mouse.mouseRightClicked && !commandExecutable)
      {
        commandExecutable = true;
      }
    }

    if (GameConstants.gameButtonController.getButtonPressed() && selected)
    {
      GameConstants.gameButtonController.setButtonPressed(false);
      setCurrentCommand(getCommandArray()[GameConstants.gameButtonController.getSource()]);
    }

  }

  @Override
  public void draw(long deltaTime, GraphicsContext graphicsContext)
  {
    if (selected)
    {
      getCurrentCommand().draw(deltaTime, graphicsContext);
    }

    //healthbar
    graphicsContext.setFill(Color.color(owner.getPlayerColor().getRed(), owner.getPlayerColor().getGreen(), owner.getPlayerColor().getBlue(), 0.65));
    graphicsContext.fillRect(getPixelX(), getPixelY(), (getHealth() / getMaxHP()) * GameConstants.TILE_WIDTH, 3);
  }

  @Override
  public void move(double x, double y)
  {
    if(GameConstants.distSquaredBetweenPoints(x,y,this.x,this.y) < getMovesLeft())
    {
      if (movePath.size() > 0)
      {
        moving = true;
        currentMoveNode = movePath.get(0);
        currentPathIndex = 0;
      }
    }
  }

  public double getX()
  {
    return x;
  }

  public double getY()
  {
    return y;
  }

  public double getPixelX()
  {
    return pixelX;
  }

  public double getPixelY()
  {
    return pixelY;
  }

  public boolean isSelected()
  {
    return selected;
  }

  public void setSelected(boolean selected)
  {
    this.selected = selected;
  }

  public boolean getMoving()
  {
    return moving;
  }

  public void resetMovesLeft()
  {
    setMovesLeft(getTotalMoves());
  }

  public double getMovesLeft()
  {
    return movesLeft;
  }

  public void setMovesLeft(double movesLeft)
  {
    this.movesLeft = movesLeft;
  }

  public double getTotalMoves()
  {
    return totalMoves;
  }

  public GamePlayer getOwner()
  {
    return owner;
  }

  public double getHealth()
  {
    return health;
  }
  public void setHealth(double health)
  {
    this.health = health;
  }
  public double getAttackPoints()
  {
    return attackPoints;
  }

  public void setAttackPoints(double attackPoints)
  {
    this.attackPoints = attackPoints;
  }

  public double getMaxHP()
  {
    return maxHP;
  }

  public void setMaxHP(double maxHP)
  {
    this.maxHP = maxHP;
  }

  public String[] getCommandsStrings()
  {
    return commandsStrings;
  }

  public void setCommandsStrings(String[] commandsStrings)
  {
    this.commandsStrings = commandsStrings;
  }

  public String[] getTooltipStrings()
  {
    return tooltipStrings;
  }

  public void setTooltipStrings(String[] tooltipStrings)
  {
    this.tooltipStrings = tooltipStrings;
  }

  public void setX(int x)
  {
    this.x = x;
  }

  public void setY(int y)
  {
    this.y = y;
  }

  public void setCurrentCommand(GameCommand currentCommand)
  {
    this.currentCommand = currentCommand;
  }

  public GameCommand[] getCommandArray()
  {
    return commandArray;
  }

  public void setCommandArray(GameCommand[] commandArray)
  {
    this.commandArray = commandArray;
  }

  public GameCommand getCurrentCommand()
  {
    return currentCommand;
  }
}
