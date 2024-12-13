package gui;

import javax.swing.*;
import logic.PuzzleGenerator;
import logic.SudokuValidator;
import java.awt.*;

public class SudokuGUI extends JFrame {
    private int[][] board;
    private JTextField[][] cells;

    public SudokuGUI(String difficulty) {
        setTitle("Sudoku Game");
        setMinimumSize(new Dimension(600, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set up the gradient background
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
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);

        // Center the window
        setLocationRelativeTo(null); 

        board = new int[9][9];
        PuzzleGenerator generator = new PuzzleGenerator();
        board = generator.generatePuzzle(difficulty);

        cells = new JTextField[9][9];
        initializeBoard();

        // Create and add the buttons panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.setOpaque(false); // Transparent panel to match background
        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Validate Button
        JButton validateButton = new JButton("Submit");
        validateButton.setFont(new Font("Arial", Font.BOLD, 24));
        validateButton.setBackground(Color.WHITE);
        validateButton.addActionListener(e -> validateBoard());
        buttonPanel.add(validateButton);

        // Return Button
        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 24));
        returnButton.setBackground(Color.WHITE);
        returnButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new StartPopup(newDifficulty -> new SudokuGUI(newDifficulty).setVisible(true)));
        });
        buttonPanel.add(returnButton);
        
        // Add the Sudoku board to the center of the background panel
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(9, 9));
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setForeground(Color.RED);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Verdana", Font.BOLD, 24));

                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setForeground(Color.BLACK);
                    cells[row][col].setFont(new Font("Verdana", Font.PLAIN, 24));
                    cells[row][col].setEditable(false);
                    cells[row][col].setBackground(Color.LIGHT_GRAY);  // Set background color for given cells
                } else {
                    cells[row][col].setBackground(Color.WHITE);  // Set background color for editable cells
                }

                boardPanel.add(cells[row][col]);
            }
        }
        
        backgroundPanel.add(boardPanel, BorderLayout.CENTER);
    }

    private void initializeBoard() {
        // Already handled inside the `boardPanel` section in the main constructor
    }

    private void validateBoard() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                try {
                    String text = cells[row][col].getText();
                    board[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
                } catch (NumberFormatException e) {
                    board[row][col] = 0;
                }
            }
        }
    
        SudokuValidator validator = new SudokuValidator();
        if (validator.validate(board)) {
            // Custom dialog for valid board
            showCustomMessage("CONGRATULATIONS! You have completed this level.", true);
        } else {
            // Custom dialog for invalid board
            showCustomMessage("Oops, please try again!", false);
        }
    }
    
    private void showCustomMessage(String message, boolean isValid) {
        // Create a custom dialog
        JDialog dialog = new JDialog(this, true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(this); // Center the dialog
    
        // Create a panel for the message
        JPanel panel = new JPanel();
        panel.setBackground(new Color(182, 52, 111));
        panel.setLayout(new BorderLayout());
    
        // Add the message label
        JLabel messageLabel = new JLabel(message, SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 22));
        messageLabel.setForeground(Color.WHITE);
        panel.add(messageLabel, BorderLayout.CENTER);
    
        // Add a close button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial", Font.PLAIN, 16));
        closeButton.addActionListener(e -> dialog.dispose());
        panel.add(closeButton, BorderLayout.SOUTH);
    
        // Set the dialog's content
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartPopup(newDifficulty -> new SudokuGUI(newDifficulty).setVisible(true)));
    }
}
