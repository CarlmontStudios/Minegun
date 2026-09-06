package project.UI;

public class Hitbox {
    int x;
    int y;
    int pixelX;
    int pixelY;
    int width;
    int height;

    public Hitbox(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        pixelX = x*40;
        pixelY = y*40;
    }
}