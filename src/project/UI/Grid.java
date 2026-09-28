package UI;

import java.awt.Color;
import java.util.ArrayList;

public class Grid {

    //in grid blocks not pixels
    public static final int MAP_WIDTH = 100;
    public static final int MAP_HEIGHT = 1000;

    //1 represents a solid block, 0 represents empty space
    public static final int[][] grid = new int[MAP_WIDTH][MAP_HEIGHT]; //grid for blocks

    //public static final Hitbox[][] hitbox_grid = new Hitbox[MAP_WIDTH][MAP_HEIGHT]; //grid for hitboxes
    public static final int[][] worm_grid = new int[MAP_WIDTH][MAP_HEIGHT]; //grid for worm positions
    public static final int[][] block_health_grid = new int[MAP_WIDTH][MAP_HEIGHT]; //grid for block health
    public static final int[][] block_max_health_grid = new int[MAP_WIDTH][MAP_HEIGHT]; //grid for block max health
    public static final Color[][] color_grid = new Color[MAP_WIDTH][MAP_HEIGHT]; //grid for block colors

    public static final Sprite[][] block_sprite_grid = new Sprite[MAP_WIDTH][MAP_HEIGHT]; //grid for block sprites
    public static final ArrayList<Sprite> sprites = new ArrayList<>(); //list of sprites in the game (thinking of using this for sprites that have animations)
    public static final int BLOCK_SIZE = 40; //in pixels



    public static enum Direction {
        UP, 
        DOWN, 
        LEFT, 
        RIGHT
    }

    public Grid(){
        // grid = new int[MAP_WIDTH][MAP_HEIGHT]; //grid for blocks
        // worm_grid = new int[MAP_WIDTH][MAP_HEIGHT]; //grid for worm positions
        initializeGrid();
        
    }

    public void initializeGrid(){
        for(int i = 0; i < MAP_WIDTH; i++){
            for(int j = 0; j < MAP_HEIGHT; j++){
                grid[i][j] = 1;
                worm_grid[i][j] = 0;
                block_health_grid[i][j] = 5;
                block_max_health_grid[i][j] = 5;
                Color baseColor = Screen.DIRT_COLOR_0; //TODO: make variation depending on y level
                int variation_r = (int)(Math.random() * 4) - 2; // Random variation between -4 and +4
                int variation_g = (int)(Math.random() * 4) - 2; // Random variation between -4 and +4
                color_grid[i][j] = new Color(
                    Math.max(0, Math.min(255, baseColor.getRed() + variation_r+70-(j/25))),
                    Math.max(0, Math.min(255, baseColor.getGreen() + variation_g+20-(j/45))),
                    0);
            }
        }

        //create spawn area
        for(int i = MAP_WIDTH/2 - 2; i < MAP_WIDTH/2 + 2; i++){ // width gets 48, 49, 50, 51
            for(int j = MAP_HEIGHT - 13; j < MAP_HEIGHT - 10 ; j++){ // height gets 987-990, 
                grid[i][j] = 0;
                // grid[i-4][j] = 0;
                // grid[i-3][j+1] = 0;
                // grid[i-4][j+2] = 0;
                // grid[i-4][j+3] = 0;
            }
        }

        generateSpecialBlocks();

        
    }

    private void generateSpecialBlocks(){
        for (int i = 0; i < MAP_WIDTH; i++) {
            for (int j = 0; j < MAP_HEIGHT; j++) {
                if (grid[i][j] == 1) { // Only consider solid blocks

                    double ironChance = Math.random();
                    double ironChanceMultiplier = (double) j / MAP_HEIGHT;
                    if (ironChance  < 0.01 * ironChanceMultiplier) { // 1% chance for iron block but decreases as you go up
                        
                        block_health_grid[i][j] = 15; //maxHealth of 15 indicates iron block (this will be used for rendering)
                        block_max_health_grid[i][j] = 15;
                    }
                }
            }
        }
    }

    public static Direction getRandomDirection(){
        int rand = (int)(Math.random() * 4);
        switch(rand){
            case 0:
                return Direction.UP;
            case 1:
                return Direction.DOWN;
            case 2:
                return Direction.LEFT;
            case 3:
                return Direction.RIGHT;
            default:
                return Direction.UP; //should never happen
        }
    }

    /**
     * gets direction by taking an input that uses the standard coordinate system (going counterclockwise)
     * @param degrees
     * @return
     */
    public static Direction degreesToDirection(double degrees) {
        // Normalize degrees to be within [0, 360)
        degrees = degrees % 360;
        if (degrees < 0) {
            degrees += 360;
        }

        if (degrees >= 45 && degrees < 135) {
            return Direction.UP;
        } else if (degrees >= 135 && degrees < 225) {
            return Direction.LEFT;
        } else if (degrees >= 225 && degrees < 315) {
            return Direction.DOWN;
        } else {
            return Direction.RIGHT;
        }
    }
    /**
     * gets direction by taking an input that uses swing's coordinate system (going clockwise)
     * @param degrees
     * @return
     */
    public static Direction degreesToDirection_swing_coords(double degrees) {
        // Normalize degrees to be within [0, 360)
        degrees = degrees % 360;
        if (degrees < 0) {
            degrees += 360;
        }

        if (degrees >= 45 && degrees < 135) {
            return Direction.DOWN;
        } else if (degrees >= 135 && degrees < 225) {
            return Direction.LEFT;
        } else if (degrees >= 225 && degrees < 315) {
            return Direction.UP;
        } else {
            return Direction.RIGHT;
        }
    }

    public static int directionToDegrees(Direction direction) {
        switch (direction) {
            case UP:
                return 90;
            case DOWN:
                return 270;
            case LEFT:
                return 180;
            case RIGHT:
                return 0;
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    public static boolean isEdgeBlock(int x, int y) {
        if (x < 0 || x >= MAP_WIDTH - 1 || y < 0 || y >= MAP_HEIGHT - 1) {
            return false; // block is at the border and counts as edge block
        }
        if (grid[x][y] == 1) { // Only consider solid blocks
            // Check the four adjacent blocks
            if (grid[x - 1][y] == 0) return true; // Left
            if (grid[x + 1][y] == 0) return true; // Right
            if (y > 0 && grid[x][y - 1] == 0) return true; // Up
            if (y < MAP_HEIGHT - 1 && grid[x][y + 1] == 0) return true; // Down
        }
        return false;
    }

    public static boolean wormCanSpawn(int x, int y){
        return worm_grid[x][y] == 0 && grid[x][y] == 1;
    }
}
