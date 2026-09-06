package project.UI;

public class Hitbox {
    int x;
    int y;
    int pixelX;
    int pixelY;
    int width;
    int pixelWidth;
    int height;
    int pixelHeight;

    public Hitbox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        pixelX = x*40;
        pixelY = y*40;
        pixelWidth = width*40;
        pixelHeight = height*40;
    }

    public boolean isColliding(Hitbox other) {
        return this.pixelX < other.pixelX + other.pixelWidth &&
               this.pixelX + this.pixelWidth > other.pixelX &&
               this.pixelY < other.pixelY + other.pixelHeight &&
               this.pixelY + this.pixelHeight > other.pixelY;
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

    public static boolean collidingWithSolidBlock(int pixelX, int pixelY){
        int lowerX = pixelX / 40;
        // int upperX = (int) Math.ceil(pixelX / 40.0);
        int lowerY = pixelY / 40;
        // int upperY = (int) Math.ceil(pixelY / 40.0);
        return Grid.grid[lowerX][lowerY] == 1; // Assuming 1 represents a solid block
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