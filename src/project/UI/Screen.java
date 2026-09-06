package project.UI;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Screen {
    JFrame frame;
    JButton startButton;

    public Screen() {
        initializeFrame();
    }

    public void initializeFrame() {

        // boilerplate copied/adapted from EcoTree
              frame = new JFrame("example");
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        int width = 1080;
        int height = 720;
            frame.setSize(width, height);
            Color backgroundColor = new Color(170, 240, 130); // Dark green
            frame.getContentPane().setBackground(backgroundColor);
            
            frame.setLayout(null);

            // initialize controls/ key listeners
            // focus screen - this is necessary in order for controls to work 
                frame.setFocusable(true);
            frame.requestFocusInWindow();

            JButton startButton = new JButton("Start");
            startButton.setBackground(new Color(70, 70, 70));
            startButton.setForeground(Color.BLACK);
            startButton.setFont(new Font("Arial", Font.BOLD, 50));
            startButton.setPreferredSize(new Dimension(150, 40));
            startButton.setBounds(390, 500, 300, 150);
            startButton.setVisible(true);

            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button was clicked");
                }
            });
            
            frame.add(startButton);
            frame.setVisible(true);
    }
}