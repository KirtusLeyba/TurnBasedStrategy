package kleyba.gameDev.turnBased.FileIO;

import javafx.scene.image.Image;
import kleyba.gameDev.turnBased.entities.GrassTile;
import kleyba.gameDev.turnBased.entities.TileEntity;
import kleyba.gameDev.turnBased.entities.WaterTile;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class GridBuilder
{
  private ArrayList<TileEntity> gridList = new ArrayList<TileEntity>();
  private TileEntity[] grid;
  private int gridWidth;
  private int gridHeight;
  private TileEntity temp;

  private Image grassTileSimpleSprite = new Image(getClass().getResourceAsStream("sprites/tiles/grassTileSimple.png"));
  private Image waterTileSimpleSprite = new Image(getClass().getResourceAsStream("sprites/tiles/waterTileSimple.png"));

  public void readMapFile(String fileName)
  {
    try
    {
      FileReader fileReader = new FileReader(fileName);
      BufferedReader bufferedReader = new BufferedReader(fileReader);
      String line;
      int currentY = 0;

      while((line = bufferedReader.readLine()) != null)
      {
        gridWidth = line.length();
        for(int currentX = 0; currentX < gridWidth; currentX++)
        {
          if(line.charAt(currentX) == 'G')
          {
            temp = new GrassTile(currentX, currentY, grassTileSimpleSprite);
          }
          else if(line.charAt(currentX) == '~')
          {
            temp = new WaterTile(currentX, currentY, waterTileSimpleSprite);
          }
          gridList.add(temp);
        }
        currentY++;
      }
      gridHeight = currentY;
      bufferedReader.close();

    } catch (FileNotFoundException e)
    {
      System.out.println("Grid File not found!");
    } catch (IOException e)
    {
      System.out.println("Error reading grid file!");
    }

    grid = new TileEntity[gridList.size()];
    for(int i = 0; i < gridList.size(); i++)
    {
      grid[i] = gridList.get(i);
    }
  }

  public TileEntity[] getGrid()
  {
    return grid;
  }
  public int getGridWidth()
  {
    return gridWidth;
  }
  public int getGridHeight()
  {
    return gridHeight;
  }

}
