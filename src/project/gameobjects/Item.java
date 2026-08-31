package project.gameobjects;

public abstract class Item {
    /* 
    types of items:
    - pickaxe
    - weapon
    - resource (ie stone, dirt, diamonds)
    - buff (optional for later)
    */






    public String getItemType()
    {
        return this.getClass().getSimpleName();
    }
}
