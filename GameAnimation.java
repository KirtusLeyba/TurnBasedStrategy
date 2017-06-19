package kleyba.gameDev.turnBased.logic;

import javafx.scene.image.Image;

/**
 * Created by Kirtus on 4/15/2017.
 */
public class GameAnimation
{
  private Image[] frames;
  private int numFrames;
  private int currentFrameIndex;
  private int animationTime;
  private int framesSinceLastFrame = 0;

  public GameAnimation(Image[] frames, int animationTime)
  {
    this.frames = frames;
    this.animationTime = animationTime;
    numFrames = frames.length;
    currentFrameIndex = 0;
  }

  private void nextFrame()
  {
    if(currentFrameIndex == numFrames-1)
    {
      currentFrameIndex = 0;
    }
    else
    {
      currentFrameIndex++;
    }
    framesSinceLastFrame = 0;
  }

  public void animateUpdate(long deltaTime)
  {
    framesSinceLastFrame++;
    if (framesSinceLastFrame >= animationTime)
    {
      nextFrame();
    }
  }

  public Image getCurrentFrame()
  {
    return frames[currentFrameIndex];
  }

}
