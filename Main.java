// Dependency for Main
import java.util.Scanner;

// Dependency for Solutions 4 and 5
import java.util.ArrayList;

// Dependencies for Solutions 1 and 2
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

// Dependency for Solution 4
import java.util.List;

// Dependency for Solution 5
import java.util.Arrays;

public class Main {
    
    public static void main(String[] args){

        Scanner user_input = new Scanner(System.in); 
        int choice;

        PermOfString perm = new  PermOfString();
        Sudoku sudo = new Sudoku();
        WordSearchPuzzle wsp = new WordSearchPuzzle();
        RatInMaze rat = new RatInMaze();
        ComboSum comsum = new ComboSum();

        do {
            System.out.print("""
            Program Choice to Run:

                1. Permutations of a String
                2. Sudoku Solver
                3. Word Search Puzzle
                4. Rat in Maze
                5. Combination Sum
                0. To Exit!

            Which program would you like to run? 
            """);
            choice = user_input.nextInt();

            switch (choice) {
                case 1:
                    perm.run();
                    break;
                case 2:
                    sudo.run();
                    break;
                case 3:
                    wsp.run();
                    break;
                case 4:
                    rat.run();
                    break;
                case 5:
                    comsum.run();
                    break;
            }
            
        } while (choice != 0);
    }   // end of main()
}


// Permutations of a String
class PermOfString {
    public void run() {
        String test1 = "cat";
        Set<String> result1 = PermutationOfAString(test1);
        PrintList(result1);
        System.out.println();

        String test2 = "wordd";
        Set<String> result2 = PermutationOfAString(test2);
        PrintList(result2);
        System.out.println();

        String test3 = "012";
        Set<String> result3 = PermutationOfAString(test3);
        PrintList(result3);
        System.out.println();

    } // end main


    public static Set<String> PermutationOfAString(String str) {
        Set<String> list = new HashSet<>();
        char[] arr = str.toCharArray();

        System.out.println("Permutations of '" + str + "': ");
        Perm(arr, list, 0);

        return list;
    } // END PermutationOfAString

    public static void Perm(char[] arr, Set<String> list, int index) {

        String element = String.valueOf(arr);

        if(index == arr.length &&  list.contains(element) == false) {
            list.add(element);
        }

        for (int i = index; i < arr.length; i++) {
            Swap(index, i, arr);

            Perm(arr, list, index + 1);

            Swap(index, i, arr);
        }

    } // END Perm (helper)

    public static void Swap(int index1, int index2, char[] arr) {
        char temp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = temp;
    } // END Swap

    public static void PrintList(Set<String> list) {
        int i = 1;
        for (String element: list)
        {
            System.out.println(i + ". " + element);
            i++;
        }
    } // END PrintList

} // END SolutionOne

