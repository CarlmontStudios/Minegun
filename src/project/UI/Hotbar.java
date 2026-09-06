package project.UI;

import javax.swing.JPanel;
import project.gameobjects.Item;

public class Hotbar extends JPanel {

    public static final int HOTBAR_SIZE = 6; // Number of slots in the hotbar
    public static final int MAX_CONSUMABLES_PER_SLOT = 5; // Maximum number of consumables per slot
    private Item[] items;
    private int[] itemQuantities;
    
    public Hotbar() {
        items = new Item[HOTBAR_SIZE]; // Assuming a hotbar with 6 slots
        itemQuantities = new int[HOTBAR_SIZE];
        setOpaque(false); // Make the hotbar transparent
    }
}
