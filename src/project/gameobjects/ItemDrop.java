package project.gameobjects;

import java.io.IOException;

import javax.swing.Timer;

import project.UI.Grid;
import project.UI.Sprite;

public class ItemDrop {
    public static void Drop(String imagePath, int centerX, int centerY, int width, int height, int blockX, int blockY)
    {
        try {
                Sprite sprite = new Sprite(imagePath,0,0, width, height);
                sprite.setCanMove(true);
                sprite.setPixelX(centerX);
                sprite.setPixelY(centerY);
                Grid.sprites.add(sprite);
                Grid.itemDrops.add(sprite);

                // animation logic

                final int[] ticks = {1};

                // 50% chance to multiply xDistance by -1, reversing the horizontal direction which the item drops
                int a = (int)(Math.random()*2);

                // maximum x distance dropped from block is equal to the size of 1/2 block, minimum distance is 1
                int xDistance = (int)(Math.pow(-1,a))*(int)(Math.random()*(Grid.BLOCK_SIZE/2)+1);
                // y variance is between BLOCK_SIZE/2 and -BLOCK_SIZE/2
                int yVariance = (int)(Math.random()*Grid.BLOCK_SIZE/2) - Grid.BLOCK_SIZE/4;
                // animation loop
                Timer timer = new Timer(50, e -> {
                ticks[0]++;
                sprite.setItemDropTicks(ticks[0]);
                // item drop can only move if not already being pulled towards the player
                if (sprite.canMove())
                {
                    // over 10 ticks, the item will travel a total of xDistance units
                        sprite.setPixelX(centerX+(xDistance*ticks[0]/10));
                        // over 10 ticks, 10*Math.sin(ticks[0]*2*Math.PI/20 becomes 0 at the last tick
                        // the yVariance is added to vary the ending location where the item drops (remove it to see what happens differently)
                        sprite.setPixelY((int)(centerY-10*Math.sin(ticks[0]*2*Math.PI/20))+(int)(yVariance*ticks[0]/10));
                    // 20 fps, 0.5 second
                }
                if (ticks[0] >= 10) {
                    (   (Timer) e.getSource()).stop(); // Stops the timer from inside itself;            
                }
                        });

                timer.start();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }
}
