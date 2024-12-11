package gui;

import javax.swing.*;
import solver.Solver;
import logic.PuzzleGenerator;  // Import the PuzzleGenerator
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuGUI extends JFrame {
    private int[][] board; // Store the current Sudoku board
    private JTextField[][] cells; // GUI components for board cells
    private Solver solver; // Solver instance for validating the board

    public SudokuGUI(String difficulty) {
        setTitle("Sudoku Game");
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the board and solver
        board = new int[9][9];
        solver = new Solver(board);

        // Generate puzzle based on difficulty
        PuzzleGenerator generator = new PuzzleGenerator();
        board = generator.generatePuzzle(difficulty); // Get the generated puzzle

        cells = new JTextField[9][9];
        initializeBoard();

        // Add validation button (optional: user can click to validate their move)
        JButton validateButton = new JButton("Validate");
        validateButton.setFont(new Font("Arial", Font.BOLD, 24));
        validateButton.setBackground(Color.WHITE);
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateBoard();
            }
        });
        
        // Add button to the bottom of the frame
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

        // Validate the board using the Solver
        if (solver.isBoardValid()) {
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
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new SudokuGUI(difficulty).setVisible(true);  // Display the GUI window with the puzzle
                }
            });
        }
    }
}
