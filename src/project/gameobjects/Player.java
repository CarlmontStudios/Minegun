package project.gameobjects;

import java.awt.Color;
import project.UI.Grid;

public class Player {

    // player's exact position in pixels, using the same coordinate system
    // as the grid: origin top-left, x increases right, y increases down
    private static double pixelX;
    private static double pixelY;

    // starting position, in grid squares (whole blocks)
    private static final int START_GRID_X = 50;
    private static final int START_GRID_Y = 989; // 10 blocks above the bottom row (999)

    // rendering (top-left corner at pixelX/pixelY, same convention as grid squares)
    public static final int PLAYER_WIDTH = Grid.BLOCK_SIZE;
    public static final int PLAYER_HEIGHT = Grid.BLOCK_SIZE;

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

    private static double groundPixelY; // resting (non-jumping) y position
    private static boolean isJumping = false;
    private static double velocityY = 0;

    public Player(){
        reset();
    }

    public static void reset() {
        pixelX = START_GRID_X * Grid.BLOCK_SIZE;
        pixelY = START_GRID_Y * Grid.BLOCK_SIZE;
        groundPixelY = pixelY;
        isJumping = false;
        velocityY = 0;
    }

    // --- movement ---
    //TODO:
    //Make the player unable to move off the map and unable to move through solid blocks
    //In order to do this you need to make it so that if the player's new coords would make it collide with a solid block it wont move
    public static void moveLeft(int amount) {
        pixelX -= amount;
    }

    public static void moveRight(int amount) {
        pixelX += amount;
    }

    public static void startJump() {
        if (!isJumping) {
            isJumping = true;
            velocityY = JUMP_VELOCITY;
        }
    }

    public static boolean isJumping() {
        return isJumping;
    }

    /** Advances the jump arc by one physics tick. Call this every frame. */
    public static void updateJump() {
        if (!isJumping) {
            return;
        }

        double offset = pixelY - groundPixelY; // negative while airborne
        offset += velocityY;
        velocityY += G;

        // safety clamp: never let discrete-time stepping overshoot the cap
        if (offset < -MAX_JUMP_HEIGHT) {
            offset = -MAX_JUMP_HEIGHT;
            if (velocityY < 0) {
                velocityY = 0; // begin falling immediately
            }
        }

        // landed
        if (offset >= 0) {
            offset = 0;
            velocityY = 0;
            isJumping = false;
        }

        pixelY = groundPixelY + offset;
    }

    // --- coordinate accessors ---

    /** Exact pixel position (origin top-left). */
    public static double getPixelX() {
        return pixelX;
    }
    public static double getPixelY() {
        return pixelY;
    }

    public static void setPixelX(double x) {
        pixelX = x;
    }
    public static void setPixelY(double y) {
        pixelY = y;
    }

    /** Which grid square (whole block number, origin top-left) the player is in. */
    public static int getGridX() {
         return (int) Math.floor(pixelX / Grid.BLOCK_SIZE);
        }
    public static int getGridY() {
        return (int) Math.floor(pixelY / Grid.BLOCK_SIZE);
        }
}