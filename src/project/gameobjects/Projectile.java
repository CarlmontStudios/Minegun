package project.gameobjects;

import java.io.IOException;
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
    Timer timer;
    

    public Projectile(Type type) {
        distanceTraveled = 0;
        if (type == Type.MINING_PROJECTILE){
            hitbox = new Hitbox(GameMap.player[0].getPixelX(), GameMap.player[0].getPixelY() + Grid.BLOCK_SIZE, Grid.BLOCK_SIZE / 40, Grid.BLOCK_SIZE / 40, false);
            velocity = 8;
            maxDistance = 120;
            mining_damage = DEFAULT_PICKAXE_DAMAGE;
            angle = getPlayerToMouseAngle();
            
            
        }

        timer = new Timer(64, e -> {
                
                travel(angle);
                //System.out.println("projectile traveling at angle: " + angle + " radians");
                if (hitbox.collidingWithSolidBlock()) {
                    if (Hitbox.getCollidedBlock(hitbox) != null) {
                        Vector collidedBlock = Hitbox.getCollidedBlock(hitbox);
                        int blockX = collidedBlock.getX();
                        int blockY = collidedBlock.getY();
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
                        //System.out.println("Block at (" + blockX + ", " + blockY + ") damaged. New health: " + Grid.block_health_grid[blockX][blockY]);
                        //System.out.println("travel distance traveled: " + distanceTraveled + " pixels");
                    }
                    timer.stop();
                } else if (distanceTraveled >= maxDistance) {
                    timer.stop();
                }
            });
            timer.start();
        
    }

    /** 
     * makes the projectile travel towards the given direction (uses radians for units)
     */
    public void travel(double angle){

        int deltaX = (int) (velocity * Math.cos(angle));
        int deltaY = (int) (velocity * Math.sin(angle));
        hitbox.setPixelX(hitbox.getPixelX() + deltaX);
        hitbox.setPixelY(hitbox.getPixelY() + deltaY);
        distanceTraveled += velocity;
        
        
    }

    public double getPlayerToMouseAngle(){
        int playerCenterX = GameMap.player[0].getPixelX() + (Grid.BLOCK_SIZE * Player.PLAYER_WIDTH) / 2;
        int playerCenterY = GameMap.player[0].getPixelY() + (Grid.BLOCK_SIZE * Player.PLAYER_HEIGHT) / 2;
        int mouseX = GameMap.getMouseX();
        int mouseY = GameMap.getMouseY();
        double angle = Math.atan2(mouseY - playerCenterY, mouseX - playerCenterX);
        return angle;
    }
}
