package project.gameobjects;

public class Inventory {

    /* notes on items
    max of 3 weapons
    max of 1 pickaxe
    */

    private Item[][] inventory;

    public Inventory()
    {
        //First row is the hotbar
        this.inventory = new Item[4][3];
    }






    public void addItem(Item item)
    {   
        for (int i = 0; i < this.inventory.length; i++)
        {
            for (int j = 0; j < this.inventory[i].length; j++)
            {
                if (this.inventory[i][j] == null)
                {
                    this.inventory[i][j] = item;
                }
            }
        }
    }

    public Item getItem(int x, int y)
    {
        return this.inventory[x][y];
    }

    public Item removeItem(int x, int y)
    {
        Item removedItem = this.inventory[x][y];
        this.inventory[x][y] = null;
        return removedItem;
    }

}
