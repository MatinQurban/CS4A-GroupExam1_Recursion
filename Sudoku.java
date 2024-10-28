import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


// High-Level Description: An explanation of how recursive backtracking will be used to solve the problem.

// 1. High-Level Description:
//      - For the Sudoku problem solver, recursive backtracking will allow us to place all necessary numbers (1-9) in all empty slots while adhering
//        to the rules of the challenge. In essence, the solver cannnot place any number more than once in a given row, column, or 3x3 subgrid.
//      - The backtracking will exhaust all available numbers in a given column and once it's not able to place all numbers in that column, the algorithm will
//        recursively backtrack and make another choice if possible and etc.
//      - Empty slots are marked with zeros.
// 2. Model:
//      - There are two shared states passed between recursive calls: board and grids.
//      - Board is a 9x9 grid and is used to place numbers and check values in each row and column.
//      - Grids is a representation of the 3x3 subgrid system which consists of a hash map with the grid number (1-9) serving as keys and values are Hash Sets of
//        unique numbers in each of the 9 subgrids. This 

// Testing Program: A Java program that tests the solver.
// Inputs and Outputs: Example inputs to the solver and the expected output.


public class Sudoku {
    
    private static boolean isValid(int[][] board, HashMap<Integer, Set<Integer>> grids, int row, int column, int num) {
        // Check row for duplicates
        for (int i = 0; i < board.length; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // Check column for duplicates
        for (int i = 0; i < board.length; i++) {
            if (board[i][column] == num) {
                return false;
            }
        }

        // Check 3x3 grid for duplicates
        int grid = determineGrid(row, column);
        if (grids.get(grid).contains(num)) {
            return false;
        }

        // If no duplicates found, add the number to the grid
        grids.get(grid).add(num);
        return true;
    }

    private static int determineGrid(int row, int column){
        int grid;
        if (row < 3){
            if (column < 3){
                grid = 1;
            }
            else if (column >= 3 && column < 6){
                grid = 2;
            }
            else{
                grid = 3;
            }
        }
        else if (row >= 3 && row < 6){
            if (column < 3){
                grid = 4;
            }
            else if (column >= 3 && column < 6){
                grid = 5;
            }
            else{
                grid = 6;
            }
        }
        else{
            if (column < 3){
                grid = 7;
            }
            else if (column >= 3 && column < 6){
                grid = 8;
            }
            else{
                grid = 9;
            }
        }

        return grid;
    }

    private static void printBoard(int[][] board) {
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (c == 2 || c == 5) {
                    System.out.print(" " + board[r][c] + "   ");
                } else {
                    System.out.print(" " + board[r][c] + " ");
                }
            }
            if (r == 2 || r == 5) {
                System.out.println("\n");
            } else {
                System.out.println();
            }
        }
    }

    private static boolean solveSudokuHelper(int row, int col, int[][] board, HashMap<Integer, Set<Integer>> grids) {
        
        int nextRow;
        int nextCol;
        
        if (row == board.length) {
            return true;  // Sudoku has been solved
        }
        else if (col == board.length - 1){
            nextRow = row + 1;
        }
        else{
            nextRow = row;
        }

        nextCol = (col + 1) % board.length;

        if (board[row][col] != 0) {
            return solveSudokuHelper(nextRow, nextCol, board, grids);  // Skip pre-filled cells
        }

        int grid = determineGrid(row, col);

        for (int num = 1; num <= 9; num++) {
            if (isValid(board, grids, row, col, num)) {
                board[row][col] = num;  // Place number

                if (solveSudokuHelper(nextRow, nextCol, board, grids)) {
                    return true;
                }

                // Backtrack
                board[row][col] = 0;
                grids.get(grid).remove(num);
            }
        }
        return false;  // No solution found
    }

    public static boolean solveSudoku(int[][] board) {
        
        HashMap<Integer, Set<Integer>> grids = new HashMap<>();

        // Initialize empty sets for each grid
        for (int i = 1; i <= 9; i++) {
            grids.put(i, new HashSet<>());
        }

        // Add existing board numbers to grids for validation
        int num;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                num = board[row][col];
                if (num != 0) {
                    int grid = determineGrid(row, col);
                    grids.get(grid).add(num);
                }
            }
        }

        return solveSudokuHelper(0, 0, board, grids);
    }

    public static void main(String[] args) {
        int[][] board1 = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
    };
    System.out.println("Test Case 1:");
    printBoard(board1);
    if (solveSudoku(board1)) {
        System.out.println("Solved:");
        printBoard(board1);
    } else {
        System.out.println("No Solution");
    }
    System.out.println("\n-------------------\n");

    // Test Case 2: Already solved Sudoku board
    int[][] board2 = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
    };
    System.out.println("Test Case 2:");
    printBoard(board2);
    if (solveSudoku(board2)) {
        System.out.println("Solved (must be unchanged):");
        printBoard(board2);
    } else {
        System.out.println("No Solution");
    }
    System.out.println("\n-------------------\n");

    // Test Case 3: Unsolvable Sudoku board
    int[][] board3 = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 0}
    };
    System.out.println("Test Case 3 (unsolvable): ");
    printBoard(board3);
    if (solveSudoku(board3)) {
        System.out.println("Solved:");
        printBoard(board3);
    } else {
        System.out.println("No Solution");
    }
    System.out.println("\n-------------------\n");
    }
}

