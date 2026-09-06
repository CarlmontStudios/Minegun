package project.UI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import project.gameobjects.Item;

public class Hotbar extends JPanel {

    public static final int HOTBAR_SIZE = 6; // Number of slots in the hotbar
    public static final int MAX_CONSUMABLES_PER_SLOT = 5; // Maximum number of consumables per slot
    public static final int HOTBAR_SQUARE_LENGTH = 80; // Length of each slot in the hotbar
    public static final int HOTBAR_HEIGHT = HOTBAR_SQUARE_LENGTH; // Height of the hotbar
    public static final int HOTBAR_WIDTH = HOTBAR_SQUARE_LENGTH * HOTBAR_SIZE; // Width of the hotbar
    private Item[] items;
    private int[] itemQuantities;
    
    public Hotbar() {
        items = new Item[HOTBAR_SIZE]; // Assuming a hotbar with 6 slots
        itemQuantities = new int[HOTBAR_SIZE];
        setOpaque(false); // Make the hotbar transparent
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        //use graphics 2d ( so we can translate things during screen scrolling)
        Graphics2D g2d = (Graphics2D) g;
        for (int i= 0; i < HOTBAR_SIZE; i ++)
            {
                int x = i * HOTBAR_SQUARE_LENGTH;
                // draw each square in the grid
                //g2d.fillRect(x, 0, Grid.BLOCK_SIZE, Grid.BLOCK_SIZE);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(10));
                g2d.drawRect(x, 0, HOTBAR_SQUARE_LENGTH, HOTBAR_SQUARE_LENGTH);
            }
    }
}