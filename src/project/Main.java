package project;

import project.UI.*;


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
        });
        timer.start();
    }

}

        
        boolean testing_worm_algorithm = false;
        if (testing_worm_algorithm) {
            Grid myGrid = new Grid();
            myGrid.initializeGrid();

            Worm worm = new Worm(Worm.Type.L1, 50, 50);
            System.out.println("here");
            System.out.println(worm.body.length);
            for (int i = 0; i < worm.body.length; i++) {
                System.out.println("Segment" + i + ": ("
                    + worm.body[i].getX() + ", " + worm.body[i].getY()
                );
                // System.out.println("Segement " + i + ": " + "(" + worm.body[i].getX() + ", " + worm.body[i].getY() + ")");
            }
        } else {
            Screen screen = new Screen();
        }

    }

}