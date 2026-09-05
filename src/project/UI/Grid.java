package project.UI;

import java.awt.Color;

public class Grid {

    //in grid blocks not pixels
    public static final int MAP_WIDTH = 100;
    public static final int MAP_HEIGHT = 1000;
    public static final int[][] grid = new int[MAP_WIDTH][MAP_HEIGHT]; //grid for blocks
    public static final int[][] worm_grid = new int[MAP_WIDTH][MAP_HEIGHT]; //grid for worm positions
    public static final Color[][] color_grid = new Color[MAP_WIDTH][MAP_HEIGHT]; //grid for block colors
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
                Color baseColor = Screen.DIRT_COLOR_0; //TODO: make variation depending on y level
                int variation = (int)(Math.random() * 8) - 4; // Random variation between -4 and +4
                color_grid[i][j] = new Color(
                    Math.max(0, Math.min(255, baseColor.getRed() + variation)),
                    Math.max(0, Math.min(255, baseColor.getGreen() + variation)),
                    0);
            }
        }

        //create spawn area
        for(int i = MAP_WIDTH/2 - 2; i < MAP_WIDTH/2 + 2; i++){ // width gets 48, 49, 50, 51
            for(int j = MAP_HEIGHT - 13; j < MAP_HEIGHT - 10 ; j++){ // height gets 987-990, 
                grid[i][j] = 0;
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

    public static boolean wormCanSpawn(int x, int y){
        return worm_grid[x][y] == 0 && grid[x][y] == 1;
    }
}
