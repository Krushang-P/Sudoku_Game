/*
 * Import statements for the program
 */
import java.util.Random;

import javax.swing.JButton;

/**
 * 
 * @author Krushang Patel <041-021-848>
 * @author Karnav Panchal <041-022-780 >
 * @version 1.8.0
 * @see default package
 * 
 * 			This is the model class of the game results in the 
 * 			functionality of the rules and regulation of the 
 * 			game.
 *
 *
 */
public class GameModel {

	/**
	 * Variables for Storing te values of the board of the game
	 */
	private int[][] sudokuBoard;
	private int[][] sudokuSolution;
	private int length;
	private int dimension;
	private int difficulty;
	
	/**
	 * Default constructor for the model class
	 */
	public GameModel() {
		
	}
	
	/**
	 * Parameterized constructor for this class
	 * 
	 * @param length - Takes the length of the array 
	 * @param difficulty - sets the difficulty
	 */
	public GameModel(int length, int difficulty) {
		this.length = length;
		this.difficulty = difficulty;
		Double dim = Math.sqrt(length);
		dimension = dim.intValue();
		sudokuBoard = new int[length][length];
	}
	
	/**
	 * Checks the row if the number is repeating or not
	 * @param i - Temporary variable for loop
	 * @param num - For checking the validity
	 * @return false if not validates 
	 */

