package project.UI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import project.gameobjects.Player;


public class GameMap extends JPanel {

    private static final int viewportWidth = Screen.VIEWPORT_WIDTH;
    private static final int viewportHeight = Screen.VIEWPORT_HEIGHT;
    public static final Player[] player = new Player[1]; //cool hack to allow player to be treated as static without making it static

    public GameMap() {
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
        int deltaX = (int) (viewportWidth / 2 - GameMap.player[0].getPixelX());
        int deltaY = (int) (viewportHeight / 2 - GameMap.player[0].getPixelY());

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
                    g2d.setColor(Grid.color_grid[i][j]);
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
        g2d.fillRect((int) GameMap.player[0].getPixelX(), (int) GameMap.player[0].getPixelY(),
                     Player.PLAYER_WIDTH * Grid.BLOCK_SIZE, Player.PLAYER_HEIGHT * Grid.BLOCK_SIZE);
    }

}