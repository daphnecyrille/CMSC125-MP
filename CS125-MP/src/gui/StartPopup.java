package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartPopup extends JFrame {

    public interface DifficultySelectionListener {
        void onDifficultySelected(String difficulty);
    }

    private DifficultySelectionListener listener;

    public StartPopup(DifficultySelectionListener listener) {
        this.listener = listener;

        // Set up the frame
        setTitle("Sudoku Game");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Background panel with gradient
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(110, 33, 168), 0, getHeight(), new Color(184, 52, 110));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        add(backgroundPanel, BorderLayout.CENTER);

        // Title label
        JLabel titleLabel = new JLabel("SUDOKU");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        backgroundPanel.add(titleLabel);

        // Buttons for difficulties
        String[] difficulties = {"Easy", "Medium", "Hard"};
        Color[] buttonColors = {new Color(48, 172, 236), new Color(244, 202, 87), new Color(233, 82, 100)};
        for (int i = 0; i < difficulties.length; i++) {
            JButton button = new JButton(difficulties[i]);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setForeground(Color.WHITE);
            button.setBackground(buttonColors[i]);
            button.setOpaque(true);
            button.setFocusPainted(false);

            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(buttonColors[i], 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setMaximumSize(new Dimension(300, 50));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));

            String difficulty = difficulties[i];
            button.addActionListener(e -> {
                if (listener != null) {
                    listener.onDifficultySelected(difficulty);
                }
                dispose();
            });

            backgroundPanel.add(Box.createVerticalStrut(20));
            backgroundPanel.add(button);
        }

        // Create an "Exit" button
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(149, 145, 137));
        exitButton.setOpaque(true);
        exitButton.setFocusPainted(false);
        exitButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(149, 145, 137), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setMaximumSize(new Dimension(300, 50));
        exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        exitButton.addActionListener(e -> {
            System.exit(0); // Exit the program
        });

        // Add the Exit button to the background panel
        backgroundPanel.add(Box.createVerticalStrut(20));
        backgroundPanel.add(exitButton);

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartPopup(difficulty -> {
            // Handle difficulty selection
            System.out.println("Selected Difficulty: " + difficulty);
            // Here, you can instantiate your Sudoku game with the chosen difficulty
        }));
    }
}
