package project.UI;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InventoryButton extends JButton {
    boolean inventoryOpen;
    static final int INVENTORY_BUTTON_WIDTH = 100;
    static final int INVENTORY_BUTTON_HEIGHT = 100;
    static final int INVENTORY_BUTTON_X = 10;
    static final int INVENTORY_BUTTON_Y = 10;
    public InventoryButton(){
        inventoryOpen = false;
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inventoryOpen = !inventoryOpen;
                Screen.inv[0].setVisible(inventoryOpen);
            }
       });
 
    }
}