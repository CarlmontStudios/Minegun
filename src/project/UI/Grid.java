package project.UI;

public class Grid {

    //in grid blocks not pixels
    public final int MAP_WIDTH = 100;
    public final int MAP_HEIGHT = 1000;
    public final int[][] grid = new int[MAP_WIDTH][MAP_HEIGHT];
    public static final int BLOCK_SIZE = 40; //in pixels



    public static enum Direction {
        UP, 
        DOWN, 
        LEFT, 
        RIGHT
    }

    public Grid(){
        initializeGrid();
    }

    public void initializeGrid(){
        for(int i = 0; i < MAP_WIDTH; i++){
            for(int j = 0; j < MAP_HEIGHT; j++){
                grid[i][j] = 1;
            }
        }

        //create spawn area
        for(int i = MAP_WIDTH/2 - 2; i < MAP_WIDTH/2 + 2; i++){ // width gets 48, 49, 50, 51
            for(int j = MAP_HEIGHT - 3; j < MAP_HEIGHT - 9; j++){ // height gets 987-990, 
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
}