	public boolean checkInRow(int i, int num) {
		for (int j = 0; j < length; j++) {
			if (sudokuBoard[i][j] == num) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks the column if the number is repeating or not
	 * @param j -  Temporary variable for loop
	 * @param num - For checking the validity
	 * @return - false if not validates 
	 */
	public boolean checkInCol(int j, int num) {
		for (int i = 0; i < length; i++) {
			if (sudokuBoard[i][j] == num) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks the inner grid if the number is repeating or not
	 * @param rowStart - Checking the row 
	 * @param colStart - Checking the column 
	 * @param num - Validating the sudoku board
	 * @return - false if not validates 
	 */
	public boolean checkInBox(int rowStart, int colStart, int num) {
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				if (sudokuBoard[rowStart + i][colStart + j] == num) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the number will generate and fits in the 
	 * particular spot or not 
	 * @param i - temporary variable for loop
	 * @param j - temporary variable for loop 
	 * @param num - number
	 * @return checkIfSafe checks col, row 
	 */
	public boolean checkIfSafe(int i, int j, int num) {
		return (checkInRow(i, num) && checkInCol(j, num) && checkInBox(i - i % dimension, j - j % dimension, num));
	}

	/**
	 * Generates random numbers for the board
	 * @param num - number 
	 * @return random numbers 
	 */
	public int randomGenerator(int num) {
		return (int) Math.floor((Math.random() * num + 1));
	}

	/**
	 * Sets the number in the grids of the boards
	 * @param row - sets the number in the row 
	 * @param col - sets the number in the column
	 */
	public void fillBox(int row, int col) {
		int num;
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				do {
					num = randomGenerator(length);
				} while (!checkInBox(row, col, num));
				sudokuBoard[row + i][col + j] = num;
			}
		}
	}
	
	
	/**
	 * This void fills the diagonal box
	 */

	public void fillDiagonal() {
		for (int i = 0; i < length; i = i + dimension) {
			fillBox(i, i);
		}
	}

	/**
	 * Fill Remaining method fill rest of the empty spaces in the 
	 * game board
	 * @param i - local variable 
	 * @param j - local variable
	 * @return false if value doesnt match
	 */
	boolean fillRemaining(int i, int j) {
		if (j >= length && i < length - 1) {
			i = i + 1;
			j = 0;
		}
		if (i >= length && j >= length)
			return true;

		if (i < dimension) {
			if (j < dimension)
				j = dimension;
		} else if (i < length - dimension) {
			if (j == (int) (i / dimension) * dimension)
				j = j + dimension;
		} else {
			if (j == length - dimension) {
				i = i + 1;
				j = 0;
				if (i >= length)
					return true;
			}
		}

		for (int num = 1; num <= length; num++) {
			if (checkIfSafe(i, j, num)) {
				sudokuBoard[i][j] = num;
				if (fillRemaining(i, j + 1)) {
					return true;
				}
				sudokuBoard[i][j] = 0;
			}
		}
		return false;
	}

	/**
	 * This method will print the board on the actual 
	 * grid of the game.
	 */
	public void printSudokuFilled() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(sudokuBoard[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * This method will print unFilled 
	 * board 
	 */
	public void printSudokuUnFilled() {
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {
				System.out.print(sudokuSolution[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * Generates the filled board of the game 
	 */
	public void generateFilledBoard() {
		fillDiagonal();
		fillRemaining(0, dimension);
	}

	/**
	 * The method is responsible generating Empty board in the game
	 * @param array - stores the values of the board 
	 */
	public void generateEmptyBoard(int[][] array) {
		sudokuSolution = array;
		int count = difficulty;
		while (count != 0) {
			int gridPosition = randomGenerator(length * length) - 1;
			int i = (gridPosition / length);
			int j = gridPosition % length;
			if (j != 0)
				j = j - 1;
			if (sudokuSolution[i][j] != 0) {
				count--;
				sudokuSolution[i][j] = 0;
			}
		}
	}
	
	/**
	 * The method will return sudoku board
	 * @return - sudokuBoard game board 
	 */
	public int[][] getFilledBoard(){
		return sudokuBoard;
	}
	
	/**
	 * The method will return sudoku unfilledboard which is the solution 
	 * of the generatesd board
	 * @return - sudoku Solution
	 */
	public int[][] getUnFilledBoard(){
		return sudokuSolution;
	}
	
	/**
	 * This method is used to check the duplicate entry values for columns for
	 * validating sudoku rules in 2 dimensions (4 x 4 grid)
	 * 
	 * @param col - number of column in which check is progressing
	 * @param num - number whose duplicate is being checked for
	 * @return <true> if duplicate is found else returns <false>
	 */
	public boolean checkInCol2(int col, String num, JButton[][] button) {
		for (int i = 0; i < 4; i++) {
			if (button[i][col].getText() == num) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method is used to check the duplicate entry values for rows for
	 * validating sudoku rules rules in 2 dimensions (4 x 4 grid)
	 * 
	 * @param row - number of row in which check is progressing
	 * @param num - number whose duplicate is being checked for
	 * @return <true> if duplicate is found else returns <false>
	 */
	public boolean checkInRow2(int row, String num, JButton[][] button) {
		for (int i = 0; i < 4; i++) {
			if (button[row][i].getText() == num) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is used to check the duplicate entry values for a specific
	 * quadrant of 2 x 2 in a grid of 4 x 4.
	 * 
	 * @param num number whose duplicate is being checked for
	 * @param row number of row in a quadrant in which check is progressing
	 * @param col number of coumn in a quadrant in which check is progressing
	 * @return <true> if duplicate is found else returns <false>
	 */
	public boolean checkInBox2(String num, int row, int col, JButton[][] button) {
		int rowQuad = row - row % 2;
		int colQuad = col - col % 2;
		for (int i = rowQuad; i < rowQuad + 2; i++) {
			for (int j = colQuad; j < colQuad + 2; j++) {
				if (button[i][j].getText() == num) {
					return true;
				}
			}
		}
		return false;
	}
	

	/**
	 * This method is used to check the duplicate entry values for rows for
	 * validating sudoku rules rules in 3 dimensions (9 x 9 grid)
	 * 
	 * @param row - number of row in which check is progressing
	 * @param num - number whose duplicate is being checked for
	 * @return <true> if duplicate is found else returns <false>
	 */
	public boolean checkInRow3(int row, String num, JButton[][] button) {
		for (int i = 0; i < 9; i++) {
			if (button[row][i].getText() == num) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is used to check the duplicate entry values for columns for
	 * validating sudoku rules in 3 dimensions (9 x 9 grid)
	 * 
	 * @param col - number of column in which check is progressing
	 * @param num - number whose duplicate is being checked for
	 * @return <true> if duplicate is found else returns <false>
	 */
	public boolean checkInCol3(int col, String num, JButton[][] button) {
		for (int i = 0; i < 9; i++) {
			if (button[i][col].getText() == num) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method is used to check the duplicate entry values for a specific
	 * quadrant of 3 x 3 in a grid of 9 x 9.
	 * 
	 * @param num number whose duplicate is being checked for
	 * @param row number of row in a quadrant in which check is progressing
	 * @param col number of column in a quadrant in which check is progressing
	 * @return <true> if duplicate is found else returns <false>
	 */
	public boolean checkInBox3(String num, int row, int col, JButton[][] button) {
		int rowQuad = row - row % 3;
		int colQuad = col - col % 3;
		for (int i = rowQuad; i < rowQuad + 3; i++) {
			for (int j = colQuad; j < colQuad + 3; j++) {
				if (button[i][j].getText() == num) {
					return true;
				}
			}
		}
		return false;
	}
	

	/**
	 * This method is used to check the duplicate entry values for rows for
	 * validating sudoku rules rules in 4 dimensions (16 x 16 grid)
	 * 
	 * @param row - number of row in which check is progressing
	 * @param num - number whose duplicate is being checked for
	 * @return <true> if duplicate is found else returns <false>
	 */
	public boolean checkInRow4(int row, String num, JButton[][] button) {
		for (int i = 0; i < 16; i++) {
			if (button[row][i].getText() == num) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method is used to check the duplicate entry values for columns for
	 * validating sudoku rules in 3 dimensions (16 x 16 grid)
	 * 
	 * @param col - number of column in which check is progressing
	 * @param num - number whose duplicate is being checked for
	 * @return <true> if duplicate is found else returns <false>
	 */
	public boolean checkInCol4(int col, String num, JButton[][] button) {
		for (int i = 0; i < 16; i++) {
			if (button[i][col].getText() == num) {
				return true;
			}
		}
		return false;
	}



	

	/**
	 * This method is used to check the duplicate entry values for a specific
	 * quadrant of 4 x 4 in a grid of 16 x 16.
	 * 
	 * @param num number whose duplicate is being checked for
	 * @param row number of row in a quadrant in which check is progressing
	 * @param col number of column in a quadrant in which check is progressing
	 * @return <true> if duplicate is found else returns <false>
	 */
	public boolean checkInBox4(String num, int row, int col, JButton[][] button) {
		int rowQuad = row - row % 4;
		int colQuad = col - col % 4;
		for (int i = rowQuad; i < rowQuad + 4; i++) {
			for (int j = colQuad; j < colQuad + 4; j++) {
				if (button[i][j].getText() == num) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void getGameFromNetwork(String game) {

	}
	

	/**
	 * This is the main method
	 * @param args -arguments
	 */
	public static void main(String[] args) {

		
	}
}