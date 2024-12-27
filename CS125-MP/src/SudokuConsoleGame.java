import logic.PuzzleGenerator;
import logic.SudokuValidator;

import java.util.Scanner;

public class SudokuConsoleGame {

    private static final int SIZE = 9;
    private int[][] board;

    public static void main(String[] args) {
        SudokuConsoleGame game = new SudokuConsoleGame();
        game.startGame();
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        PuzzleGenerator generator = new PuzzleGenerator();

        System.out.println("Select difficulty (Easy, Medium, Hard): ");
        String difficulty = scanner.nextLine();
        if (!difficulty.matches("Easy|Medium|Hard")) {
            throw new IllegalArgumentException("Difficulty must be Easy, Medium, or Hard");
        }

        board = generator.generatePuzzle(difficulty);
        play(scanner);
    }

    private void play(Scanner scanner) {
        boolean gameOn = true;

        while (gameOn) {
            printBoard();
            System.out.println("Enter your move (row[1-9] col[1-9] value[1-9]) or 'validate' to check solution: ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("validate")) {
                validateBoard();
                gameOn = false; // End the game
            } else {
                try {
                    String[] parts = input.split(" ");
                    int row = Integer.parseInt(parts[0]) - 1;
                    int col = Integer.parseInt(parts[1]) - 1;
                    int value = Integer.parseInt(parts[2]);

                    if (isValidMove(row, col, value)) {
                        board[row][col] = value;
                    } else {
                        System.out.println("Invalid move. Try again.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Format: row col value");
                }
            }
        }
    }

    private void printBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                System.out.print((board[row][col] == 0 ? "." : board[row][col]) + " ");
            }
            System.out.println();
        }
    }

    private void validateBoard() {
        SudokuValidator validator = new SudokuValidator();
        if (validator.validate(board)) {
            System.out.println("CONGRATULATIONS! You solved the puzzle!");
        } else {
            System.out.println("The solution is incorrect. Try again.");
        }
    }

    private boolean isValidMove(int row, int col, int value) {
        return row >= 0 && row < SIZE && col >= 0 && col < SIZE && value > 0 && value <= SIZE && board[row][col] == 0;
    }
}
