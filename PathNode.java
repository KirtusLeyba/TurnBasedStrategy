package kleyba.gameDev.turnBased.logic;

import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import kleyba.gameDev.turnBased.GameConstants;
import kleyba.gameDev.turnBased.entities.TileEntity;
import kleyba.gameDev.turnBased.entities.UnitEntity;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class PathNode
{
  private int x;
  private int y;
  private double h;
  private double g;
  private double f;
  private PathNode parent;

  public PathNode getParent()
  {
    return parent;
  }

  public void setParent(PathNode parent)
  {
    this.parent = parent;
  }

  public PathNode(int x, int y)
  {
    this.x = x;
    this.y = y;
  }

  public void calculateF()
  {
    f = h + g;
  }


  public int getX()
  {
    return x;
  }

  public void setX(int x)
  {
    this.x = x;
  }

  public int getY()
  {
    return y;
  }

  public void setY(int y)
  {
    this.y = y;
  }

  public double getH()
  {
    return h;
  }

  public void setH(double h)
  {
    this.h = h;
  }

  public double getG()
  {
    return g;
  }

  public void setG(double g)
  {
    this.g = g;
  }

  public double getF()
  {
    return f;
  }

  /**
   * Returns all the tiles adjacent to this tile
   * @return
   * ArrayList of adjacent tiles
   */
  public ArrayList<PathNode> getAdjacentTiles(int gridWidth, int gridHeight, ArrayList<PathNode> closed, TileEntity[] grid, ArrayList<UnitEntity> units, PlayerManager playerManager)
  {
    ArrayList<PathNode> adjTiles = new ArrayList<PathNode>();
    PathNode temp;
    //North
    temp = new PathNode(x, y-1);
    if(temp.nodeLegal(gridWidth, gridHeight, closed, grid, units, playerManager))
    {
      adjTiles.add(temp);
    }
    //NorthEast
    temp = new PathNode(x+1, y-1);
    if(temp.nodeLegal(gridWidth, gridHeight, closed, grid, units, playerManager))
    {
      adjTiles.add(temp);
    }
    //East
    temp = new PathNode(x+1, y);
    if(temp.nodeLegal(gridWidth, gridHeight, closed, grid, units, playerManager))
    {
      adjTiles.add(temp);
    }
    //SouthEast
    temp = new PathNode(x+1, y+1);
    if(temp.nodeLegal(gridWidth, gridHeight, closed, grid, units, playerManager))
    {
      adjTiles.add(temp);
    }
    //South
    temp = new PathNode(x, y+1);
    if(temp.nodeLegal(gridWidth, gridHeight, closed, grid, units, playerManager))
    {
      adjTiles.add(temp);
    }
    //SouthWest
    temp = new PathNode(x-1, y+1);
    if(temp.nodeLegal(gridWidth, gridHeight, closed, grid, units, playerManager))
    {
      adjTiles.add(temp);
    }
    //West
    temp = new PathNode(x-1, y);
    if(temp.nodeLegal(gridWidth, gridHeight, closed, grid, units, playerManager))
    {
      adjTiles.add(temp);
    }
    //NorthWest
    temp = new PathNode(x-1, y-1);
    if(temp.nodeLegal(gridWidth, gridHeight, closed, grid, units, playerManager))
    {
      adjTiles.add(temp);
    }

    return adjTiles;

  }

  public boolean nodeLegal(int gridWidth, int gridHeight, ArrayList<PathNode> closed, TileEntity[] grid, ArrayList<UnitEntity> units, PlayerManager playerManager)
  {

    //Check for legal position
    if(x >= 0 && y >= 0 && x < gridWidth && y < gridHeight && !closed.contains(this))
    {
      //check if tile at this position is passable
      boolean unitHere = false;
      for(UnitEntity unit: units)
      {
        if(unit.getX() == x && unit.getY() == y && unit.getOwner().equals(playerManager.getCurrentPlayer()))
        {
          unitHere = true;
          break;
        }
      }
      if(grid[x + gridWidth*y].getPassable() && !unitHere)
      {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean equals(Object o)
  {
    if (o.getClass() != getClass())
    {
      return false;
    }
    PathNode other = (PathNode)o;
    if (other.getX() == x && other.getY() == y)
    {
      return true;
    }
    return false;
  }

  @Override
  public String toString()
  {
    return "(x: " + x + ", y: " + y + ")";
  }



}
