package gui;

import javax.swing.*;
import logic.PuzzleGenerator;
import logic.SudokuValidator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGUI extends JFrame {
    private int[][] board; // Store the current Sudoku board
    private JTextField[][] cells; // GUI components for board cells

    public SudokuGUI(String difficulty) {
        setTitle("Sudoku Game");
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the board
        board = new int[9][9];

        // Generate puzzle based on difficulty
        PuzzleGenerator generator = new PuzzleGenerator();
        board = generator.generatePuzzle(difficulty); // Get the generated puzzle

        cells = new JTextField[9][9];
        initializeBoard();

        
        JButton validateButton = new JButton("Validate");
        validateButton.setFont(new Font("Arial", Font.BOLD, 24));
        validateButton.setBackground(Color.WHITE);
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateBoard();
            }
        });

        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(validateButton);
        add(buttonPanel, BorderLayout.LINE_START);

        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 24));
        returnButton.setBackground(Color.WHITE);
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    SudokuGame.main(null); // Redirect to the menu by calling the main method of SudokuGame
                });
            }
        });

        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setBackground(Color.WHITE);
        buttonPanel2.add(returnButton);
        add(buttonPanel2, BorderLayout.LINE_END);
    }

    private void initializeBoard() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 9));

        // Create the grid of cells (JTextFields)
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                cells[row][col] = new JTextField();
                cells[row][col].setForeground(Color.BLUE);
                cells[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                cells[row][col].setHorizontalAlignment(JTextField.CENTER);
                cells[row][col].setFont(new Font("Times New Roman", Font.BOLD, 24));

                // Display the generated puzzle values
                if (board[row][col] != 0) {
                    cells[row][col].setText(String.valueOf(board[row][col]));
                    cells[row][col].setForeground(Color.BLACK);
                    cells[row][col].setFont(new Font("Times New Roman", Font.PLAIN, 24));
                    cells[row][col].setEditable(false); // Disable editing for pre-filled cells
                }

                panel.add(cells[row][col]);
            }
        }

        add(panel, BorderLayout.CENTER);
    }

    /**
     * This method will be called when the player clicks the "Validate" button.
     */
    private void validateBoard() {
        // Fill the board with the current values from the text fields
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                try {
                    String text = cells[row][col].getText();
                    board[row][col] = text.isEmpty() ? 0 : Integer.parseInt(text);
                } catch (NumberFormatException e) {
                    board[row][col] = 0; // Default to 0 if the input is invalid
                }
            }
        }

        // Validate the board using the SudokuValidator
        SudokuValidator validator = new SudokuValidator();
        if (validator.validate(board)) {
            JOptionPane.showMessageDialog(this, "The board is valid!");
        } else {
            JOptionPane.showMessageDialog(this, "The board is invalid. Try again!");
        }
    }

    public static void main(String[] args) {
        // Show pop-up when the game starts and capture the difficulty choice
        StartPopup popup = new StartPopup();
        String difficulty = popup.showPopup();

        if (difficulty != null) {
            // Run the game
            SwingUtilities.invokeLater(() -> new SudokuGUI(difficulty).setVisible(true));
        }
    }
}
