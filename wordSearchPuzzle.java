/*
 * High-Level Description: An explanation of how recursive backtracking will be used to solve the problem.
 * Model: A description of the state that is passed between recursive calls.
 * Java Source Code: The implementation that solves the problem using recursive backtracking.
 * Testing Program: A Java program that tests the solver.
 * Inputs and Outputs: Example inputs to the solver and the expected output.
 */

 // High-Level Description:
 /*
    Problem Statement: Given a 2D board of characters and a word, check if the word can be constructed from letters of 
sequentially adjacent cells (horizontal or vertical) without revisiting the same cell.
    Objective: Determine if the word exists on the board and return true/false.

  * start at first letter of the word, search through the grid until the first letter is found
  * search for next letter with a for loop in all 8 directions (up, down, left, right, and diagonals)
  * col+1(down), col-1(up), row+1(right), row-1(left)
  * if found next letter, continue in same direction: pass in word, indexWord+1, row, col
  * if not found that means the original grid point was the incorrect starting point...
  * ..backtrack to original row and col, then increment to next time the letter is found on the grid.
  * to check directions can make array [0, 1, -1] and double for loop through it and add array[i] to col, arr[j] to row
  * do not need to check 8 directions.

  */

  public class wordSearchPuzzle
  {
    public static void main(String[] args)
    {
      //Create a board to test with, find one on google/online
      char[][] board = //Board from Thompson's Teachings - - - Amanda Thompson on TPT
      {{'P', 'S', 'U', 'Z', 'Z', 'S', 'C', 'S'}, 
      {'L', 'K', 'T', 'W', 'R', 'L', 'O', 'E'},
      {'T', 'I', 'G', 'R', 'J', 'I', 'R', 'E'},
      {'M', 'N', 'L', 'Z', 'E', 'C', 'E', 'D'},
      {'C', 'B', 'U', 'S', 'H', 'E', 'L', 'S'}};
      //Tree, Core, Slice, Bushel, Skin, Seeds
      String findStr1 = "BUSHEL"; //Horizontal Check
      String findStr2 = "SKIN";   //Vertical Check

      boolean wordInBoard = wordSearch(board, findStr1);

      System.out.println("Word "+findStr1+" is in the board? " +wordInBoard);
    }
  }

  public static boolean wordSearch(char[][] board, String word)
  {
    // This function will search through the board until it finds a char that
    //matches the first letter of the word we are looking for, then calls helpers to do the rest
    int[] dx = {0, 1, 0, -1};
    int[] dy = {1, 0, -1, 0};
    char firstChar = word.charAt(0);
    boolean result = false;

    for(int row = 0; row < board.length; row++)
    {
      for(int col = 0; col < board[0].length; col++)
      {
        if(board[row][col] == firstChar)
        {
          result = wordSearchHelper(board, word, row, col);
        } 
      }
    }
    return result;
  }
  public static boolean wordSearchHelper(char[][] board, String word, int row, int col)
  {
    return false;
  }
  public static boolean completeWord(char[][] board, String word, int row, int col, int dirX, int dirY)
  {
    if(row == 0 && dirY == -1) return false;
    if(col == 0 && dirX == -1) return false;
    if(row == board.length-1 && dirY == 1) return false;
    if(col == board[0].length-1 && dirX == 1) return false;
    if(word.length() == 0) return true;
  }