// Expected Output:
/* 
Test Case 1:
 5  3  0    0  7  0    0  0  0 
 6  0  0    1  9  5    0  0  0 
 0  9  8    0  0  0    0  6  0 

 8  0  0    0  6  0    0  0  3 
 4  0  0    8  0  3    0  0  1 
 7  0  0    0  2  0    0  0  6 

 0  6  0    0  0  0    2  8  0 
 0  0  0    4  1  9    0  0  5 
 0  0  0    0  8  0    0  7  9 
Solved:
 5  3  4    6  7  8    9  1  2 
 6  7  2    1  9  5    3  4  8 
 1  9  8    3  4  2    5  6  7 

 8  5  9    7  6  1    4  2  3 
 4  2  6    8  5  3    7  9  1 
 7  1  3    9  2  4    8  5  6 

 9  6  1    5  3  7    2  8  4 
 2  8  7    4  1  9    6  3  5 
 3  4  5    2  8  6    1  7  9 

-------------------

Test Case 2:
 5  3  4    6  7  8    9  1  2 
 6  7  2    1  9  5    3  4  8 
 1  9  8    3  4  2    5  6  7 

 8  5  9    7  6  1    4  2  3 
 4  2  6    8  5  3    7  9  1 
 7  1  3    9  2  4    8  5  6 

 9  6  1    5  3  7    2  8  4 
 2  8  7    4  1  9    6  3  5 
 3  4  5    2  8  6    1  7  9 
Solved (must be unchanged):
 5  3  4    6  7  8    9  1  2 
 6  7  2    1  9  5    3  4  8 
 1  9  8    3  4  2    5  6  7 

 8  5  9    7  6  1    4  2  3 
 4  2  6    8  5  3    7  9  1 
 7  1  3    9  2  4    8  5  6 

 9  6  1    5  3  7    2  8  4 
 2  8  7    4  1  9    6  3  5 
 3  4  5    2  8  6    1  7  9 

-------------------

Test Case 3 (unsolvable): 
 5  3  4    6  7  8    9  1  2 
 6  7  2    1  9  5    3  4  8 
 1  9  8    3  4  2    5  6  7 

 8  5  9    7  6  1    4  2  3 
 4  2  6    8  5  3    7  9  1 
 7  1  3    9  2  4    8  5  6 

 9  6  1    5  3  7    2  8  4 
 2  8  7    4  1  9    6  3  5 
 3  4  5    2  8  6    1  7  0 
Solved:
 5  3  4    6  7  8    9  1  2 
 6  7  2    1  9  5    3  4  8 
 1  9  8    3  4  2    5  6  7 

 8  5  9    7  6  1    4  2  3 
 4  2  6    8  5  3    7  9  1 
 7  1  3    9  2  4    8  5  6 

 9  6  1    5  3  7    2  8  4 
 2  8  7    4  1  9    6  3  5 
 3  4  5    2  8  6    1  7  9 


// End of program
*/