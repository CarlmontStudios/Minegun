package project.UI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import project.gameobjects.Player;


public class GameMap extends JPanel {

    private final int viewportWidth;
    private final int viewportHeight;

    public GameMap(int viewportWidth, int viewportHeight) {
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;
        setBackground(Color.BLACK);
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
        int deltaX = (int) (viewportWidth / 2 - Player.getPixelX());
        int deltaY = (int) (viewportHeight / 2 - Player.getPixelY());

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
                if(Grid.grid[i][j] == 1){
                    g2d.setColor(Screen.DIRT_COLOR_0);
                } else if (Grid.grid[i][j] == 0){
                    g2d.setColor(Color.BLACK);
                }

                g2d.fillRect(x, y, Grid.BLOCK_SIZE, Grid.BLOCK_SIZE);
                g2d.setColor(Color.BLACK);
                g2d.drawRect(x, y, Grid.BLOCK_SIZE, Grid.BLOCK_SIZE);
            }
        }

        // draw the player at its world position -- since deltaX/deltaY above
        // were derived specifically to center the player, this lands it in
        // the middle of the viewport every frame without any extra math
        g2d.setColor(Player.PLAYER_COLOR);
        g2d.fillRect((int) Player.getPixelX(), (int) Player.getPixelY(),
                     Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);
    }
}