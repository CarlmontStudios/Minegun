package project.UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import project.gameobjects.Player;

public class Screen {
    JFrame frame;
    JButton button;
    public static final GameMap map[] = new GameMap[1]; //cool hack to allow map to be treated as static without making it static
    private GameMap gameMap;
    public Player player;
    Grid grid;
    JLayeredPane gamePanel;

    public static final Color DIRT_COLOR_0 = new Color(40, 20, 0);
    public static final Color DIRT_COLOR_1 = new Color(61, 30, 0);
    public static final Color DIRT_COLOR_2 = new Color(97, 52, 0);

    public static final int VIEWPORT_WIDTH = 1080;
    public static final int VIEWPORT_HEIGHT = 720;

    public Screen() {
        grid = new Grid();
        gamePanel = new JLayeredPane();
        initializeFrame();
    }

    public void initializeFrame() {

        // boilerplate copied/adapted from EcoTree
        frame = new JFrame("example");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setSize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        Color backgroundColor = DIRT_COLOR_2;
        frame.getContentPane().setBackground(backgroundColor);

        frame.setLayout(null);

        // initialize controls/ key listeners
        // focus screen - this is necessary in order for controls to work
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        JButton button = new JButton("Start");
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBounds((VIEWPORT_WIDTH - 150) / 2, (VIEWPORT_HEIGHT - 50) / 2, 150, 50);
        button.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Button was clicked");
                button.setVisible(false);
                
                frame.add(gamePanel);
                gamePanel.setBounds(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);

                // panel is now fixed at the viewport size -- the grid scrolls
                // underneath it via translate, not by moving the panel itself
                Screen.map[0] = new GameMap();
                gameMap = Screen.map[0];
                gameMap.setBounds(0, 0, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
                gamePanel.add(gameMap, JLayeredPane.DEFAULT_LAYER);
                //frame.getContentPane().setComponentZOrder(map, 1);
                gameMap.setVisible(true);
                frame.remove(button);

                Controls.initializeControls(frame, gameMap);
                player = new Player();
                Hotbar hotbar = new Hotbar();
                int hotbarX = (VIEWPORT_WIDTH - Hotbar.HOTBAR_WIDTH) / 2;
                int hotbarY = VIEWPORT_HEIGHT - Hotbar.HOTBAR_HEIGHT - 50;

                
                gamePanel.add(hotbar, JLayeredPane.PALETTE_LAYER);
                hotbar.setBounds(hotbarX, hotbarY, Hotbar.HOTBAR_WIDTH, Hotbar.HOTBAR_HEIGHT);
                //frame.getContentPane().setComponentZOrder(hotbar, 0);
                hotbar.setVisible(true);
                frame.revalidate();
                frame.repaint();
                frame.requestFocusInWindow();
            }
        });

        frame.add(button);
        frame.setVisible(true);
    }
}
