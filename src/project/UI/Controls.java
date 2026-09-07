package project.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;

public class Controls {

    public static boolean leftPressed = false;
    public static boolean rightPressed = false;
    public static boolean leftClickPressed = false;

    public static final double MINING_COOLDOWN = 0.2; // seconds
    public static double miningCooldownTimer = 0; // seconds

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
                if (e.getKeyCode() == KeyEvent.VK_1) {
                    Hotbar.selectedSlot = 0;
                }
                if (e.getKeyCode() == KeyEvent.VK_2) {
                    Hotbar.selectedSlot = 1;
                }
                if (e.getKeyCode() == KeyEvent.VK_3) {
                    Hotbar.selectedSlot = 2;
                }
                if (e.getKeyCode() == KeyEvent.VK_4) {
                    Hotbar.selectedSlot = 3;
                }
                if (e.getKeyCode() == KeyEvent.VK_5) {
                    Hotbar.selectedSlot = 4;
                }
                if (e.getKeyCode() == KeyEvent.VK_6) {
                    Hotbar.selectedSlot = 5;
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

        map.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    leftClickPressed = true;
                    
                }
            }
 
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    leftClickPressed = false;
                }
            }
        });

       
    }

}
