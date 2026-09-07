package project.UI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
import project.gameobjects.Player;



public class GameMap extends JPanel {

    private static final int viewportWidth = Screen.VIEWPORT_WIDTH;
    private static final int viewportHeight = Screen.VIEWPORT_HEIGHT;
    public static final Player[] player = new Player[1]; //cool hack to allow player to be treated as static without making it static


    private static int mouseScreenX;
    private static int mouseScreenY;
    private static int deltaX;
    private static int deltaY;

    //sprites that load in at the start
    Sprite mouseHoverSprite;
    String hoverSpritePath;


    public GameMap() {
        setBackground(Color.BLACK);
        MouseMotionAdapter mouseTracker = new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseScreenX = e.getX();
                mouseScreenY = e.getY();
            }
 
            @Override
            public void mouseDragged(MouseEvent e) {
                mouseScreenX = e.getX();
                mouseScreenY = e.getY();
            }
        };
        addMouseMotionListener(mouseTracker);
        
    
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        // clear the screen
        super.paintComponent(g);
        //use graphics 2d ( so we can translate things during screen scrolling)
        Graphics2D g2d = (Graphics2D) g;

        // keep the player centered in the viewport: shift the world so the
        // player's current pixel position always lands in the middle of
        // the visible area. This is recalculated every frame, so it works
        // for the very first frame too (no separate "starting bounds" needed).
        deltaX = (int) (viewportWidth / 2 - GameMap.player[0].getPixelX());
        deltaY = (int) (viewportHeight / 2 - GameMap.player[0].getPixelY());

        g2d.translate(deltaX, deltaY);

        // width
        for (int i = 0; i < Grid.MAP_WIDTH; i ++)
        {
            // height
            for (int j= 0; j < Grid.MAP_HEIGHT; j ++)
            {
                // draw each square in the grid
                int x = i * Grid.BLOCK_SIZE;
                int y = j * Grid.BLOCK_SIZE;

                if (Grid.block_health_grid[i][j] <= 0) {
                    Grid.grid[i][j] = 0; // Set the block to empty
                }
                
                if(Grid.grid[i][j] == 1){
                    g2d.setColor(Grid.color_grid[i][j]);
                } else if (Grid.grid[i][j] == 0){
                    g2d.setColor(Color.BLACK);
                }

                g2d.fillRect(x, y, Grid.BLOCK_SIZE, Grid.BLOCK_SIZE);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, Grid.BLOCK_SIZE, Grid.BLOCK_SIZE);

                if (Grid.block_sprite_grid[i][j] != null && Grid.grid[i][j] == 1) {
                    try {
                        g2d.drawImage(Grid.block_sprite_grid[i][j].getImage(), x, y, Grid.BLOCK_SIZE, Grid.BLOCK_SIZE, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // draw the player at its world position -- since deltaX/deltaY above
        // were derived specifically to center the player, this lands it in
        // the middle of the viewport every frame without any extra math
        g2d.setColor(Player.PLAYER_COLOR);
        g2d.fillRect((int) GameMap.player[0].getPixelX(), (int) GameMap.player[0].getPixelY(),
                     Player.PLAYER_WIDTH * Grid.BLOCK_SIZE, Player.PLAYER_HEIGHT * Grid.BLOCK_SIZE);
        
        //for loading sprites
        if (Hotbar.selectedSlot == 0) {
            hoverSpritePath = Sprite.PICKAXE_HOVER; // Path to the pickaxe sprite image
        }
        else {
            hoverSpritePath = null; // Clear the hover sprite path when not in the first slot
            mouseHoverSprite = null; // Clear the mouse hover sprite when not in the first slot
        }
        try {
            mouseHoverSprite = new Sprite(hoverSpritePath, GameMap.getMouseX() - 10, GameMap.getMouseY() - 10, Grid.BLOCK_SIZE, Grid.BLOCK_SIZE, true);
            g2d.drawImage(mouseHoverSprite.getImage(), mouseHoverSprite.getPixelX(), mouseHoverSprite.getPixelY(), mouseHoverSprite.getPixelWidth(), mouseHoverSprite.getPixelHeight(), null);
            //g2d.drawImage(mouseHoverSprite.getImage(), GameMap.getMouseX(), GameMap.getMouseY(), Grid.BLOCK_SIZE, Grid.BLOCK_SIZE, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        for (Sprite sprite : Grid.sprites) {
            if (sprite != null) {
                try {
                    g2d.drawImage(sprite.getImage(), sprite.getPixelX(), sprite.getPixelY(), sprite.getPixelWidth(), sprite.getPixelHeight(), null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * returns the x coordinates of the mouse on the map (in pixels)
     * @return
     */
    public static int getMouseX() {
        return mouseScreenX - deltaX; // subtract deltaX to get the mouse position in world coordinates
    }

    /**
     * returns the y coordinates of the mouse on the map (in pixels)
     * @return
     */
    public static int getMouseY() {
        return mouseScreenY - deltaY; // subtract deltaY to get the mouse position in world coordinates
    }

}