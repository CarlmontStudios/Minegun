//package project;

import javax.swing.Timer;
import UI.*;
import gameobjects.Player;
import gameobjects.Projectile;
import gameobjects.Worm;


public class Main {
    public static void main (String[] args) {
        Screen screen = new Screen();
        
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

                
            }
            if (0 < Math.random() && Math.random() < 0.01) {
                reportWormSprites();
                reportBlockSprites();
            }

            // worms should spawn roughly every 20 seconds.
            // 16 miliseconds = 0.016 seconds. In a 20 second period, this will repeat 20/0.016 = 1250 times
             if (Math.random() < 1.0 / 1250) { // should be 1250
                int x_radius = 20; // how horizontally far from the player the head of the worm can spawn
                int y_radius = 200; // how horizontally far from the player the head of the worm can spawn, should be 200

                int rightBound = Player.getX() + x_radius; 
                int leftBound = Player.getX() - x_radius;
                if (rightBound >= 100) {
                    rightBound = 99;
                }
                if (leftBound < 0) {
                    leftBound = 0;
                }

                int upperBound = Player.getY() - y_radius; 
                int lowerBound = Player.getY() + y_radius;
                if (upperBound < 0) {
                    upperBound = 0;
                }
                if (lowerBound >= 1000) {
                    lowerBound = 999;
                }

                boolean reporting = true;
                if (reporting) {
                    System.out.println("Upper: " + upperBound);
                    System.out.println("Lower: " + lowerBound);
                    System.out.println("Right: " + rightBound);
                    System.out.println("Left: " + leftBound);
                }
                int wormX = leftBound + (int) (Math.random() * (rightBound - leftBound + 1));
                int wormY = upperBound + (int) (Math.random() * (lowerBound - upperBound + 1));

                System.out.println("wormX: " + wormX);
                System.out.println("wormY: " + wormY);
                Screen.worms.add(new Worm(Worm.Type.L1, wormX, wormY));
            }
                
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
            System.out.println("Image Path : " + sp.getImagePath());
            System.out.println("Pixel Position: " + sp.getPixelX() + ", " + sp.getPixelY());
            System.out.println("Width / Height: " + sp.getWidth() + " / " + sp.getHeight());
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