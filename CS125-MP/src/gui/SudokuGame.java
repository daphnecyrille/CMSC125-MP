package gui;

public class SudokuGame {
    public static void main(String[] args) {
        // Show pop-up when the game starts and capture the difficulty choice
        StartPopup popup = new StartPopup();
        String difficulty = popup.showPopup();

        if (difficulty != null) {
            // You can now use the difficulty level for puzzle generation or other purposes
            System.out.println("Difficulty selected: " + difficulty);

            // Launch the main Sudoku GUI with the selected difficulty
            javax.swing.SwingUtilities.invokeLater(() -> {
                new SudokuGUI(difficulty).setVisible(true);  // Pass difficulty to SudokuGUI constructor
            });
        }
    }
}
