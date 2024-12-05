package gui;

import javax.swing.JOptionPane;

public class StartPopup {
    public void showPopup() {
        String[] options = {"Easy", "Medium", "Hard"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Welcome to Sudoku! Choose your difficulty:",
                "Sudoku Game",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        System.out.println("Difficulty chosen: " + options[choice]);
        // You can store or use the difficulty choice for puzzle generation later.
    }
}
