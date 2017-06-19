package kleyba.gameDev.turnBased.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import kleyba.gameDev.turnBased.logic.*;
import kleyba.gameDev.turnBased.logic.commands.GameCommand;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class PirateUnit extends UnitEntity
{

  private Image[] pirateWalkingFrames;
  private GameAnimation pirateAnimation;
  private int animTime = 7;
  private double totalMoves = 10;
  private double movesLeft = 10;
  private double pirateAttackPoints = 2;
  private double pirateHealth = 5;
  private double pirateMaxHP = 5;
  private String[] commandsStrings = {"Move", "Attack", "Swipe","","","","","",""};
  private String[] tooltipStrings = {"Move up to 10 spaces per turn","Deal 2 damage to target",
          "Deal 1 damage to each enemy in arc","","","","","",""};
  private GameCommand currentCommand;
  private GameCommand[] commandArray = new GameCommand[9];

  public PirateUnit(double x, double y, GamePlayer owner, TileEntity[] grid, UnitManager unitManager)
  {
    super(x,y,owner,grid, unitManager);
    pirateWalkingFrames = new Image[4];
    for(int i = 0; i < 4; i++)
    {
      pirateWalkingFrames[i] = new Image("file:resources/kleyba/gameDev/turnBased/FileIO/sprites/units/pirate/pirate" + i + ".png");
    }
    pirateAnimation = new GameAnimation(pirateWalkingFrames, animTime);
  }

  @Override
  public void draw(long deltaTime, GraphicsContext graphicsContext)
  {
    if(this.getMoving())
    {
      pirateAnimation.animateUpdate(deltaTime);
    }
    double pixelX = this.getPixelX();
    double pixelY = this.getPixelY();
    graphicsContext.drawImage(pirateAnimation.getCurrentFrame(), pixelX, pixelY);
    super.draw(deltaTime,graphicsContext);
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
  public double getHealth()
  {
    return pirateHealth;
  }
  @Override
  public void setHealth(double health)
  {
    this.pirateHealth = health;
  }
  @Override
  public double getAttackPoints()
  {
    return pirateAttackPoints;
  }
  @Override
  public void setAttackPoints(double attackPoints)
  {
    this.pirateAttackPoints = attackPoints;
  }
  @Override
  public double getMaxHP()
  {
    return pirateMaxHP;
  }
  @Override
  public void setMaxHP(double maxHP)
  {
    this.pirateMaxHP = maxHP;
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

}
