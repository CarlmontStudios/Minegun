package project.gameobjects;

import javax.swing.Timer;
import project.UI.Grid;
import project.UI.Grid.Direction;
public class Worm {
    
    public static enum Type {
        //placeholder names, can change later
        L1,
        L2,
        L3
    }

    public WormBodyPart[] body;
    private int length;
    private Grid.Direction headDirection;
    private Type type;
    private int x; //represents x and y coordinates of the head of the worm
    private int y;
    private int step = 5; //probably GRID.BLOCK_SIZE % step must == 0.

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

        Timer wormTimer = new Timer(16, e -> {
            int blocksPerTurn = 5;
            boolean changingDirection = Math.random() < (1.0 / (8 * blocksPerTurn));

            if (changingDirection) {
                System.out.println("changing direction of worm");
                double choice = (int) (Math.random() * 2);
                // 0 is turn left, 1 is turn right
                // enums are lowk annoying for direction, using a vector would be better
                if (choice == 0) {
                    switch (body[0].getDirection()) {
                        case UP:
                            body[0].setPendingDirection(Direction.LEFT);
                            break;
                        case LEFT:
                            body[0].setPendingDirection(Direction.DOWN);
                            break;
                        case DOWN:
                            body[0].setPendingDirection(Direction.RIGHT);
                            break;
                        case RIGHT:
                            body[0].setPendingDirection(Direction.UP);
                            break;
                    }
                }
                if (choice == 1) {
                    switch (body[0].getDirection()) {
                        case UP:
                            body[0].setPendingDirection(Direction.RIGHT);
                            break;
                        case LEFT:
                            body[0].setPendingDirection(Direction.UP);
                            break;
                        case DOWN:
                            body[0].setPendingDirection(Direction.LEFT);
                            break;
                        case RIGHT:
                            body[0].setPendingDirection(Direction.DOWN);
                            break;
                        //default:
                        //    throw new IllegalArgumentException("Unknown direction: " + body[0].getDirection());
                    }    
                }
            }
            for (int i = body.length - 1; i >=0; i--) {
                //System.out.println("just entered worm body part for loop");
                if (i != 0) {
                    body[i].moveForward(i, body[i-1].getDirection(), body[i-1].getPendingDirection());
                } else {
                    body[i].moveForward(i, null, null);
                }
            }

        });
        wormTimer.start();
    }

    /** initializes the worm's body on the field */
    public void setupWorm(){
        System.out.println("entering setupWorm()");
        length = 20;
        body = new WormBodyPart[length];
        
        for (int i = 0; i < length; i++) {
            Direction direction;
            
            int partX = 0;
            int partY = 0;
            int testX = 0;
            int testY = 0;
            if (i == 0){
                direction = headDirection; // Assuming the body parts follow the head's direction
                testX = x;
                testY = y; // only initialize to the values for head if it is actually the head
            }
            else {
                partX = body[i-1].getX();
                partY= body[i-1].getY();
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
                    System.out.println("stuck in while(!Grid.wormCanSpawn(...");
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

            body[i] = new WormBodyPart(testX, testY, direction);
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
        while(d == impossibleDirection){
            d = Grid.getRandomDirection();
        }

        return d;
    }

    //#region GENERATION


    

    //#region MOVEMENT
    public void moveForward() {

        for (int i = body.length - 1; i > 0; i++) {
            body[i].setX(body[i-1].getX());
            body[i].setY(body[i-1].getY()); // because a part should always be moving towards the part directly in front of it
            
            switch(body[i-1].getDirection()) {
                case UP:
                    body[i].setDirection(Direction.UP);
                case DOWN:
                    body[i].setDirection(Direction.DOWN);
                case LEFT:
                    body[i].setDirection(Direction.LEFT);
                case RIGHT:
                    body[i].setDirection(Direction.RIGHT);
                default:
                    System.out.println("TODO: Put whatever is supposed to be here.");  
            }
        }

        switch(body[0].getDirection()) {
                case UP:
                    body[0].setY(body[0].getY()-1);
                case DOWN:
                    body[0].setY(body[0].getY()+1);
                case LEFT:
                    body[0].setX(body[0].getX()-1);
                case RIGHT:
                    body[0].setX(body[0].getX()+1);
                default:
                    System.out.println("TODO: Put whatever is supposed to be here.");  
            }
    }

    
}
