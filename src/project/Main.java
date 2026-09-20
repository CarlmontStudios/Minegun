package project;

import javax.swing.Timer;
import project.UI.*;
import project.gameobjects.Projectile;


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