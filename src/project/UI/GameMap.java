package project.UI;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import project.gameobjects.Player;
import project.gameobjects.WormBodyPart;


public class GameMap extends JPanel {

    private final int viewportWidth = Screen.VIEWPORT_WIDTH;
    private final int viewportHeight = Screen.VIEWPORT_HEIGHT;

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
                    g2d.setColor(Grid.color_grid[i][j]);
                } else if (Grid.grid[i][j] == 0){
                    g2d.setColor(Color.BLACK);
                }
                
                //for (int k = 0; i < Grid.MAP_WIDTH; k++) {
                //    for (int l = 0; l < Grid.MAP_HEIGHT; l++) {

                //    }
                //}
                if (Grid.worm_grid[i][j] == 1) {
                    g2d.setColor(Color.BLUE);
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

        // displaying the worm: we can't use the grid because then it would move in large steps,
        // and it could only be displyaed exactly on a certain grid position.
        for (int i = 0; i < Screen.worms.length; i++) {
            WormBodyPart[] bodyParts2 = Screen.worms[i].body;
            System.out.println("The length is:" + bodyParts2.length);
            for (int j = 0; j < bodyParts2.length; j++) {
                System.out.println(i + " " + j);
                if (j == 20) System.out.println("before exception");
                g2d.fillRect(bodyParts2[j].getX() - Grid.BLOCK_SIZE / 2, 
                    bodyParts2[j].getY() - Grid.BLOCK_SIZE / 2, 
                    Grid.BLOCK_SIZE / 2, Grid.BLOCK_SIZE / 2);
                if (j == 20) System.out.println("after exception");
            }
        }
    }
}