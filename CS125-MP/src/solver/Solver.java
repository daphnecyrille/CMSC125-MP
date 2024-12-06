package solver;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Solver {
    private static final int SIZE = 9; // Standard Sudoku grid size
    private int[][] board; // The Sudoku board

    public Solver(int[][] board) {
        this.board = board;
    }

    /**
     * Validates the entire Sudoku board using multithreading.
     *
     * @return true if the board is valid, false otherwise.
     */
    public boolean isBoardValid() {
        ExecutorService executor = Executors.newFixedThreadPool(SIZE * 3); // Threads for rows, columns, and grids

        // Shared result flag (thread-safe)
        final boolean[] isValid = {true};

        // Validate rows
        for (int row = 0; row < SIZE; row++) {
            int finalRow = row;
            executor.execute(() -> {
                if (!isRowValid(finalRow)) {
                    synchronized (isValid) {
                        isValid[0] = false;
                    }
                }
            });
        }

        // Validate columns
        for (int col = 0; col < SIZE; col++) {
            int finalCol = col;
            executor.execute(() -> {
                if (!isColumnValid(finalCol)) {
                    synchronized (isValid) {
                        isValid[0] = false;
                    }
                }
            });
        }

        // Validate 3x3 grids
        for (int row = 0; row < SIZE; row += 3) {
            for (int col = 0; col < SIZE; col += 3) {
                int finalRow = row;
                int finalCol = col;
                executor.execute(() -> {
                    if (!isGridValid(finalRow, finalCol)) {
                        synchronized (isValid) {
                            isValid[0] = false;
                        }
                    }
                });
            }
        }

        executor.shutdown();

        try {
            executor.awaitTermination(Long.MAX_VALUE, java.util.concurrent.TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return isValid[0];
    }

    /**
     * Validates a single row in the board.
     *
     * @param row the row index to validate.
     * @return true if the row is valid, false otherwise.
     */
    private boolean isRowValid(int row) {
        boolean[] seen = new boolean[SIZE + 1];
        for (int col = 0; col < SIZE; col++) {
            int value = board[row][col];
            if (value != 0) {
                if (seen[value]) {
                    return false;
                }
                seen[value] = true;
            }
        }
        return true;
    }

    /**
     * Validates a single column in the board.
     *
     * @param col the column index to validate.
     * @return true if the column is valid, false otherwise.
     */
    private boolean isColumnValid(int col) {
        boolean[] seen = new boolean[SIZE + 1];
        for (int row = 0; row < SIZE; row++) {
            int value = board[row][col];
            if (value != 0) {
                if (seen[value]) {
                    return false;
                }
                seen[value] = true;
            }
        }
        return true;
    }

    /**
     * Validates a single 3x3 grid in the board.
     *
     * @param startRow the starting row index of the grid.
     * @param startCol the starting column index of the grid.
     * @return true if the grid is valid, false otherwise.
     */
    private boolean isGridValid(int startRow, int startCol) {
        boolean[] seen = new boolean[SIZE + 1];
        for (int row = startRow; row < startRow + 3; row++) {
            for (int col = startCol; col < startCol + 3; col++) {
                int value = board[row][col];
                if (value != 0) {
                    if (seen[value]) {
                        return false;
                    }
                    seen[value] = true;
                }
            }
        }
        return true;
    }

    /**
     * Prints the current Sudoku board.
     */
    public void printBoard() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }
}
