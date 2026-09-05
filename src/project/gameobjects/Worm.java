package project.gameobjects;

import project.UI.Grid;
import project.UI.Grid.Direction;
public class Worm {
    
    public static enum Type {
        //placeholder names, can change later
        L1,
        L2,
        L3
    }

    private WormBodyPart[] body;
    private int length;
    private Grid.Direction headDirection;
    private Type type;
    private int x; //represents x and y coordinates of the head of the worm
    private int y;

    //coordinates of the worm in pixels, used for movement
    private int pixelCoordsX; 
    private int pixelCoordsY;

    public Worm(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.pixelCoordsX = x * Grid.BLOCK_SIZE;
        this.pixelCoordsY = y * Grid.BLOCK_SIZE;
        headDirection = Grid.getRandomDirection();
        setupWorm();
    }


    public void setupWorm(){
        body = new WormBodyPart[length];
        for (int i = 0; i < length; i++) {
            Direction direction;
            int partX = x;
            int partY = y;
            if (i == 0){
                direction = headDirection; // Assuming the body parts follow the head's direction
            }
            else {
                int testX;
                int testY;
                direction = retrieveDirectionForBodyPart(i); // Get direction for the current body part
                //make it so that the body part is facing the previous body part
                switch (direction) {
                    case UP:
                        testY = partY + 1;
                        testX = partX;
                        break;
                    case DOWN:
                        testY = partY - 1;
                        testX = partX;
                        break;
                    case LEFT:
                        testX = partX + 1;
                        testY = partY;
                        break;
                    case RIGHT:
                        testX = partX - 1;
                        testY = partY;
                        break;
                default:
                    testX = partX;
                    testY = partY;
                }
                while (!Grid.wormCanSpawn(testX, testY)) {
                    direction = retrieveDirectionForBodyPart(i); // Get a new direction if the space is occupied
                    // Update testX and testY based on the new direction
                    switch (direction) {
                        case UP:
                            testY = partY + 1;
                            testX = partX;
                            break;
                        case DOWN:
                            testY = partY - 1;
                            testX = partX;
                            break;
                        case LEFT:
                            testX = partX + 1;
                            testY = partY;
                            break;
                        case RIGHT:
                            testX = partX - 1;
                            testY = partY;
                            break;
                    }
                }
                
            }

            body[i] = new WormBodyPart(partX, partY, direction);
        }
    }
    
    /**
     * This method is used for setting up the worm. When the worm generates, it will create a body part that is attached (facing towards) the body part in front of it.
     * This method will retrieve a possible direction for that body part that ensures it can face towards the part in front of it.
     * @param index The index of the body part for which to retrieve a direction.
     * @return
     */
    private Direction retrieveDirectionForBodyPart(int index){
        WormBodyPart frontPart = body[index-1];
        Direction frontDirection = frontPart.getDirection();
        Direction impossibleDirection;
        switch (frontDirection) {
            case UP:
                impossibleDirection = Direction.DOWN;
                break;
            case DOWN:
                impossibleDirection = Direction.UP;
                break;
            case LEFT:
                impossibleDirection = Direction.RIGHT;
                break;
            case RIGHT:
                impossibleDirection = Direction.LEFT;
                break;
            default:
                throw new IllegalArgumentException("Invalid direction: " + frontDirection);
        }
        Direction d = frontDirection; // Initialize d to frontDirection to enter the loop
        while(d != impossibleDirection){
            d = Grid.getRandomDirection();
        }

        return d;
    }

    //#region MOVEMENT
    

    
}
