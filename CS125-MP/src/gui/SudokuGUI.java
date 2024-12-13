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
        setMinimumSize(new Dimension(800, 600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Center the window
        setLocationRelativeTo(null); 

        board = new int[9][9];
        PuzzleGenerator generator = new PuzzleGenerator();
        board = generator.generatePuzzle(difficulty);

        cells = new JTextField[9][9];
        initializeBoard();

        JButton validateButton = new JButton("Validate");
        validateButton.setFont(new Font("Arial", Font.BOLD, 24));
        validateButton.setBackground(Color.WHITE);
        validateButton.addActionListener(e -> validateBoard());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.PINK);
        buttonPanel.add(validateButton);
        add(buttonPanel, BorderLayout.LINE_START);

        JButton returnButton = new JButton("Return");
        returnButton.setFont(new Font("Arial", Font.BOLD, 24));
        returnButton.setBackground(Color.WHITE);
        returnButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new StartPopup(newDifficulty -> new SudokuGUI(newDifficulty).setVisible(true)));
        });

        JPanel buttonPanel2 = new JPanel();
        buttonPanel2.setBackground(Color.PINK);
        buttonPanel2.add(returnButton);
        add(buttonPanel2, BorderLayout.LINE_END);

        JPanel buttonPanel3 = new JPanel();
        buttonPanel3.setBackground(Color.PINK);
        add(buttonPanel3, BorderLayout.PAGE_START);
    }

    private void initializeBoard() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 9));

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
                }

                panel.add(cells[row][col]);
            }
        }

        add(panel, BorderLayout.CENTER);
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
            JOptionPane.showMessageDialog(this, "The board is valid!");
        } else {
            JOptionPane.showMessageDialog(this, "The board is invalid. Try again!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StartPopup(newDifficulty -> new SudokuGUI(newDifficulty).setVisible(true)));
    }
}
