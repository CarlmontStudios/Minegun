package project.gameobjects;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.Timer;
import project.UI.Grid;
import project.UI.Grid.Direction;
import project.utilities.Vector;
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

    private Direction previousDirectionWhileReversing;

    int countForDebugging = 0;

    boolean reversing = false;
    int reversingCounter = 0;

    public Worm(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.pixelCoordsX = x * Grid.BLOCK_SIZE;
        this.pixelCoordsY = y * Grid.BLOCK_SIZE;

        headDirection = Grid.getRandomDirection();
        length = (int) (Math.random() * 3) + 5; // Random length between 5 and 8
        setupWorm();


        Timer wormTimer = new Timer(60, e -> {

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
            body[i].setBodyLength(length);
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


        return impossibleDirection;
    }

    //#region GENERATION


    //used for checking if it is not going back to the spot it just reversed from
    private boolean notHeadedInPreviousSpot(Direction d){
        if (previousDirectionWhileReversing == null){
            return true;
        } else {
            if (willEndUpStuck(d)){
                return false;
            } else {
                return true;
            }
        }
    }

    private Direction getDirectionTowardsTarget(){
        double angle = Math.toDegrees(Math.atan2(Player.pixelY - body[0].getY() * Grid.BLOCK_SIZE, Player.pixelX - body[0].getX() * Grid.BLOCK_SIZE));
        Direction d = Grid.degreesToDirection_swing_coords(angle);
        Vector checkPosition = Grid.getNextBlock(body[0].getX(), body[0].getY(), d);
        if ((d != getImpossibleDirection(body[0].getDirection()) 
        && Grid.worm_grid[checkPosition.getX()][checkPosition.getY()] == 0) && notHeadedInPreviousSpot(d)) {
            //System.out.println("regular mode");
            
            return d;
        } else {

            //System.out.println("backup direction mode");
            //double directionAngle = Grid.directionToDegrees(d);
            ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
            //1 indicates clockwise checking, -1 indicates counterclockwise checking
            // int checkingDirection;
            Direction bestD = body[0].getDirection();
            double bestDistance = Double.MAX_VALUE;
            directions.remove(getImpossibleDirection(body[0].getDirection())); //make sure it doesn't check the impossible direction
            directions.remove(d); //get rid of the original direction which we know won't work
            Direction originalD = bestD;
            boolean skippedCheck = false;
            while (!directions.isEmpty()){
                
                Direction checkDirection = directions.get(0);
                Vector check = Grid.getNextBlock(body[0].getX(), body[0].getY(), checkDirection);
                directions.remove(checkDirection);
                if ((Grid.worm_grid[check.getX()][check.getY()] == 1 || Grid.grid[check.getX()][check.getY()] == 0) || !notHeadedInPreviousSpot(checkDirection)){
                    //System.out.println("skipping direction: " + checkDirection + "worm occupied? " + (Grid.worm_grid[check.getX()][check.getY()] == 1) + "open air? " + (Grid.grid[check.getX()][check.getY()] == 0));
                    continue; //dont count the direction if the worm would move into itself or another worm
                }
                double distance = getDistanceToPlayer(check.getX(), check.getY());
                if (distance < bestDistance){
                    // if ((body[0].getPixelY() % Grid.BLOCK_SIZE == 0 && body[0].getPixelX() % Grid.BLOCK_SIZE ==0)){
                    //     System.out.println("original d: " + d + "working d: " + checkDirection);
                    // }
                    
                    bestD = checkDirection;
                    bestDistance = distance;
                    

                }

            }
            if (bestD == originalD){
                skippedCheck = true;
            }
            // if ((body[0].getPixelY() % Grid.BLOCK_SIZE == 0 && body[0].getPixelX() % Grid.BLOCK_SIZE == 0)){
            //     System.out.println("skipped check? " + skippedCheck + " headed towards " + bestD );
            //     //System.out.println("head coordinates: x: " + body[0].getX() + " y: " + body[0].getY() + "worm occupied: " + Grid.worm_grid[body[0].getX()][body[0].getY()]);
            // }
            
            return bestD;
           

        }
        
        
        
    }

    private boolean isPlayerInRange(int x, int y){

        if (getDistanceToPlayer(x, y) > attackRange){
            return false;
        } 
        //sets the starting check position in pixel coords
        int checkX = x * Grid.BLOCK_SIZE + 20;
        int checkY = y * Grid.BLOCK_SIZE + 20;
        // double theta = get
        return true;

    }

    private Direction getDirectionTowardsTarget(int x, int y){
        double angle = Math.toDegrees(Math.atan2(Player.pixelY - y * Grid.BLOCK_SIZE, Player.pixelX - x * Grid.BLOCK_SIZE));
        return Grid.degreesToDirection_swing_coords(angle);
    }

    //#region PATHFINDING
    boolean attacking = false;
    boolean pathFinding = false;
    boolean circling = false;
    private void pathfind_to_player(){
        
        //IF THE WORM IS WITHIN DISTANCE TO ATTACK
        if (!Grid.isNextBlockSolid(body[0].getX(), body[0].getY(), getDirectionTowardsTarget()) && getDistanceToPlayer() <= attackRange){
            //if (!attacking)
                //System.out.println("attacking, distance = " + getDistanceToPlayer());
            headDirection = getDirectionTowardsTarget();
            body[0].setPendingDirection(headDirection);
            attacking = true;
            pathFinding = false;
            circling = false;
        }
        else if (isStuck()){
            // make the worm take damage for being stuck
            System.out.println("stuck");
        } else if (willEndUpStuck(getDirectionTowardsTarget()) && getAvailableDirections() <=1){
            System.out.println("reversing");
            //if the worm can move directly backwards
            if (Grid.isPreviousBlockSafe(body[body.length - 1].getX(), body[body.length - 1].getY(), body[body.length - 1].getDirection())){
                body[body.length-1].setPendingDirection(body[body.length - 1].getDirection());
                
                reverse();
            } else {
                //if the worm has to change the direction of its very back to reverse:
                ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
                directions.remove(body[body.length - 1].getDirection());
                for (Direction d : directions){
                    
                    if (Grid.isPreviousBlockSafe(body[body.length - 1].getX(), body[body.length - 1].getY(), d)){
                        body[length - 1].setPendingDirection(d);
                        reverse();
                        
                        break;
                    }
                }
            }
        }
         else if (Grid.isNextBlockSolid(body[0].getX(), body[0].getY(), getDirectionTowardsTarget())){ //Grid.isNextBlockSolid(body[0].getX(), body[0].getY(), headDirection)
            //IF THE WORM IS JUST TRYING TO GET CLOSER TO THE PLAYER
            if (!pathFinding)
                System.out.println("moving towards player");
            

            attacking = false;
            circling = false;
            headDirection = getDirectionTowardsTarget();
            body[0].setPendingDirection(headDirection);
            if (headDirection != previousDirectionWhileReversing && reversingCounter >= 2){
                previousDirectionWhileReversing = null;
            }
            
            //if (!pathFinding)
                //System.out.println("pending direction: " + body[0].getPendingDirection() + "direction: " + body[0].getDirection());
            if ((body[0].getPixelY() % Grid.BLOCK_SIZE == 0 && body[0].getPixelX() % Grid.BLOCK_SIZE == 0)){
                System.out.println("head direction(pending direction " + headDirection + " current direction: " + body[0].getDirection() + " x: " + countForDebugging);
                countForDebugging++;
            }
            moveForward();
            pathFinding = true;
            
        }  else if (!Grid.isNextBlockSolid(body[0].getX(), body[0].getY(), getDirectionTowardsTarget()) && getDistanceToPlayer() > attackRange){
            
            //IF THE WORM NEEDS TO CIRCLE AROUND A CAVE TO GET TO THE PLAYER
            if (!circling){
                circling = true;
            }
            reversing = true;
            int count_clockwise = calculateShortestPath(1)[0];
            int count_counterclockwise = calculateShortestPath(-1)[0];
            //System.out.println("clockwise: " + count_clockwise + "counterclockwise" + count_counterclockwise);
            if (count_clockwise < count_counterclockwise){
                int[] result = calculateShortestPath(1);
                Direction firstDirection = Grid.directions[result[1]];
                headDirection = firstDirection;
                System.out.println("first direction: " + firstDirection);
                body[0].setPendingDirection(firstDirection);

            } else{
                int[] result = calculateShortestPath(-1);
                Direction firstDirection = Grid.directions[result[1]];
                headDirection = firstDirection;
                System.out.println("first direction: " + firstDirection);
                body[0].setPendingDirection(firstDirection);
            }
            previousDirectionWhileReversing = null;
            moveForward();
            
        
        }

        
    }

    private boolean isStuck(){
        boolean stuck = true;
        Direction[] directions = Direction.values();
        //check if it can move anywhere from its head
        for (int i = 0; i < directions.length; i++){
            Direction d = directions[i];
            if (Grid.isNextBlockSafe(body[0].getX(), body[0].getY(), d)){
                return false;
            }
        }
        //check if it can move anywhere from the back of its tail
        for (int i = 0; i < directions.length; i++){
            Direction d = directions[i];
            if (Grid.isNextBlockSafe(body[body.length - 1].getX(), body[body.length - 1].getY(), d)){
                return false;
            }
        }
        return stuck;
    }

    private int getAvailableDirections(){
        int count = 0;
        Direction[] directions = Direction.values();

        //check if it can move anywhere from its head
        for (int i = 0; i < directions.length; i++){
            Direction d = directions[i];
            if (Grid.isNextBlockSafe(body[0].getX(), body[0].getY(), d)){
                count++;
            }
        }
        return count;
    }


    private boolean cantMoveForward(){
        boolean stuck = true;
        Direction[] directions = Direction.values();

        //check if it can move anywhere from its head
        for (int i = 0; i < directions.length; i++){
            Direction d = directions[i];
            if (Grid.isNextBlockSafe(body[0].getX(), body[0].getY(), d)){
                return false;
            }
        }
        return stuck;
    }
    /** checks if the worm wouldn't be able to move forward at the given coordinates */
    private boolean cantMoveForward(int x, int y, ArrayList<Vector> wormBody){
        boolean stuck = true;
        Direction[] directions = Direction.values();

        //check if it can move anywhere from its head
        for (int i = 0; i < directions.length; i++){
            Direction d = directions[i];
            
            if (Grid.isNextBlockSafe(x, y, d) && !wormBody.contains(Grid.getNextBlock(x, y, d))){
                return false;
            }
        }
        return stuck;
    }

    private boolean willEndUpStuck(Direction firstDirection){
        ArrayList<Vector> positions = new ArrayList<>();
        //put the positions of the worm's body into the arraylist
        for (WormBodyPart p : body){
            positions.add(0, new Vector(p.getX(), p.getY()));
        }

        int checkX = body[0].getX();
        int checkY = body[0].getY();

        //check the next 10 spots it would take along its predicted path
        for (int i = 0; i < 5; i ++){
            // if (getDistanceToPlayer(checkX, checkY) < attackRange && Grid.isEdgeBlock(checkX, checkY)){
            //     return false; //if the worm will end up in a spot where it can attack it will not count as being stuck
            // }
            Direction d = getDirectionTowardsTarget(checkX, checkY);
            if (i == 0) d = firstDirection; 
            Vector nextPos = Grid.getNextBlock(checkX, checkY, d);
            //checks to see if this next block would end up making the worm stuck
            if (!Grid.isNextBlockSafe(checkX, checkY, d, positions)){
                //if its not safe to move in that direction then check the other directions
                ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
                directions.remove(d);
                // Direction bestD = d;
                double bestDistance = Double.MAX_VALUE;
                Vector bestPos = nextPos;
                //loop through the other directions to see if any will be valid
                while (!directions.isEmpty()){
                
                    d = directions.get(0);
                    nextPos = Grid.getNextBlock(checkX, checkY, d);
                    directions.remove(d);
                    //if it is able to move forward in this direction
                    if (!cantMoveForward(nextPos.getX(), nextPos.getY(), positions) && Grid.isCurrentBlockSafe(nextPos.getX(), nextPos.getY(), positions)){
                        double distance = getDistanceToPlayer(nextPos.getX(), nextPos.getY());
                        if (distance < bestDistance){
                        
                            // bestD = d;
                            bestDistance = distance;
                            bestPos = nextPos;

                        }
                    }
                    
                }
                if (bestDistance == Double.MAX_VALUE) 
                    return true; //this will happen if none of the directions allowed the worm to move
                nextPos = bestPos;
                
            }
            checkX = nextPos.getX();
            checkY = nextPos.getY();
            positions.remove(0);
            positions.add(nextPos);

             


        } 
        return false; //if it never ends up stuck return false
    }

    //#region MOVEMENT

    private void moveForward(){
        reversing = false;
        reversingCounter = 0;
        if (body[0].getPixelX() % Grid.BLOCK_SIZE == 0 && body[0].getPixelY() % Grid.BLOCK_SIZE == 0){
            System.out.println("reversing counter: " + reversingCounter);
            System.out.println("head direction: " + body[0].getDirection());
            System.out.println("is reversing direction null?" + previousDirectionWhileReversing == null);
        }
        for (WormBodyPart p : body) Grid.worm_grid[p.getX()][p.getY()] = 0;
        for (int i = body.length - 1; i >=0; i--) {
                //System.out.println("just entered worm body part for loop");
                if (i != 0) {
                    body[i].moveForward(i, body[i-1].getDirection(), body[i-1].getPendingDirection());
                } else {
                    body[i].moveForward(i, null, null);
                }
            }

        
        for (WormBodyPart p : body) Grid.worm_grid[p.getX()][p.getY()] = 1;
    }

    private void reverse(){
        for (WormBodyPart p : body) Grid.worm_grid[p.getX()][p.getY()] = 0;
        
        reversing = true;
        previousDirectionWhileReversing = body[0].getDirection();
        System.out.println("head direction: " + body[0].getDirection());
        for (int i = 0; i < body.length; i++) {
                //System.out.println("just entered worm body part for loop");
                if (i != body.length - 1) {
                    body[i].moveBackward(i, body[i+1].getDirection(), body[i+1].getPendingDirection());
                } else {
                    body[i].moveBackward(i, null, null);
                }
        }

        if (body[0].getPixelX() % Grid.BLOCK_SIZE == 0 && body[0].getPixelY() % Grid.BLOCK_SIZE == 0){
            reversingCounter++;
            System.out.println("reversing counter: " + reversingCounter);
            System.out.println("previous direction while reversing" + previousDirectionWhileReversing);
        }
        
        //System.out.println(previousDirectionWhileReversing);
        
        for (WormBodyPart p : body) Grid.worm_grid[p.getX()][p.getY()] = 1;
        
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
        Direction ignoreDirection; //the direction that would bring the checking position back to the last one
        double checkAngle = Math.toDegrees(Math.atan2(Player.pixelY - checkY * Grid.BLOCK_SIZE, Player.pixelX - checkX * Grid.BLOCK_SIZE));
        Direction checkDirection = Grid.degreesToDirection_swing_coords(checkAngle);
        ArrayList<Vector> steps = new ArrayList();
        while (getDistanceToPlayer(checkX, checkY) > attackRange){
            // double checkDistance = getDistanceToPlayer();
            checkAngle = Math.toDegrees(Math.atan2(Player.pixelY - checkY * Grid.BLOCK_SIZE, Player.pixelX - checkX * Grid.BLOCK_SIZE));
            double angleIncrement = 0.0;
            ignoreDirection = getImpossibleDirection(checkDirection);
            checkDirection = Grid.degreesToDirection_swing_coords(checkAngle);
            //System.out.println("count: " + count + " direction: " + checkDirection + " angle: " + checkAngle);
            //System.out.println("X: " + checkX + "Y: " + checkY);
            ArrayList<Direction> directions = new ArrayList<>(Arrays.asList(Direction.values()));
            directions.remove(ignoreDirection);

            // rotate the "checking angle" around each block until it finds a direction the block can move in. This is to find the path around an open space and calculate the amount of blocks needed to travel to get there
            while (!directions.isEmpty()) { //&& Grid.degreesToDirection_swing_coords(checkAngle + angleIncrement) != getImpossibleDirection(body[0].getDirection())
                //check if it can move in the direction of the check angle
                //System.out.println("next block safe: " + Grid.isNextBlockSafe(checkX, checkY, checkDirection));
                //System.out.println("not ignore direction " + (checkDirection != ignoreDirection));
                //System.out.println("already checked this position: " + steps.contains(Grid.getNextBlock(checkX,checkY, checkDirection)));
                if (Grid.isNextBlockSafe(checkX, checkY, checkDirection) &&  !steps.contains(Grid.getNextBlock(checkX,checkY, checkDirection))) { //checkDirection != ignoreDirection &&
                    
                    if (count == 0) {
                        firstDirection = checkDirection;
                    }
                    Grid.block_health_grid[checkX][checkY] = 1;
                    steps.add(new Vector(checkX, checkY));
                    count++;
                    //System.out.println("count: " + count + "check position -  x: " + checkX + " y: " + checkY + " d: " + checkDirection);
                    //update the new checking coords
                    switch (checkDirection) {
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
                // else if (count == 0 && checkDirection == getImpossibleDirection(body[0].getDirection())){
                //     // count = Integer.MAX_VALUE; //if the worm has to turn in its impossible direction to get closer to the player then don't allow it
                //     // firstDirection = getImpossibleDirection(body[0].getDirection());
                //     // return new int[]{count,firstDirection.ordinal()};
                //     directions.remove(checkDirection);
                    
                 else {
                    directions.remove(checkDirection);
                    //System.out.println("incrementing. angle = " + angleIncrement);
                    // If the worm can't move in the current direction, try the next direction
                    checkAngle += direction * 90.0;
                    //System.out.println(checkAngle);
                    //System.out.println(checkDirection);
                    //System.out.println("check X: " + checkX + "check Y: " + checkY);
                    checkDirection = Grid.degreesToDirection_swing_coords(checkAngle);
                    angleIncrement += 90.0; // Increment the angle by 90 degrees in the specified direction
                    
                }
                
                
            }
            //if the algorithm gets stuck exit out
            if (directions.isEmpty() && (!Grid.isNextBlockSafe(checkX, checkY, checkDirection) ||  steps.contains(Grid.getNextBlock(checkX,checkY, checkDirection)))){
                return new int[]{Integer.MAX_VALUE, body[0].getDirection().ordinal()};
            }
            //System.out.println(steps);
            //System.out.println("finished list of directions: x: " + checkX + " y: " + checkY);
        }

        return new int[]{count, firstDirection.ordinal()};
        

    }

    private double getDistanceToPlayer(){
        return Math.sqrt(Math.pow(Player.pixelX - body[0].getX() * Grid.BLOCK_SIZE, 2) + Math.pow(Player.pixelY - body[0].getY() * Grid.BLOCK_SIZE, 2));
    }

    private double getDistanceToPlayer(int x, int y){
        return Math.sqrt(Math.pow(Player.pixelX - x * Grid.BLOCK_SIZE, 2) + Math.pow(Player.pixelY - y * Grid.BLOCK_SIZE, 2));
    }

    private double getAngleToPlayer(int x, int y){
        return Math.atan2(Player.pixelY - y * Grid.BLOCK_SIZE, Player.pixelX - x * Grid.BLOCK_SIZE);
    }

    

    
}
