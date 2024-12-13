package gui;

import javax.swing.*;
import java.awt.*;

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
        Color[] buttonColors = {new Color(30, 144, 255), new Color(255, 215, 0), new Color(220, 20, 60)};
        for (int i = 0; i < difficulties.length; i++) {
            JButton button = new JButton(difficulties[i]);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setForeground(Color.WHITE);
            button.setBackground(buttonColors[i]);
            button.setFocusPainted(false);

            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.WHITE, 2),
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

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }
}
