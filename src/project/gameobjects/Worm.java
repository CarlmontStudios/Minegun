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
    private double attackRange = 200; //TODO: adjust
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
        length = (int) (Math.random() * 3) + 5; // Random length between 5 and 8
        setupWorm();

        Timer wormTimer = new Timer(32, e -> {

            pathfind_to_player();
            // int blocksPerTurn = 5;
            // boolean changingDirection = Math.random() < (1.0 / (8 * blocksPerTurn));

            // if (changingDirection) {
            //     System.out.println("changing direction of worm");
            //     double choice = (int) (Math.random() * 2);
            //     // 0 is turn left, 1 is turn right
            //     // enums are lowk annoying for direction, using a vector would be better
            //     if (choice == 0) {
            //         switch (body[0].getDirection()) {
            //             case UP:
            //                 body[0].setPendingDirection(Direction.LEFT);
            //                 break;
            //             case LEFT:
            //                 body[0].setPendingDirection(Direction.DOWN);
            //                 break;
            //             case DOWN:
            //                 body[0].setPendingDirection(Direction.RIGHT);
            //                 break;
            //             case RIGHT:
            //                 body[0].setPendingDirection(Direction.UP);
            //                 break;
            //         }
            //     }
            //     if (choice == 1) {
            //         switch (body[0].getDirection()) {
            //             case UP:
            //                 body[0].setPendingDirection(Direction.RIGHT);
            //                 break;
            //             case LEFT:
            //                 body[0].setPendingDirection(Direction.UP);
            //                 break;
            //             case DOWN:
            //                 body[0].setPendingDirection(Direction.LEFT);
            //                 break;
            //             case RIGHT:
            //                 body[0].setPendingDirection(Direction.DOWN);
            //                 break;
            //             //default:
            //             //    throw new IllegalArgumentException("Unknown direction: " + body[0].getDirection());
            //         }    
            //     }
            // }
            

        });
        wormTimer.start();
    }

    /** initializes the worm's body on the field */
    public void setupWorm(){
        System.out.println("entering setupWorm()");
        
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
                direction = retrieveDirectionForBodyPart(i, false); // Get direction for the current body part
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

                boolean failedGeneration = false;
                while (!Grid.wormCanSpawn(testX, testY)) {
                    System.out.println("stuck in while(!Grid.wormCanSpawn(...");
                    direction = retrieveDirectionForBodyPart(i, failedGeneration); // Get a new direction if the space is occupied
                    // Update testX and testY based on the new direction
                    System.out.println("Direction being tried: " + direction);
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
                    failedGeneration = true;
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
    private Direction retrieveDirectionForBodyPart(int index, boolean failing){
        WormBodyPart frontPart = body[index-1];
        Direction frontDirection = frontPart.getDirection();
        Direction impossibleDirection = getImpossibleDirection(frontDirection);
        
        Direction d = frontDirection; // Initialize d to frontDirection to enter the loop
        while(d == impossibleDirection){
            d = Grid.getRandomDirection();
        }

        return d;
    }

    /**returns the impossible direction for a given direction. Note the impossible direction is the direction that is opposite to the direction of the body part.
     * The impossible direction is important to know because for each worm body part the impossible direction is the direction it is not allowed to move in.
     */
    private Direction getImpossibleDirection(Direction frontDirection){
        
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

        Direction impossibleDirectionTwo = d;

        while(d == impossibleDirection || (d == impossibleDirectionTwo && failing)){
            d = Grid.getRandomDirection();
            System.out.println("supposedly random direction:" + d);
        }

        return d;
    }

    //#region GENERATION


    

    //#region MOVEMENT
    // public void moveForward() {

    //     for (int i = body.length - 1; i > 0; i++) {
    //         body[i].setX(body[i-1].getX());
    //         body[i].setY(body[i-1].getY()); // because a part should always be moving towards the part directly in front of it
            
    //         switch(body[i-1].getDirection()) {
    //             case UP:
    //                 body[i].setDirection(Direction.UP);
    //             case DOWN:
    //                 body[i].setDirection(Direction.DOWN);
    //             case LEFT:
    //                 body[i].setDirection(Direction.LEFT);
    //             case RIGHT:
    //                 body[i].setDirection(Direction.RIGHT);
    //             default:
    //                 System.out.println("TODO: Put whatever is supposed to be here.");  
    //         }
    //     }

    //     switch(body[0].getDirection()) {
    //             case UP:
    //                 body[0].setY(body[0].getY()-1);
    //             case DOWN:
    //                 body[0].setY(body[0].getY()+1);
    //             case LEFT:
    //                 body[0].setX(body[0].getX()-1);
    //             case RIGHT:
    //                 body[0].setX(body[0].getX()+1);
    //             default:
    //                 System.out.println("TODO: Put whatever is supposed to be here.");  
    //         }
    // }

    private Direction getDirectionTowardsTarget(){
        double angle = Math.atan2(Player.pixelY - body[0].getY() * Grid.BLOCK_SIZE, Player.pixelX - body[0].getX() * Grid.BLOCK_SIZE);
        return Grid.degreesToDirection_swing_coords(Math.toDegrees(angle));
    }

    private double getAngleTowardsTarget(){
        double angle = Math.atan2(Player.pixelY - body[0].getY() * Grid.BLOCK_SIZE, Player.pixelX - body[0].getX() * Grid.BLOCK_SIZE);
        return Math.toDegrees(angle);
    }

    private void pathfind_to_player(){

        if (Grid.isNextBlockSolid(body[0].getX(), body[0].getY(), headDirection)){
            headDirection = getDirectionTowardsTarget();
            body[0].setPendingDirection(headDirection);
            moveForward();
    
        } else if (getDistanceToPlayer() <= attackRange){
            System.out.println("attacking, distance = " + getDistanceToPlayer());
            
        } else {
            int count_clockwise = calculateShortestPath(1)[0];
            int count_counterclockwise = calculateShortestPath(-1)[0];
            System.out.println("clockwise: " + count_clockwise + "counterclockwise" + count_counterclockwise);
            if (count_clockwise < count_counterclockwise){
                int[] result = calculateShortestPath(1);
                Direction firstDirection = Grid.directions[result[1]];
                headDirection = firstDirection;
                body[0].setPendingDirection(firstDirection);

            } else{
                int[] result = calculateShortestPath(-1);
                Direction firstDirection = Grid.directions[result[1]];
                headDirection = firstDirection;
                body[0].setPendingDirection(firstDirection);
            }

            moveForward();
            
        
        }

        
    }

    private void moveForward(){
        for (int i = body.length - 1; i >=0; i--) {
                //System.out.println("just entered worm body part for loop");
                if (i != 0) {
                    body[i].moveForward(i, body[i-1].getDirection(), body[i-1].getPendingDirection());
                } else {
                    body[i].moveForward(i, null, null);
                }
            }
    }

    /**
     * calculates the shortest path to move around edge blocks to reach an attacking distance towards the player.
     * 
     * @param direction 1 indicates clockwise, -1 indicates counterclockwise
     * @return an array where the first element is the number of blocks needed to move to reach a position where the worm can attack and the second element is the direction the worm should first move to take that path
     *  for the second element, 0 indicates UP, 1 indicates DOWN, 2 indicates LEFT, and 3 indicates RIGHT
     */
    private int[] calculateShortestPath(int direction){
        int checkX = body[0].getX();
        int checkY = body[0].getY();
        
        int count = 0;
        Direction firstDirection = body[0].getDirection(); //represents the direction the worm will first move in when it goes in this path. initialized to the head's direction

        while (getDistanceToPlayer(checkX, checkY) > attackRange){
            // double checkDistance = getDistanceToPlayer();
            double checkAngle = Math.toDegrees(Math.atan2(Player.pixelY - checkY * Grid.BLOCK_SIZE, Player.pixelX - checkX * Grid.BLOCK_SIZE));
            double angleIncrement = 0.0;
            System.out.println("count: " + count + " direction: " + Grid.degreesToDirection_swing_coords(checkAngle) + " angle: " + checkAngle);
            System.out.println("X: " + checkX + "Y: " + checkY);
           
        
            // rotate the "checking angle" around each block until it finds a direction the block can move in. This is to find the path around an open space and calculate the amount of blocks needed to travel to get there
            while (angleIncrement < 180.0 ) { //&& Grid.degreesToDirection_swing_coords(checkAngle + angleIncrement) != getImpossibleDirection(body[0].getDirection())
                //check if it can move in the direction of the check angle
                if (Grid.isNextBlockSolid(checkX, checkY, Grid.degreesToDirection_swing_coords(checkAngle))) {
                    
                    if (count == 0) firstDirection = Grid.degreesToDirection_swing_coords(checkAngle);
                    count++;
                    //update the new checking coords
                    switch (Grid.degreesToDirection_swing_coords(checkAngle)) {
                        case UP:
                            checkY--;
                            break;
                        case DOWN:
                            checkY++;
                            break;
                        case LEFT:
                            checkX--;
                            break;
                        case RIGHT:
                            checkX++;
                            break;
                    }
                    break;
                }
                else if (count == 0 && Grid.degreesToDirection_swing_coords(checkAngle) == getImpossibleDirection(body[0].getDirection())){
                    count = Integer.MAX_VALUE; //if the worm has to turn in its impossible direction to get closer to the player then don't allow it
                    firstDirection = getImpossibleDirection(body[0].getDirection());
                    return new int[]{count,firstDirection.ordinal()};
                } else{
                    System.out.println("incrementing");
                    // If the worm can't move in the current direction, try the next direction
                    checkAngle += direction * 90.0;
                    angleIncrement += 90.0; // Increment the angle by 90 degrees in the specified direction
                    
                }
                
                
            }
        }

        return new int[]{count, firstDirection.ordinal()};
        

    }

    private double getDistanceToPlayer(){
        return Math.sqrt(Math.pow(Player.pixelX - body[0].getX() * Grid.BLOCK_SIZE, 2) + Math.pow(Player.pixelY - body[0].getY() * Grid.BLOCK_SIZE, 2));
    }

    private double getDistanceToPlayer(int x, int y){
        return Math.sqrt(Math.pow(Player.pixelX - x * Grid.BLOCK_SIZE, 2) + Math.pow(Player.pixelY - y * Grid.BLOCK_SIZE, 2));
    }

    

    
}
