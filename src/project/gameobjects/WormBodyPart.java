package project.gameobjects;
import project.UI.Grid;
import project.UI.Grid.Direction;
import project.UI.Sprite;
import project.utilities.Vector;

public class WormBodyPart {

    private int x;
    private int y;
    private Direction direction;
    Sprite sprite;

    //TODO: implement sprite representation for the worm body part
    public WormBodyPart(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        Grid.worm_grid[x][y] = 1; // Mark the grid position as occupied by the worm

        try {
            sprite = new Sprite(Sprite.WORM_TEST, x, y, 1, 1); // Assuming the sprite is 1x1 grid square
            Grid.sprites.add(sprite); // Add the sprite to the grid's sprite list
            sprite.setX(x); // Set the sprite's position to match the worm body part
            sprite.setY(y);
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

    public void setDirection(Direction direction) {
        this.direction = direction;
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
}