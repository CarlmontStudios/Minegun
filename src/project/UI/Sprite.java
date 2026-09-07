package project.UI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {
    BufferedImage image;
    String imagePath;
    int x;
    int y;
    int pixelX;
    int pixelY;
    int width;
    int height;

    public static final String CRACKED_0 = "src/images/cracked_0.png";
    public static final String CRACKED_1 = "src/images/cracked_1.png";
    public static final String PICKAXE_HOVER = "src/images/pickaxe_hover.png";

    /**
     * creates a new sprite with grid square units
     * @param imagePath
     * @param width
     * @param height
     * @throws IOException
     */
    public Sprite(String imagePath, int width, int height) throws IOException{
        this.imagePath = imagePath;
        this.width = width;
        this.height = height;
        image = ImageIO.read(new File(imagePath));
        x = 0;
        y = 0;
        pixelX = 0;
        pixelY = 0;
    }

    /**
     * creates a new sprite with grid square units and a specific position
     * @param imagePath
     * @param x
     * @param y
     * @param width
     * @param height
     * @throws IOException
     */
    public Sprite(String imagePath, int x, int y, int width, int height) throws IOException{
        this.imagePath = imagePath;
        image = ImageIO.read(new File(imagePath));
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        pixelX = 0;
        pixelY = 0;
    }

    /**
     * creates a new sprite with pixel units and a specific position
     * @param imagePath
     * @param pixelX
     * @param pixelY
     * @param pixelWidth
     * @param pixelHeight
     * @param pixelUnits
     * @throws IOException
     */
    public Sprite(String imagePath, int pixelX, int pixelY, int pixelWidth, int pixelHeight, boolean pixelUnits) throws IOException{
        this.imagePath = imagePath;
        image = ImageIO.read(new File(imagePath));
        this.pixelX = pixelX;
        this.pixelY = pixelY;
        this.x = pixelX / Grid.BLOCK_SIZE;
        this.y = pixelY / Grid.BLOCK_SIZE;
        this.width = pixelWidth / Grid.BLOCK_SIZE;
        this.height = pixelHeight / Grid.BLOCK_SIZE;
    }



    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
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
    public int getPixelX() {
        return pixelX;
    }
    public void setPixelX(int pixelX) {
        this.pixelX = pixelX;
    }
    public int getPixelY() {
        return pixelY;
    }
    public void setPixelY(int pixelY) {
        this.pixelY = pixelY;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getPixelWidth() {
        return width * Grid.BLOCK_SIZE;
    }
    public int getPixelHeight() {
        return height * Grid.BLOCK_SIZE;
    }
}