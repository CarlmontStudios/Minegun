package project.UI;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.Graphics;

public class Sprite {
    BufferedImage image;
    String imagePath;
    int x;
    int y;
    int pixelX;
    int pixelY;
    int width;
    int height;

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
}