package kleyba.gameDev.turnBased.logic;

import kleyba.gameDev.turnBased.GameConstants;
import kleyba.gameDev.turnBased.entities.TileEntity;
import kleyba.gameDev.turnBased.entities.UnitEntity;

import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class UnitManager
{
  private int gridWidth;
  private int gridHeight;
  private TileEntity[] grid;
  private ArrayList<PathNode> closed;
  private ArrayList<PathNode> open;
  private ArrayList<PathNode> emptyList;
  private ArrayList<PathNode> adjTiles;
  private boolean done = false;
  private PathNode qNode;
  private ArrayList<UnitEntity> units;
  private PlayerManager playerManager;

  public UnitManager(int gridWidth, int gridHeight, TileEntity[] grid, ArrayList<UnitEntity> units)
  {
    this.gridWidth = gridWidth;
    this.gridHeight = gridHeight;
    this.grid = grid;
    this.units = units;
  }

  public void setPlayerManager(PlayerManager playerManager)
  {
    this.playerManager = playerManager;
  }

  public ArrayList<UnitEntity> getUnits()
  {
    return units;
  }

  public ArrayList<PathNode> generatePath(PathNode start, PathNode goal)
  {

    ArrayList<PathNode> closed = new ArrayList<PathNode>();
    ArrayList<PathNode> open = new ArrayList<PathNode>();
    ArrayList<PathNode> emptyList = new ArrayList<PathNode>();
    ArrayList<PathNode> adjTiles = new ArrayList<PathNode>();

    if ((start == null) || (goal == null))
    {
      return emptyList;
    }

    start.setH(GameConstants.distSquaredBetweenPoints(start.getX(), start.getY(), goal.getX(),goal.getY()));
    start.setG(0);
    start.calculateF();

    open.add(0, start);

    while(!done)
    {
      qNode = findLowestFValue(open);
      closed.add(0, qNode);
      open.remove(qNode); //Remove might not work properly for this purpose but we will see
      adjTiles = qNode.getAdjacentTiles(gridWidth, gridHeight, closed, grid, units, playerManager);
      for(PathNode currAdj: adjTiles)
      {
        if(!open.contains(currAdj))
        {
          currAdj.setParent(qNode);
          currAdj.setH(GameConstants.distSquaredBetweenPoints(currAdj.getX(), currAdj.getY(), goal.getX(), goal.getY()));
          currAdj.setG(qNode.getG()
                  + GameConstants.distSquaredBetweenPoints(currAdj.getX(), currAdj.getY(), qNode.getX(), qNode.getY()));
          currAdj.calculateF();
          open.add(0, currAdj);
        }
        else if(currAdj.getG() > qNode.getG()
                + GameConstants.distSquaredBetweenPoints(currAdj.getX(), currAdj.getY(), qNode.getX(), qNode.getY()))
        {
          currAdj.setParent(qNode);
          currAdj.setG(qNode.getG()
                  + GameConstants.distSquaredBetweenPoints(currAdj.getX(), currAdj.getY(), qNode.getX(), qNode.getY()));
          currAdj.setH(GameConstants.distSquaredBetweenPoints(currAdj.getX(), currAdj.getY(), goal.getX(), goal.getY()));
          currAdj.calculateF();
        }
        if(currAdj.equals(goal))
        {
          return buildPath(start, currAdj);
        }
      }
      if(open.isEmpty())
      {
        return emptyList;
      }
    }
    return null;
  }

  private ArrayList<PathNode> buildPath(PathNode start, PathNode goal)
  {
    ArrayList<PathNode> path = new ArrayList<PathNode>();
    PathNode current = goal;
    boolean done = false;
    while(!done)
    {
      path.add(0, current);
      if(current.equals(start))
      {
        done = true;
        break;
      }
      current = current.getParent();
      if(current.equals(start))
      {
        done = true;
        path.add(0, current);
      }
    }
    return path;
  }

  private PathNode findLowestFValue(ArrayList<PathNode> list)
  {
    double lowestF = list.get(0).getF();
    PathNode lowestFPathNode = list.get(0);
    for(PathNode p: list)
    {
      if(p.getF() <  lowestF)
      {
        lowestFPathNode = p;
        lowestF = p.getF();
      }
    }

    return lowestFPathNode;
  }

  public void setUnits(ArrayList<UnitEntity> units)
  {
    this.units = units;
  }
}
