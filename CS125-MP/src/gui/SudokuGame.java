package gui;

import javax.swing.*;
import java.awt.*;

public class SudokuGame extends JFrame {
    public static void main(String[] args) {
        // Show pop-up when the game starts and capture the difficulty choice
        StartPopup popup = new StartPopup();
        String difficulty = popup.showPopup();

        if (difficulty != null) {
            
            System.out.println("Difficulty selected: " + difficulty);

            
            javax.swing.SwingUtilities.invokeLater(() -> {
                new SudokuGUI(difficulty).setVisible(true);  // Pass difficulty to SudokuGUI constructor
            });
        }
    }
}
