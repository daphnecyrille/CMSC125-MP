package gui;

import javax.swing.*;
import java.awt.*;

public class SudokuGUI {
    private JFrame frame;
    private JTextField[][] board = new JTextField[9][9];

    public void createAndShowGUI() {
        frame = new JFrame("Sudoku Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(9, 9));

        // Create a 9x9 grid of text fields
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                board[row][col] = new JTextField();
                board[row][col].setHorizontalAlignment(JTextField.CENTER);
                frame.add(board[row][col]);
            }
        }

        frame.setSize(600, 600);
        frame.setVisible(true);
    }
}
