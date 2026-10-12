package project.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Inventory extends JPanel {

    public int width = 900;
    public int height = 500;
    public int squareLength = 100;
    public static void displayInventory()
    {
    }
    public Inventory()
    {
    }
         @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //use graphics 2d ( so we can translate things during screen scrolling)

        Graphics2D g2d = (Graphics2D) g;
        // inventory background
        g2d.setColor(new Color(198,198,198));
        g2d.fillRect(0, 0, width, height);

        // inventory border
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(10));
        g2d.drawRect(0, 0, width, height);

        // inventory
        g2d.setStroke(new BasicStroke(5));
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 2; j++)
            {
            g2d.drawRect(50+((squareLength+20)*i), 50+((squareLength+20)*j), squareLength, squareLength);
            }
        }
    }

}