package logic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class SudokuValidator {
    private static final int SIZE = 9;

    public boolean validate(int[][] board) {
        ExecutorService executor = Executors.newFixedThreadPool(SIZE * 3); // For rows, columns, and sub-grids
        AtomicBoolean isValid = new AtomicBoolean(true);

        // Validate rows
        for (int row = 0; row < SIZE; row++) {
            int finalRow = row;
            executor.submit(() -> {
                if (!isRowValid(board, finalRow)) {
                    isValid.set(false);
                }
            });
        }

        // Validate columns
        for (int col = 0; col < SIZE; col++) {
            int finalCol = col;
            executor.submit(() -> {
                if (!isColumnValid(board, finalCol)) {
                    isValid.set(false);
                }
            });
        }

        // Validate sub-grids
        for (int grid = 0; grid < SIZE; grid++) {
            int finalGrid = grid;
            executor.submit(() -> {
                if (!isGridValid(board, finalGrid)) {
                    isValid.set(false);
                }
            });
        }

        executor.shutdown();

        // Wait for all threads to complete
        while (!executor.isTerminated()) {
            // Loop until all tasks are done
        }

        return isValid.get();
    }

    private boolean isRowValid(int[][] board, int row) {
        boolean[] seen = new boolean[SIZE + 1]; // 1 to 9
        for (int col = 0; col < SIZE; col++) {
            int value = board[row][col];
            if (value < 1 || value > SIZE || seen[value]) {
                return false;
            }
            seen[value] = true;
        }
        return true;
    }

    private boolean isColumnValid(int[][] board, int col) {
        boolean[] seen = new boolean[SIZE + 1];
        for (int row = 0; row < SIZE; row++) {
            int value = board[row][col];
            if (value < 1 || value > SIZE || seen[value]) {
                return false;
            }
            seen[value] = true;
        }
        return true;
    }

    private boolean isGridValid(int[][] board, int grid) {
        boolean[] seen = new boolean[SIZE + 1];
        int startRow = (grid / 3) * 3;
        int startCol = (grid % 3) * 3;

        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                int value = board[row][col];
                if (value < 1 || value > SIZE || seen[value]) {
                    return false;
                }
                seen[value] = true;
            }
        }
        return true;
    }
}
