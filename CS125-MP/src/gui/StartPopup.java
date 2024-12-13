package gui;

import javax.swing.*;
import java.awt.*;

public class StartPopup extends JFrame {
    public String showPopup() {

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

        JOptionPane.showMessageDialog(
                null,
                "You chose " + options[choice] + "!",
                "Sudoku Game",
                JOptionPane.PLAIN_MESSAGE
        );

        if (choice == -1) {
            return null;
        }

        System.out.println("Difficulty chosen: " + options[choice]);
        return options[choice];

    }
}
