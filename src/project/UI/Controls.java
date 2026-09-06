package project.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class Controls {

    public static boolean leftPressed = false;
    public static boolean rightPressed = false;

    public static final int MOVE_SPEED = 4; // pixels per tick for A/D

    public static void initializeControls(JFrame frame, GameMap map) {

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    leftPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    rightPressed = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    //System.out.println(GameMap.player[0].isGrounded());
                    if (GameMap.player[0] != null && GameMap.player[0].isGrounded()) {
                        GameMap.player[0].startJump();
                    }
                
                }

                // TESTING
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    GameMap.player[0].setPixelY(GameMap.player[0].getPixelY()+250);
                    System.out.println(GameMap.player[0].getPixelY());
                }
                if (e.getKeyCode() == KeyEvent.VK_V) {
                    GameMap.player[0].setPixelY(GameMap.player[0].getPixelY()-250);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    leftPressed = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    rightPressed = false;
                }
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // required by interface, unused
            }
        });

       
    }
}