// Solution to Problem 2
class Sudoku{
    
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
            } else if (column >= 3 && column < 6){
                grid = 2;
            } else{
                grid = 3;
            }
        } else if (row >= 3 && row < 6){
            if (column < 3){
                grid = 4;
            } else if (column >= 3 && column < 6){
                grid = 5;
            } else{
                grid = 6;
            }
        } else {
            if (column < 3){
                grid = 7;
            } else if (column >= 3 && column < 6){
                grid = 8;
            } else {
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
        } else if (col == board.length - 1){
            nextRow = row + 1;
        } else {
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

    public void run() {
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
}   // end of program 2


// Solution to Problem 3
class WordSearchPuzzle{
    public void run(){
      //Create a board to test with, find one on google/online
      char[][] board = //Board from Thompson's Teachings - - - Amanda Thompson on TPT
      {{'P', 'S', 'U', 'Z', 'Z', 'S', 'C', 'S'}, 
      {'L', 'K', 'T', 'W', 'R', 'L', 'O', 'E'},
      {'T', 'I', 'G', 'R', 'J', 'I', 'R', 'E'},
      {'M', 'N', 'L', 'Z', 'E', 'C', 'E', 'D'},
      {'C', 'B', 'U', 'S', 'H', 'E', 'L', 'S'}};
      //Words: Tree, Core, Slice, Bushel, Skin, Seeds
      String findStr1 = "BUSHEL"; //Horizontal Check
      String findStr2 = "SEEDS";  //Vertical Check
      String findStr3 = "BUG";    //False check

      System.out.println("Board:\n");
      printBoard(board);
      System.out.println();

      boolean wordInBoard = wordSearch(board, findStr1);
      System.out.println("Word "+findStr1+" is in the board? " +wordInBoard);
      
      wordInBoard = wordSearch(board, findStr2);
      System.out.println("Word "+findStr2+" is in the board? " +wordInBoard);
      
      wordInBoard = wordSearch(board, findStr3);
      System.out.println("Word "+findStr3+" is in the board? " +wordInBoard);
    }

    public static void printBoard(char[][] board){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean wordSearch(char[][] board, String word){
      // 1. Establish origin
      // This function will search through the board until it finds a char that
      //matches the first letter of the word we are looking for, then calls helpers to do the rest
      
      char firstChar = word.charAt(0);
      String restOfWord = word.substring(1);
      boolean result = false;

      for(int row = 0; row < board.length; row++)
      {
        for(int col = 0; col < board[0].length; col++)
        {
          if(board[row][col] == firstChar)
          {
            result = searchDirectional(board, restOfWord, row, col);
            if (result) return true;
          } 
        }
      }
      return result;
    }
    public static boolean searchDirectional(char[][] board, String word, int row, int col)
    {
      // 2. Establish Direction
      // This one uses the second character of the word and tracks in which direction it goes
      int[] dx = {0, 1, 0, -1};
      int[] dy = {1, 0, -1, 0};
      int nextRow = row;
      int nextCol = col;
      char secondChar = word.charAt(0); // Use second char because I want to find the direction the search should go

      for(int i = 0; i < dx.length; i++)
      {
        nextRow = row + dy[i];
        nextCol = col + dx[i];
        if(nextRow >= 0 && nextRow < board.length && nextCol >= 0 && nextCol < board[0].length)
        {
          if(board[nextRow][nextCol] == secondChar)
          {
            if(completeWord(board, word, row, col, dx[i], dy[i]))
            {
              // System.out.println("TRUE COMPLETE WHYARE YOU NOT RETURNING TRUe?????");
              return true;
            } 
          }
        }
      }

      return false;
    }
    public static boolean completeWord(char[][] board, String word, int row, int col, int dirX, int dirY)
    {
      // 3. Search Direction
      // Base case: if we went through every character already, that means we found the word
      if(word.length() == 0)
      {
        // System.out.println("TRue");
        return true;
      }

      // Edge cases: if on the sides of the board, cannot extend past the borders, so return false
      //if we're going in that direction
      if(row == 0 && dirY == -1) return false;
      if(col == 0 && dirX == -1) return false;
      if(row == board.length-1 && dirY == 1) return false;
      if(col == board[0].length-1 && dirX == 1) return false;

      String reducedWord = word.substring(1);
      char searchChar = word.charAt(0);
      char searchLocation = board[row+dirY][col+dirX];

      if(searchChar != searchLocation)
      {
        // System.out.println("Word_*"+word+"*_");
        return false;
      }
      return completeWord(board, reducedWord, row+dirY, col+dirX, dirX, dirY);
    }
} // end of program 3

// Solution to program 4
class RatInMaze {
    // Constant for the size of the maze
    static final int N = 4;

    public void run() {
        // Define the maze: 1 is an open cell, 0 is a blocked cell
        int[][] maze = {
            {1, 0, 0, 0},
            {1, 1, 0, 1},
            {0, 1, 0, 0},
            {0, 1, 1, 1}
        };
        
        // List to store all found paths
        List<String> paths = new ArrayList<>();
        
        System.out.println("Maze:\n");
        printBoard(maze);
        System.out.println();
        
        // Start finding paths from the top-left corner (0, 0)
        findPaths(maze, 0, 0, "", paths);
        
        // Output all possible paths
        System.out.println("All possible paths:");
        for (String path : paths) {
            System.out.println(path);
        }
    }

    public static void printBoard(int[][] board){
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[i].length; j++){
                System.out.print(" " + board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * Recursive method to find all paths from the current position in the maze.
     * 
     * maze The maze represented as a 2D array (1 for open, 0 for blocked).
     * x The current row index of the rat.
     * y The current column index of the rat.
     * path The string representing the path taken so far (composed of 'R' and 'D').
     * paths The list that collects all valid paths from start to destination.
     */
    static void findPaths(int[][] maze, int x, int y, String path, List<String> paths) {
        // Check if the current position is the destination
        if (x == N - 1 && y == N - 1) {
            paths.add(path); // Add the current path to the list of paths
            return; // Exit the method since we found a valid path
        }
        
        // Check if the current cell is valid (within bounds and not blocked)
        if (isSafe(maze, x, y)) {
            // Mark the cell as visited by changing its value to 0
            maze[x][y] = 0;

            // Move right (to the next column)
            findPaths(maze, x, y + 1, path + "R", paths);
            // Move down (to the next row)
            findPaths(maze, x + 1, y, path + "D", paths);
            
            // Backtrack: unmark the cell to allow other paths to use it
            maze[x][y] = 1; // Mark the cell as unvisited
        }
    }

    static boolean isSafe(int[][] maze, int x, int y) {
        // Return true if the cell is within bounds and open (1)
        return (x >= 0 && x < N && y >= 0 && y < N && maze[x][y] == 1);
    }   
}   // end of program 4

// Solution to program 5
class ComboSum {
    public void run() {

        System.out.println("Test 1: Target 3");
        int[] test1 = {1, 2, 3};
        int sum1 = 3;
        CombinationSum(test1, sum1);

        System.out.println("\n\nTest 2: No possible solutions");
        int[] test2 = {7, 6, 9};
        int sum2 = 4;
        CombinationSum(test2, sum2);

        System.out.println("\n\nTest 3: Larger Array and sum");

        int[] test3 = {4, 2, 3, 8, 7};
        int sum3= 16;
        CombinationSum(test3, sum3);

    } // END driver main

    /* Requires an array of positive, non-zero integers WITHOUT repeating numbers */
    public static void CombinationSum (int[] array, int target) {
        ArrayList<Integer> path = new  ArrayList<Integer>();

        Arrays.sort(array);

        System.out.println("Solutions which add to " + target + ":");
        // index zero for start since all numbers are valid candidates
        CombinationSum(path, array, target, 0);
        return;
    } // END combination sum (caller)

    /* Helper: Difference starts as target, and has numbers gradually subtracted from it, until it reaches zero
     * (meaning a path has been found), it runs out of possible combinations, or the difference is negative, meaning
     * the path overshot the target.  */
    public static void CombinationSum (ArrayList<Integer> path, int[] array, int difference, int index) {
        if (difference == 0){
            // print path
            PrintPath(path);

            return;
        }

        for (int i = index; i < array.length; i++) {
            if (difference - array[i] >= 0){

                path.add(array[i]);

                // recursive call with next valid option
                CombinationSum(path, array, difference - array[i], i);

                // backtracks by popping the number added to the stack (undoing the add action)
                path.remove(path.size() - 1);

            }
        }
    } // END combination sum

    public static void PrintPath(ArrayList<Integer> path) {
        int i;
        for (i = 0; i < path.size() - 1; i++) {
            System.out.print(path.get(i) + " + ");
        }
        System.out.print(path.get(i));
        System.out.println();
    }
} // end of program 5