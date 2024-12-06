package logic;

import java.util.Random;

public class PuzzleGenerator {

    private static final int SIZE = 9;
    private static final int[] EASY = {36, 45, 50};   // Number of pre-filled cells for different difficulties
    private static final int[] MEDIUM = {30, 40, 45};
    private static final int[] HARD = {20, 30, 35};
    
    private int[][] board;

    public PuzzleGenerator() {
        board = new int[SIZE][SIZE]; // Initialize a 9x9 board
        // Fill the board with zeros initially
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 0;
            }
        }
    }

    public int[][] generatePuzzle(String difficulty) {
        int numClues = getCluesForDifficulty(difficulty);

        // Generate a full solved Sudoku board first (you can use an existing solver or algorithm)
        // For simplicity, this part assumes you already have a solved board.
        generateSolvedBoard();

        // Randomly remove numbers based on the difficulty
        removeClues(numClues);

        return board;
    }

    private void generateSolvedBoard() {
        // Simple backtracking to generate a solved Sudoku board (you can optimize or replace this)
        solve(board);
    }

    private boolean solve(int[][] board) {
        // Basic backtracking algorithm to solve the Sudoku grid
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) { // Find empty cell
                    for (int num = 1; num <= SIZE; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (solve(board)) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false; // No valid number found
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int col, int num) {
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num || board[row - row % 3 + i / 3][col - col % 3 + i % 3] == num) {
                return false;
            }
        }
        return true;
    }

    private void removeClues(int numClues) {
        Random random = new Random();
        int cluesRemoved = 0;
        
        while (cluesRemoved < (SIZE * SIZE - numClues)) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            
            if (board[row][col] != 0) {
                board[row][col] = 0;
                cluesRemoved++;
            }
        }
    }

    private int getCluesForDifficulty(String difficulty) {
        switch (difficulty) {
            case "Easy":
                return EASY[new Random().nextInt(EASY.length)];
            case "Medium":
                return MEDIUM[new Random().nextInt(MEDIUM.length)];
            case "Hard":
                return HARD[new Random().nextInt(HARD.length)];
            default:
                return 36; // Default to Easy if invalid difficulty
        }
    }
}
