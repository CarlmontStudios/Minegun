package project.UI;

import java.util.ArrayList;
import project.utilities.Vector;

public class Hitbox {
    int x;
    int y;
    int pixelX;
    int pixelY;
    int width;
    int pixelWidth;
    int height;
    int pixelHeight;
    boolean isActive; //determines whether this hitbox can be involved in collisions

    /*
     * Creates a new Hitbox with the specified properties (in grid squares).
     */
    public Hitbox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        pixelX = x*40;
        pixelY = y*40;
        pixelWidth = width*40;
        pixelHeight = height*40;
        this.isActive = true;
    }
    /*
     * Creates a new Hitbox with the specified properties (in pixels)
     * 
     * 
     */
    public Hitbox(int x, int y, int width, int height, boolean isActive) {
        this.pixelX = x;
        this.pixelY = y;
        this.pixelWidth = width;
        this.pixelHeight = height;
        this.x = x/40;
        this.y = y/40;
        this.width = width/40;
        this.height = height/40;
        this.isActive = isActive;
    }

    public boolean isColliding(Hitbox other) {

        return other.isActive() && 
               this.pixelX < other.pixelX + other.pixelWidth &&
               this.pixelX + this.pixelWidth > other.pixelX &&
               this.pixelY < other.pixelY + other.pixelHeight &&
               this.pixelY + this.pixelHeight > other.pixelY ;
    }

    public boolean collidingWithSolidBlock(){
        int lowerX = this.pixelX / 40;
        int upperX = (int) Math.ceil((this.pixelX + this.pixelWidth) / 40.0);
        int lowerY = this.pixelY / 40;
        int upperY = (int) Math.ceil((this.pixelY + this.pixelHeight) / 40.0);
        for (int i = lowerX; i < upperX; i++) {
            for (int j = lowerY; j < upperY; j++) {
                if (Grid.grid[i][j] == 1) { // Assuming 1 represents a solid block
                    return true;
                }
            }
        }
        return false;
    }

    public static Vector getCollidedBlock(Hitbox hitbox){
        int lowerX = hitbox.pixelX;
        int upperX = hitbox.pixelX + hitbox.pixelWidth;
        int lowerY = hitbox.pixelY;
        int upperY = hitbox.pixelY + hitbox.pixelHeight;
        //System.out.println("lowerX: " + lowerX + ", upperX: " + upperX + ", lowerY: " + lowerY + ", upperY: " + upperY);
        for (int i = lowerX; i < upperX; i++) {
            for (int j = lowerY; j < upperY; j++) {
                int x = i/40;
                int y = j/40;
                if (Grid.grid[x][y] == 1) { 
                    //System.out.println("Collided with block at (" + x + ", " + y + ")");
                    return new Vector(x, y);
                }
            }
        }
        //System.out.println("No block collision detected for hitbox at (" + hitbox.pixelX + ", " + hitbox.pixelY + ")");
        return null;
    }

    /**
     * returns a list of all the blocks a hitbox is colliding with (in grid coordinates)
     * @param hitbox
     * @return
     */
    public static ArrayList<Vector> getCollidedBlocks(Hitbox hitbox){
        int lowerX = hitbox.pixelX;
        int upperX = hitbox.pixelX + hitbox.pixelWidth;
        int lowerY = hitbox.pixelY;
        int upperY = hitbox.pixelY + hitbox.pixelHeight;
        ArrayList<Vector> collidedBlocks = new ArrayList<>();
        //System.out.println("lowerX: " + lowerX + ", upperX: " + upperX + ", lowerY: " + lowerY + ", upperY: " + upperY);
        for (int i = lowerX; i < upperX; i++) {
            for (int j = lowerY; j < upperY; j++) {
                int x = i/40;
                int y = j/40;
                if (Grid.grid[x][y] == 1) { 
                    if (collidedBlocks != null && !collidedBlocks.contains(new Vector(x, y))) {
                        collidedBlocks.add(new Vector(x, y));
                    }
                }
            }
        }
        //System.out.println("No block collision detected for hitbox at (" + hitbox.pixelX + ", " + hitbox.pixelY + ")");
        return collidedBlocks;
    }

    /**
     * returns true if the given pixel coordinates are colliding with a solid block
     * @param pixelX
     * @param pixelY
     * @return
     */

    public static boolean collidingWithSolidBlock(int pixelX, int pixelY){
        int lowerX = pixelX / 40;
        // int upperX = (int) Math.ceil(pixelX / 40.0);
        int lowerY = pixelY / 40;
        // int upperY = (int) Math.ceil(pixelY / 40.0);
        return Grid.grid[lowerX][lowerY] == 1; // Assuming 1 represents a solid block
    }


    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setX(int x) {
        this.x = x;
        this.pixelX = x * 40;
    }
    public void setY(int y) {
        this.y = y;
        this.pixelY = y * 40;
    }
    public void setPixelX(int pixelX) {
        this.pixelX = pixelX;
        this.x = pixelX / 40;
    }
    public void setPixelY(int pixelY) {
        this.pixelY = pixelY;
        this.y = pixelY / 40;
    }
    public void changePixelX(int deltaPixelX) {
        this.pixelX += deltaPixelX;
        this.x = pixelX / 40;
    }
    public void changePixelY(int deltaPixelY) {
        this.pixelY += deltaPixelY;
        this.y = pixelY / 40;
    }
    public void setPixelWidth(int pixelWidth) {
        this.pixelWidth = pixelWidth;
        this.width = pixelWidth / 40;
    }
    public void setPixelHeight(int pixelHeight) {
        this.pixelHeight = pixelHeight;
        this.height = pixelHeight / 40;
    }
    public void setWidth(int width) {
        this.width = width;
        this.pixelWidth = width * 40;
    }
    public void setHeight(int height) {
        this.height = height;
        this.pixelHeight = height * 40;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getPixelX() {
        return pixelX;
    }
    public int getPixelY() {
        return pixelY;
    }
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}