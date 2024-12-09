package gui;

import javax.swing.*;
import java.awt.*;

public class StartPopup {
    public String showPopup() {
        String[] options = {"Easy", "Medium", "Hard"};

        int choice = JOptionPane.showOptionDialog(
                null,
                "Welcome to Sudoku! Choose your difficulty:",
                "Sudoku Game Checker",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,

                null,
                options,
                options[0]
        );

        if (choice == -1) {
            return null;  // User closed the popup without making a choice
        }

        System.out.println("Difficulty chosen: " + options[choice]);
        return options[choice];

    }
}
