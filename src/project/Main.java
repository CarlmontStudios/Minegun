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
                    if (Controls.miningCooldownTimer <= 0){

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
