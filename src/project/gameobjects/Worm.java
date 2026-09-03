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

    public Worm(Type type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
        headDirection = Grid.getRandomDirection();
        
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
                direction = retrieveDirectionForBodyPart(i); // Get direction for the current body part
                //make it so that the body part is facing the previous body part
                switch (direction) {
                    case UP:
                        partY += 1;
                        break;
                    case DOWN:
                        partY -= 1;
                        break;
                    case LEFT:
                        partX += 1;
                        break;
                    case RIGHT:
                        partX -= 1;
                        break;
                }
            }

            body[i] = new WormBodyPart(partX, partY, direction);
        }
    }

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

    
}
