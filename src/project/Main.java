package project;

import javax.swing.Timer;
import project.UI.*;
import project.gameobjects.Player;
import project.gameobjects.Projectile;


public class Main {
    public static void main (String[] args) {
        Screen screen = new Screen();
        final int[] counter = {0};
        Timer timer = new Timer(16, e -> {
            
            if (Screen.map[0] != null){

                Screen.map[0].repaint();

                if (Controls.leftClickPressed) {
                    //for mining
                    if (Hotbar.selectedSlot == 0 && Controls.miningCooldownTimer <= 0){

                        Controls.miningCooldownTimer = Controls.MINING_COOLDOWN; // reset cooldown
                        //System.out.println("projectile firing");
                        new Projectile(Projectile.Type.MINING_PROJECTILE);
                    }
                }

                Controls.miningCooldownTimer -= 0.016; // decrease cooldown by 16 milliseconds (0.016 seconds)
                if (Controls.miningCooldownTimer < 0) {
                    Controls.miningCooldownTimer = 0; // ensure it doesn't go below 0
                }
                

                // check every 16*16 ms
                if (counter[0] % 1 == 0)
                {
                    // item drops move closer to the player if within a certain radius of the player
                    // iterate through all item drops to find the ones close to the player
                    for (int i = 0; i < Grid.itemDrops.size(); i++)
                    {
                        // calculate distance between player and sprite
                        Sprite sprite = Grid.itemDrops.get(i);
                        double distance = (Math.sqrt(Math.pow(sprite.getPixelX()-Player.pixelX,2)+Math.pow(sprite.getPixelY()-Player.pixelY,2)));
                        // item drops must be within a certain radius
                        // additionally, the item must have had at least 5 ticks of drop animation before changing to magnet animation
                        if (distance < 80 && distance > 10 && sprite.getItemDropTicks() > 5)
                        {
                            // prevents the item from falling w/ gravity if it is in magnet range of player
                            sprite.setCanMove(false);
                            // check if item should move left or right (towards player)
                            if (sprite.getPixelX() > Player.pixelX)
                            {
                                sprite.setPixelX(sprite.getPixelX()-5);
                            }
                            else if (sprite.getPixelX() < Player.pixelX)
                            {
                                sprite.setPixelX(sprite.getPixelX()+5);
                            }

                            // check if item should move up or down (towards player)
                            if (Math.abs(sprite.getPixelY()-Player.pixelY) > 2)
                            {
                                // add 0.5*Grid.BLOCK_SIZE so the item goes to the center of the player, rather than the player's head
                                if (sprite.getPixelY() > Player.pixelY+ (0.5*Grid.BLOCK_SIZE))
                                {
                                    sprite.setPixelY(sprite.getPixelY()-2);
                                }
                                else if (sprite.getPixelY() < Player.pixelY+ (0.5*Grid.BLOCK_SIZE))
                                {
                                    sprite.setPixelY(sprite.getPixelY()+2);
                                }
                            }
                            // if the item is close enough, let it be picked up (and delete the sprite)
                            if (Math.abs(sprite.getPixelY()-Player.pixelY) <= 25 && Math.abs(sprite.getPixelX()-Player.pixelX) <= 25)
                            {
                                Grid.sprites.remove(sprite);
                                Grid.itemDrops.remove(sprite);
                            }
                        }
                    }

                }
            }
            if (0 < Math.random() && Math.random() < 0.01) {
                reportWormSprites();
                reportBlockSprites();
            }
            counter[0]++;
        });
        timer.start();

        // boolean testing_sprites = true;
        // try{
        //     if (testing_sprites) {
        //         Sprite testSprite = new Sprite(Sprite.WORM_TEST, 100, 100);
        //         testSprite.setPixelX(400);
        //         testSprite.setPixelY(400);
        //         Grid.sprites.add(testSprite);
        //     }
        // } catch (IOException ex) {
        //     ex.printStackTrace();
        // }

    }

    public static void reportWormSprites() {
        System.out.println("Printing Report");
        for (Sprite sp : Grid.sprites){
            // System.out.println("Image Path : " + sp.getImagePath());
            // System.out.println("Pixel Position: " + sp.getPixelX() + ", " + sp.getPixelY());
            // System.out.println("Width / Height: " + sp.getWidth() + " / " + sp.getHeight());
        }
    }

    public static void reportBlockSprites() {
        System.out.println("Printing Report");
        for (Sprite[] sps : Grid.block_sprite_grid){
            for (Sprite sp : sps) {
                // System.out
                if (sp != null) {
                    System.out.println("just entered if (sp != null)");
                    System.out.println(sp != null);
                    System.out.println("Image Path : " + sp.getImagePath());
                    System.out.println("Pixel Position: " + sp.getPixelX() + ", " + sp.getPixelY());
                    System.out.println("Width / Height: " + sp.getWidth() + " / " + sp.getHeight());
                }
            }
        }
    }
}

        
//         boolean testing_worm_algorithm = false;
//         if (testing_worm_algorithm) {
//             Grid myGrid = new Grid();
//             myGrid.initializeGrid();

//             Worm worm = new Worm(Worm.Type.L1, 50, 50);
//             System.out.println("here");
//             System.out.println(worm.body.length);
//             for (int i = 0; i < worm.body.length; i++) {
//                 System.out.println("Segment" + i + ": ("
//                     + worm.body[i].getX() + ", " + worm.body[i].getY()
//                 );
//                 // System.out.println("Segement " + i + ": " + "(" + worm.body[i].getX() + ", " + worm.body[i].getY() + ")");
//             }
//         } else {
//             Screen screen = new Screen();
//         }

//     }

// }