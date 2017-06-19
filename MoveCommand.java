package kleyba.gameDev.turnBased.logic.commands;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import kleyba.gameDev.turnBased.GameConstants;
import kleyba.gameDev.turnBased.entities.TileEntity;
import kleyba.gameDev.turnBased.entities.UnitEntity;
import kleyba.gameDev.turnBased.logic.PathNode;
import kleyba.gameDev.turnBased.logic.UnitManager;

import java.util.ArrayList;

/**
 * Created by Kirtus on 4/18/2017.
 * Basic move command
 */
public class MoveCommand extends GameCommand
{

  private double goalX;
  private double goalY;
  private UnitEntity unit;
  private ArrayList<PathNode> movePath = new ArrayList<PathNode>();
  private ArrayList<PathNode> emptyPath = new ArrayList<PathNode>();
  private boolean moving = false;
  private PathNode currentMoveNode;
  private int framesSinceLastMove = 0;
  private int currentPathIndex = 0;
  private int animTime = 10;
  private TileEntity[] grid;
  private boolean activated = false;

  public MoveCommand(double x, double y, UnitEntity unit, TileEntity[] grid)
  {
    this.goalX = x;
    this.goalY = y;
    this.unit = unit;
    this.grid = grid;
  }




  @Override
  public void update(double deltaTime)
  {
    if(activated)
    {
      if (moving)
      {
        framesSinceLastMove++;
        if (framesSinceLastMove >= animTime)
        {
          if (movePath.size() > 0)
          {
            simpleMove();
          }
          else
          {
            deactivate();
          }
          framesSinceLastMove = 0;
        }
      }
    }
  }

  private void deactivate()
  {
    activated = false;
    movePath = emptyPath;
    framesSinceLastMove = 0;
  }

  private void simpleMove()
  {
    unit.setX(currentMoveNode.getX());
    unit.setY(currentMoveNode.getY());
    currentPathIndex++;
    if (currentPathIndex == movePath.size() || unit.getMovesLeft() < GameConstants.distSquaredBetweenPoints(unit.getX(), unit.getY(), currentMoveNode.getX(), currentMoveNode.getY()))
    {
      moving = false;
      deactivate();
      movePath = emptyPath;
      currentPathIndex = 0;
    } else
    {
      currentMoveNode = movePath.get(currentPathIndex);
      unit.setMovesLeft(unit.getMovesLeft() - GameConstants.distSquaredBetweenPoints(unit.getX(), unit.getY(), currentMoveNode.getX(), currentMoveNode.getY()));
    }
  }

  @Override
  public void execute()
  {
    if(unit.isSelected())
    {
      activated = true;
      if (GameConstants.distSquaredBetweenPoints(goalX, goalY, unit.getX(), unit.getY()) < unit.getMovesLeft())
      {
        if (movePath.size() > 0)
        {
          moving = true;
          currentMoveNode = movePath.get(0);
          currentPathIndex = 0;
        }
        else
        {
          activated = false;
        }
      }
      else
      {
        activated = false;
      }
    }
  }

  @Override
  public void prepare(double x, double y, UnitManager unitManager)
  {
    if(unit.isSelected())
    {
      generateMovePath(x, y, unitManager);
    }
  }

  @Override
  public void draw(double deltaTime, GraphicsContext graphicsContext)
  {
    if(unit.isSelected())
    {
      //Draw Movable Grid
      for (TileEntity tile : grid)
      {
        if (GameConstants.distSquaredBetweenPoints(tile.getX(), tile.getY(), unit.getX(), unit.getY()) < unit.getMovesLeft() && tile.getPassable())
        {
          double tX = GameConstants.gridXtoPixelX(tile.getX());
          double tY = GameConstants.gridYtoPixelY(tile.getY());
          graphicsContext.setFill(Color.color(unit.getOwner().getPlayerColor().getRed(), unit.getOwner().getPlayerColor().getGreen(), unit.getOwner().getPlayerColor().getBlue(), 0.17).brighter());
          graphicsContext.fillRect(tX, tY, GameConstants.TILE_WIDTH, GameConstants.TILE_HEIGHT);
        }
      }

      if (movePath.size() > 0)
      {
        drawPath(movePath, graphicsContext);
      }
    }

  }

  private void drawPath(ArrayList<PathNode> path, GraphicsContext graphicsContext)
  {
    if(path.size() > 0)
    {
      for (PathNode node : path)
      {
        double startx = 0;
        double starty = 0;
        double goalx = 0;
        double goaly = 0;
        if (node.getParent() != null)
        {
          startx = GameConstants.gridXtoPixelX(node.getX()) + 16;
          starty = GameConstants.gridYtoPixelY(node.getY()) + 16;
          goalx = GameConstants.gridXtoPixelX(node.getParent().getX()) + 16;
          goaly = GameConstants.gridYtoPixelY(node.getParent().getY()) + 16;
          graphicsContext.setStroke(Color.WHITE);
          graphicsContext.setLineWidth(2);
          graphicsContext.strokeLine(startx, starty, goalx, goaly);
        }
      }
    }
  }

  public void generateMovePath(double x, double y, UnitManager unitManager)
  {
    if(!(unit.getX() == x && unit.getY() == y))
    {
      movePath = unitManager.generatePath(new PathNode((int) unit.getX(), (int) unit.getY()), new PathNode((int) x, (int) y));
      if(movePath.size() > 0)
      {
        currentPathIndex = 0;
        currentMoveNode = movePath.get(0);
      }
    }
  }

  @Override
  public boolean isActivated()
  {
    return activated;
  }

  @Override
  public void setActivated(boolean activated)
  {
    this.activated = activated;
  }
}
