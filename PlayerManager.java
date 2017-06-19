package kleyba.gameDev.turnBased.logic;

import java.util.ArrayList;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class PlayerManager
{
  private ArrayList<GamePlayer> players;
  private GamePlayer currentPlayer;
  private int numPlayers = 0;

  public PlayerManager()
  {
    players = new ArrayList<GamePlayer>();
  }

  public void add(GamePlayer player)
  {
    players.add(player);
    numPlayers++;
  }

  public void setCurrentPlayer(int index)
  {
    for(GamePlayer player: players)
    {
      player.setTurn(false);
    }
    currentPlayer = players.get(index);
    currentPlayer.setTurn(true);
  }

  public void setNextPlayer()
  {
    int currentIndex = players.indexOf(currentPlayer);
    players.get(currentIndex).setTurn(false);
    if (currentIndex == numPlayers-1)
    {
      currentIndex = 0;
    } else
    {
      currentIndex++;
    }
    currentPlayer = players.get(currentIndex);
    currentPlayer.setTurn(true);
  }

  public int getNumPlayers()
  {
    return numPlayers;
  }

  public GamePlayer getCurrentPlayer()
  {
    return currentPlayer;
  }

  public ArrayList<GamePlayer> getPlayers()
  {
    return players;
  }
}
