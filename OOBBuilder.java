package kleyba.gameDev.turnBased.FileIO;

import kleyba.gameDev.turnBased.entities.*;
import kleyba.gameDev.turnBased.logic.GamePlayer;
import kleyba.gameDev.turnBased.logic.UnitManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class OOBBuilder
{
  private ArrayList<UnitEntity> oOB = new ArrayList<UnitEntity>();
  private ArrayList<GamePlayer> players = new ArrayList<GamePlayer>();
  private int gridWidth;
  private int gridHeight;
  private UnitEntity temp;

  public OOBBuilder(ArrayList<GamePlayer> players)
  {
    this.players = players;
  }

  public void readOOBFile(String oOBFileName, String OwnerFileName, TileEntity[] grid, int gridWidth, int gridHeight, UnitManager unitManager)
  {
    this.gridWidth = gridWidth;
    this.gridHeight = gridHeight;
    try
    {
      FileReader oOBFileReader = new FileReader(oOBFileName);
      BufferedReader bufferedReader = new BufferedReader(oOBFileReader);
      FileReader ownerFileReader = new FileReader(OwnerFileName);
      BufferedReader ownerBufferedReader = new BufferedReader(ownerFileReader);

      String lineOOB;
      String lineOwner;
      int currentY = 0;
      while((lineOOB = bufferedReader.readLine()) != null)
      {
        lineOwner = ownerBufferedReader.readLine();
        for(int currentX = 0; currentX < gridWidth; currentX++)
        {
          if(lineOOB.charAt(currentX) == 'P') //pirate
          {
            temp = new PirateUnit(currentX, currentY, players.get(Character.getNumericValue(lineOwner.charAt(currentX))), grid, unitManager);
            oOB.add(temp);
          }
          else if(lineOOB.charAt(currentX) == 'R') //robot
          {
            temp = new RobotUnit(currentX, currentY, players.get(Character.getNumericValue(lineOwner.charAt(currentX))), grid, unitManager);
            oOB.add(temp);
          }
        }
        currentY++;
      }
    }
    catch(FileNotFoundException e)
    {
      System.out.println("OOB File not found");
    }
    catch(IOException e)
    {
      System.out.println("OOB File reading error");
    }
  }

  public ArrayList<UnitEntity> getoOB()
  {
    return oOB;
  }

}
