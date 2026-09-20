package project.gameobjects;
import project.UI.Grid;
import project.UI.Grid.Direction;
import project.UI.Sprite;
import project.utilities.Vector;

public class WormBodyPart {

    private int x;
    private int y;
    private int xOffset;
    private int yOffset;
    private Direction pendingDirection;
    private Direction direction;
    Sprite sprite;
    private int step = 5;

    //TODO: implement sprite representation for the worm body part
    public WormBodyPart(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.xOffset = 0;
        this.yOffset = 0;
        this.direction = direction;
        this.pendingDirection = direction;
        Grid.worm_grid[x][y] = 1; // Mark the grid position as occupied by the worm

        try {
            sprite = new Sprite(Sprite.WORM_TEST, x, y, 1, 1); // Assuming the sprite is 1x1 grid square
            Grid.sprites.add(sprite); // Add the sprite to the grid's sprite list
            // sprite.setX(x); // Set the sprite's position to match the worm body part
            // This doesn't work because x and y represent grid position.
            // sprite.setY(y);
            // the idea is that we want the vector from the viewport center (deltaX, deltaY)
            // to the position of the worm component, so we do worm component vector - viewport vector.
            // ?
            int displayX = (x * Grid.BLOCK_SIZE + xOffset); //- GameMap.getDeltaX();
            int displayY = (y * Grid.BLOCK_SIZE + yOffset); //- GameMap.getDeltaX();
            System.out.println("displayX: " + displayX);
            System.out.println("displayY: " + displayY);
            sprite.setPixelX(displayX);
            sprite.setPixelY(displayY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public Sprite getSprite() {
        return sprite;
    }
    // Getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        Grid.worm_grid[this.x][this.y] = 0; // Clear the old position
        this.x = x;
        sprite.setX(x); // Update the sprite's position
        Grid.worm_grid[x][this.y] = 1; // Mark the new position
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        Grid.worm_grid[this.x][this.y] = 0; // Clear the old position
        this.y = y;
        sprite.setY(y); // Update the sprite's position
        Grid.worm_grid[this.x][y] = 1; // Mark the new position
    }

    public Direction getDirection() {
        return direction;
    }

    public Direction getPendingDirection() {
        return pendingDirection;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setPendingDirection(Direction direction) {
        this.pendingDirection = direction;
    }


    public boolean isColliding(WormBodyPart other) {
        return this.x == other.x && this.y == other.y;
    }

    public boolean isColliding(Vector other) {
        return this.x == other.getX() && this.y == other.getY();
    }



    public Vector getCoordsInFront(){
        switch (direction) {
            case UP:
                return new Vector(x, y - 1);
            case DOWN:
                return new Vector(x, y + 1);
            case LEFT:
                return new Vector(x - 1, y);
            case RIGHT:
                return new Vector(x + 1, y);
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    public void updateSprite() {
        int displayX = (x * Grid.BLOCK_SIZE + xOffset); //- GameMap.getDeltaX();
        int displayY = (y * Grid.BLOCK_SIZE + yOffset); //- GameMap.getDeltaX();
        if (Math.random() < 0.01) {
            System.out.println("displayX: " + displayX);
            System.out.println("displayY: " + displayY);
        }
        sprite.setPixelX(displayX);
        sprite.setPixelY(displayY);
    }

    public void moveForward(int i, Direction directionNextSegment, Direction pendingDirectionNextSegment) {
        //if (Math.random() < 0.01) {
            //System.out.println("moving forward");
        //}
        switch (direction) {
            case UP:
                yOffset -= step;
                if (yOffset == -1 * Grid.BLOCK_SIZE) {
                    y -= 1;
                    yOffset = 0;
                    if (i != 0) {
                        direction = directionNextSegment;
                        pendingDirection = pendingDirectionNextSegment;
                    } else {
                        direction = pendingDirection;
                    }
                }
                break;
            case DOWN:
                yOffset += step;
                if (yOffset == 1 * Grid.BLOCK_SIZE) {
                    y += 1;
                    yOffset = 0;
                    if (i != 0) {
                        direction = directionNextSegment;
                        pendingDirection = pendingDirectionNextSegment;
                    } else {
                        direction = pendingDirection;
                    }
                }
                break;
            case LEFT:
                xOffset -= step;
                if (xOffset == -1 * Grid.BLOCK_SIZE) {
                    x -= 1;
                    xOffset = 0;
                    if (i != 0) {
                        direction = directionNextSegment;
                        pendingDirection = pendingDirectionNextSegment;
                    } else {
                        direction = pendingDirection;
                    }
                }
                break;
            case RIGHT:
                xOffset += step;
                if (xOffset == 1 * Grid.BLOCK_SIZE) {
                    x += 1;
                    xOffset = 0;
                    if (i != 0) {
                        direction = directionNextSegment;
                        pendingDirection = pendingDirectionNextSegment;
                    } else {
                        direction = pendingDirection;
                    }
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }

        updateSprite();
    }
}