package project.UI;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Screen {
    JFrame frame;
    JButton button;

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

            JButton button = new JButton("Start");
            button.setBackground(new Color(70, 70, 70));
            button.setForeground(Color.BLACK);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setPreferredSize(new Dimension(150, 40));
            button.setBounds(300, 300, 150, 50);
            button.setVisible(true);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Button was clicked");
                }
            });
            
            frame.add(button);
            frame.setVisible(true);
    }
}