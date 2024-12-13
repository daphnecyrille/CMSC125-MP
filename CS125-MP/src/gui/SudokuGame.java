package gui;

import javax.swing.*;

public class SudokuGame {
    public static void main(String[] args) {
        // Launch the StartPopup and handle difficulty selection
        SwingUtilities.invokeLater(() -> {
            new StartPopup(difficulty -> {
                System.out.println("Difficulty selected: " + difficulty);

                // Start the Sudoku GUI with the selected difficulty
                SwingUtilities.invokeLater(() -> new SudokuGUI(difficulty).setVisible(true));
            });
        });
    }
}
