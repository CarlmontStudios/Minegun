package project.gameobjects;

import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Timer;
import project.UI.GameMap;
import project.UI.Grid;
import project.UI.Hitbox;
import project.UI.Sprite;
import project.utilities.Vector;

public class Projectile {

    public static enum Type {
        //placeholder names, can change later
        BULLET,
        ROCKET,
        LASER,
        MINING_PROJECTILE
    }

    public static final int DEFAULT_PICKAXE_DAMAGE = 1;

    private Type type;
    private Hitbox hitbox;
    private double angle; 
    private int velocity;
    private int maxDistance; // in pixels
    private int distanceTraveled;
    private int mining_damage;

    private Vector targetedBlock; //used for mining projectiles
    private ArrayList<Vector> collidedBlocks; //used for mining projectiles
    Timer timer;
    

    public Projectile(Type type) {
        distanceTraveled = 0;
        if (type == Type.MINING_PROJECTILE){
            hitbox = new Hitbox(GameMap.player[0].getPixelX() + (Grid.BLOCK_SIZE / 2), GameMap.player[0].getPixelY() + (Grid.BLOCK_SIZE / 2), Grid.BLOCK_SIZE / 40, Grid.BLOCK_SIZE / 40, false);
            velocity = 12;
            maxDistance = 200;
            mining_damage = DEFAULT_PICKAXE_DAMAGE;
            angle = getPlayerToMouseAngle();
            targetedBlock = getSelectedBlockCoordinates();
            collidedBlocks = new ArrayList<>();
            //System.out.println("targeted block: " + targetedBlock);
            
            
        }

        timer = new Timer(32, e -> {
                
                travel(angle);
                //System.out.println("projectile traveling at angle: " + angle + " radians");
                if (hitbox.collidingWithSolidBlock()) {
                    //System.out.println("hitting solid block");
                    if (Hitbox.getCollidedBlock(hitbox) != null) {
                        collidedBlocks.addAll(Hitbox.getCollidedBlocks(hitbox));
                        //System.out.println("collided blocks: " + collidedBlocks);
                        if (collidedBlocks.contains(targetedBlock)) {
                            System.out.println("mining projectile hit targeted block at: " + targetedBlock.getX() + ", " + targetedBlock.getY());
                            //Vector collidedBlock = Hitbox.getCollidedBlock(hitbox);
                            damageBlock(targetedBlock);
                        }
                        //System.out.println("Block at (" + blockX + ", " + blockY + ") damaged. New health: " + Grid.block_health_grid[blockX][blockY]);
                        //System.out.println("travel distance traveled: " + distanceTraveled + " pixels");
                        timer.stop();
                    }
                    
                }
                if (distanceTraveled >= maxDistance) {
                    timer.stop();
                }
            });
            timer.start();
        
    }

    /** 
     * makes the projectile travel towards the given direction (uses radians for units)
     */
    public void travel(double angle){
        if (distanceTraveled < maxDistance){
            int deltaX = (int) (velocity * Math.cos(angle));
            int deltaY = (int) (velocity * Math.sin(angle));
            hitbox.setPixelX(hitbox.getPixelX() + deltaX);
            hitbox.setPixelY(hitbox.getPixelY() + deltaY);
            distanceTraveled += velocity;
        }
        
        
        
    }

    private double getPlayerToMouseAngle(){
        int X = hitbox.getPixelX();
        int Y = hitbox.getPixelY();
        
        int mouseX = GameMap.getMouseX();
        int mouseY = GameMap.getMouseY();
        double angle = Math.atan2(mouseY - Y, mouseX - X);
        return angle;
    }

    private double getDistanceToMouse(){
        int playerCenterX = GameMap.player[0].getPixelX() + (Grid.BLOCK_SIZE * Player.PLAYER_WIDTH) / 2;
        int playerCenterY = GameMap.player[0].getPixelY() + (Grid.BLOCK_SIZE * Player.PLAYER_HEIGHT) / 2;
        int mouseX = GameMap.getMouseX();
        int mouseY = GameMap.getMouseY();
        double distance = Math.sqrt(Math.pow(mouseX - playerCenterX, 2) + Math.pow(mouseY - playerCenterY, 2));
        return distance;
    }

    private Vector getSelectedBlockCoordinates(){
        int mouseX = GameMap.getMouseX();
        int mouseY = GameMap.getMouseY();
        int blockX = mouseX / Grid.BLOCK_SIZE;
        int blockY = mouseY / Grid.BLOCK_SIZE;
        return new Vector(blockX, blockY);
    }

    private void damageBlock(Vector block){
        
        int blockX = block.getX();
        int blockY = block.getY();
        Grid.block_health_grid[blockX][blockY] -= mining_damage;
        if (Grid.block_health_grid[blockX][blockY] <= 0.8 * Grid.block_max_health_grid[blockX][blockY]) {
            String crackedSpritePath = Sprite.CRACKED_1; // Path to the cracked sprite image

        if (Grid.block_health_grid[blockX][blockY] <= 0.5 * Grid.block_max_health_grid[blockX][blockY]) {
            crackedSpritePath = Sprite.CRACKED_0; // Path to the more cracked sprite image
        }
        try {
            Grid.block_sprite_grid[blockX][blockY] = new Sprite(crackedSpritePath, 1, 1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
                            
        }
    }

    // private void mineBlock(){
        
    //     Vector selectedBlock = getSelectedBlockCoordinates();
    //     int blockX = selectedBlock.getX();
    //     int blockY = selectedBlock.getY();
    //     if (getDistanceToMouse() <= maxDistance) {
    //         Grid.block_health_grid[blockX][blockY] -= mining_damage;
    //     if (Grid.block_health_grid[blockX][blockY] <= 0.8 * Grid.block_max_health_grid[blockX][blockY]) {
    //         String crackedSpritePath = Sprite.CRACKED_1; // Path to the cracked sprite image

    //         if (Grid.block_health_grid[blockX][blockY] <= 0.5 * Grid.block_max_health_grid[blockX][blockY]) {
    //             crackedSpritePath = Sprite.CRACKED_0; // Path to the more cracked sprite image
    //         }
    //         try {
    //             Grid.block_sprite_grid[blockX][blockY] = new Sprite(crackedSpritePath, 1, 1);
    //         } catch (IOException ex) {
    //             ex.printStackTrace();
    //         }
    //     }
        
            
    //     }
    // }


}
