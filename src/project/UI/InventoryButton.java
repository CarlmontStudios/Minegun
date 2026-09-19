package project.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

public class InventoryButton extends JButton {
    boolean inventoryOpen;
    static final int INVENTORY_BUTTON_WIDTH = 85;
    static final int INVENTORY_BUTTON_HEIGHT = 85;
    static final int INVENTORY_BUTTON_X = Hotbar.HOTBAR_X - 100;
    static final int INVENTORY_BUTTON_Y = Hotbar.HOTBAR_Y;
    private Sprite sprite; 
    public InventoryButton(){
        try {
            sprite = new Sprite(Sprite.INVENTORY_BUTTON, INVENTORY_BUTTON_X, INVENTORY_BUTTON_Y, INVENTORY_BUTTON_WIDTH, INVENTORY_BUTTON_HEIGHT, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        inventoryOpen = false;
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryOpen = !inventoryOpen;
                Screen.inv[0].setVisible(inventoryOpen);
                if (inventoryOpen) {
                    try {
                        sprite.setImagePath(Sprite.HIGHLIGHTED_INVENTORY_BUTTON);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    try {
                        sprite.setImagePath(Sprite.INVENTORY_BUTTON);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
       });
 
    }

    @Override 
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        g.drawImage(sprite.getImage(), 0, 0, INVENTORY_BUTTON_WIDTH, INVENTORY_BUTTON_HEIGHT, null);
    }
}