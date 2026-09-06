package project.UI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;
import project.gameobjects.Player;

public class Controls {

    private static boolean leftPressed = false;
    private static boolean rightPressed = false;

    private static final int MOVE_SPEED = 4; // pixels per tick for A/D

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
                    Player.startJump();
                }

                // TESTING
                if (e.getKeyCode() == KeyEvent.VK_C) {
                    Player.setPixelY(Player.getPixelY()+250);
                    System.out.println(Player.getPixelY());
                }
                if (e.getKeyCode() == KeyEvent.VK_V) {
                    Player.setPixelY(Player.getPixelY()-250);
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
        //TODO: We might want to change this to deal with falling (right now you can jump up and down but can't fall)
        // drives both horizontal movement and the jump arc
        Timer physicsTimer = new Timer(16, e -> {
            if (leftPressed) {
                Player.moveLeft(MOVE_SPEED);
            }
            if (rightPressed) {
                Player.moveRight(MOVE_SPEED);
            }

            Player.updateJump();

            map.repaint();
        });
        physicsTimer.start();
    }
}
