package project.gameobjects;

import java.awt.Color;
import javax.swing.Timer;
import project.UI.Controls;
import project.UI.GameMap;
import project.UI.Grid;
import project.UI.Hitbox;
import project.UI.Screen;

public class Player {

    // player's exact position in pixels, using the same coordinate system
    // as the grid: origin top-left, x increases right, y increases down
    private static int pixelX;
    private static int pixelY;

    // starting position, in grid squares (whole blocks)
    private static final int START_GRID_X = 50;
    private static final int START_GRID_Y = 988; // 10 blocks above the bottom row (999)

    // rendering (top-left corner at pixelX/pixelY, same convention as grid squares)
    // in grid squares
    public static final int PLAYER_WIDTH = 1;
    public static final int PLAYER_HEIGHT = 2;

    //This is temporary
    //TODO: change the player representation to a sprite
    //TODO: Also create a hitbox to represent the player
    public static final java.awt.Color PLAYER_COLOR = Color.GREEN;

    // --- jump physics ---
    public static final double MAX_JUMP_HEIGHT = 1.5 * Grid.BLOCK_SIZE; // pixels (1.25 blocks)
    
    //TODO: We need to make it so that when the player is not on a solid block they will fall down. Fall damage can be implemented later
    public static final double G = 1.5;                           // pixels/tick^2
    // initial upward speed, derived so the natural peak of the arc lands
    // right at MAX_JUMP_HEIGHT (v0 = sqrt(2 * g * h))
    private static final double JUMP_VELOCITY = -Math.sqrt(2 * G * MAX_JUMP_HEIGHT);

    //private static double groundPixelY; // resting (non-jumping) y position
    //private static boolean isJumping = false;
    private double velocityY = 0;

    private Hitbox hitbox;

    public Player(){
        reset();
        velocityY = 0;
        GameMap.player[0] = this; //cool hack to allow player to be treated as static without making it static
        pixelX = START_GRID_X * Grid.BLOCK_SIZE;
        pixelY = START_GRID_Y * Grid.BLOCK_SIZE;
        hitbox = new Hitbox(START_GRID_X, START_GRID_Y, PLAYER_WIDTH, PLAYER_HEIGHT);

         //TODO: We might want to change this to deal with falling (right now you can jump up and down but can't fall)
        // drives both horizontal movement and the jump arc
        Timer physicsTimer = new Timer(16, e -> {
            if (Controls.leftPressed) {
                moveLeft(Controls.MOVE_SPEED);
            }
            if (Controls.rightPressed) {
                moveRight(Controls.MOVE_SPEED);
            }
            

            updateJump();

            Screen.map[0].repaint();
        });
        physicsTimer.start();
    }

    public static void reset() {
        pixelX = START_GRID_X * Grid.BLOCK_SIZE;
        pixelY = START_GRID_Y * Grid.BLOCK_SIZE;
        // groundPixelY = pixelY;
        // isJumping = false;
        
    }

    // --- movement ---
    //TODO:
    //Make the player unable to move off the map and unable to move through solid blocks
    //In order to do this you need to make it so that if the player's new coords would make it collide with a solid block it wont move
    public void moveLeft(int amount) {
        pixelX -= amount;
        hitbox.setPixelX((int) pixelX);
        if (hitbox.collidingWithSolidBlock()) {
            hitbox.setX((int) Math.ceil((pixelX + amount) / 40)); //for preventing collision
            pixelX = hitbox.getPixelX();
        }
    }

    public void moveRight(int amount) {
        pixelX += amount;
        hitbox.setPixelX((int) pixelX);
        if (hitbox.collidingWithSolidBlock()) {
            hitbox.setX((int) Math.floor((pixelX - amount) / 40)); //for preventing collision
            pixelX = hitbox.getPixelX();
        }
    }

    public void startJump() {
        
        velocityY = JUMP_VELOCITY;
    }

    // public static boolean isJumping() {
    //     return isJumping;
    // }

    /** Advances the jump arc by one physics tick. Call this every frame. */
    public void updateJump() {

        pixelY += velocityY;
        hitbox.setPixelY((int) pixelY);
        velocityY += G;

        if (isHittingRoof()) {
            velocityY = 0;
            hitbox.setY((int) Math.ceil(pixelY / 40.0)); //for preventing collision
            pixelY = hitbox.getPixelY();
            //System.out.println("Hit roof");
            return;
        }

        if (isGrounded()) {
            velocityY = 0;
            hitbox.setY(pixelY / 40); //for preventing collision
            pixelY = hitbox.getPixelY();
            //System.out.println("Hit ground");
            //return;
        }
    }

    // --- coordinate accessors ---

    /** Exact pixel position (origin top-left). */
    public int getPixelX() {
        return pixelX;
    }
    public int getPixelY() {
        return pixelY;
    }

    public void setPixelX(int x) {
        pixelX = x;
    }
    public void setPixelY(int y) {
        pixelY = y;
    }

    /** Which grid square (whole block number, origin top-left) the player is in. */
    public int getGridX() {
         return (int) Math.floor(pixelX / Grid.BLOCK_SIZE);
    }
    public int getGridY() {
        return (int) Math.floor(pixelY / Grid.BLOCK_SIZE);
    }

    public boolean isGrounded(){
        //in pixel units
        int bottomY = (int) (pixelY + PLAYER_HEIGHT * Grid.BLOCK_SIZE); 
        int leftX = (int) pixelX;
        for (int x = leftX; x < leftX + PLAYER_WIDTH * Grid.BLOCK_SIZE; x++) {
            if (Hitbox.collidingWithSolidBlock(x, bottomY)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHittingRoof(){
        
        for (int x = pixelX; x < pixelX + PLAYER_WIDTH * Grid.BLOCK_SIZE; x++) {
            if (Hitbox.collidingWithSolidBlock(x, pixelY)) {
                return true;
            }
        }
        return false;
    }

    
    
    
}