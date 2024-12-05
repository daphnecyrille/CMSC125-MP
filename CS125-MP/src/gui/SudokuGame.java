package gui;

public class SudokuGame {
    public static void main(String[] args) {
        // Show pop-up when the game starts
        StartPopup popup = new StartPopup();
        popup.showPopup();

        // Launch the main Sudoku GUI
        javax.swing.SwingUtilities.invokeLater(() -> {
            new SudokuGUI().createAndShowGUI();
        });
    }
}
