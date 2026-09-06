package project.gameobjects;
import project.UI.Grid;
import project.UI.Grid.Direction;
import project.utilities.Vector;

public class WormBodyPart {

    private int x;
    private int y;
    private Direction direction;

    //TODO: implement sprite representation for the worm body part
    public WormBodyPart(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        Grid.worm_grid[x][y] = 1; // Mark the grid position as occupied by the worm
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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