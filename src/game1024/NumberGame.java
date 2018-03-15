package game1024;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

/*****************************************************************
Creates the game engine for the 1024 game. Allows movement, changing
of board size and adding random numbers.

@author Colton Scranton
@version Winter 2017
*****************************************************************/

public class NumberGame  implements NumberSlider {
	
	/* Number of boards rows */
	public int height;
	
	/* Number of boards column */
	public int width;
	
	/* Sets games winning value */
	public int winningVal = 1048;
	
	/* Creates board */
	int[][] board = new int[4][4];
	
	/* Creates stack to save game state */
	Stack<int[][]> gameState = new Stack<int[][]>();
	
	/* Sets game status */
	GameStatus gameStatus = GameStatus.IN_PROGRESS;

	public static void main(String[] args) {
		
	}
	

	@Override
	public void resizeBoard(int height, int width, int winningValue) {
	    /**
	     * Reset the game logic to handle a board of a given dimension
	     *
	     * @param height the number of rows in the board
	     * @param width the number of columns in the board
	     * @param winningValue the value that must appear on the board to
	     *                     win the game
	     * @throws IllegalArgumentException when the winning value is not power of two
	     *  or negative
	     */
			
		/* Puts board data into a temp array */
		int[][] data = new int[height][width];
			
		/* Sets board equal to the temp array and sets the winning value */
		board = data;
		winningVal = winningValue;
		
		
		/* Checks to see if winning value is less than 0 or not divisable by two */
		if (winningVal < 2 || (winningVal & (winningVal - 1)) != 0){
				
		throw new IllegalArgumentException();
			
		}
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
	    /**
	     * Remove all numbered tiles from the board and place
	     * TWO non-zero values at random location
	     * 
	     * @param check trigger if spot is empty
	     * @param r, r2 random number for a row
	     * @param c, c2 random number for a column
	     * @param n random number for values exponent
	     * @param result, result2 value to be placed on board
	     */
		/* Sets game status to in progress */
		gameStatus = GameStatus.IN_PROGRESS;
		
		/* Clears the game data */
		gameState.clear();
		
		/* Sets all spot on board to 0 */
		for (int i = 0; i < board.length; i++) {
	        for (int j = 0; j < board[i].length; j++) {
	        	
	            board[i][j] = 0;  
	            
	        }
		}
		
		Random rand = new Random();
		
		/* Sets check trigger to 0 */
		int check = 0;
		
		/* Creates random numbers for rows, columns, and exponents */
		int  r = rand.nextInt((board.length + 1) - 1);
		int  c = rand.nextInt((board[0].length + 1) - 1);
		int  n = rand.nextInt((4 - 1) + 1) + 1;
		
		/* Set value of 2 to the power of n */
		int result = (int)Math.pow(2, n);
		
		/* Put value onto random spot on the board */
		board[r][c] = result;
		
		/* Creates random numbers for rows, columns, and exponents for the second number*/
		int  r2 = rand.nextInt((board.length + 1) - 1);
		int  c2 = rand.nextInt((board[0].length + 1) - 1);
		int  n2 = rand.nextInt((4 - 1) + 1) + 1;
		
		/* Set value of 2 to the power of n */
		int result2 = (int)Math.pow(2, n2);
		
		/* Checks to see if 2nd values been placed  */
		while (check == 0){
			if (r == r2 && c == c2){
				
				/* If the second random number is on the same tile as the first, the second number finds a new spot */
				  r2 = rand.nextInt(board.length) + 1;
				  c2 = rand.nextInt(board[0].length) + 1;
				  
			}
			else{
				
				/* Places second random number on the board */
				board[r2][c2] = result2;
				
				/* Exits the while loop */
				check = 1;
				
			}
		}
		
		
		
	}

	@Override
	public void setValues(int[][] ref) {
		// TODO Auto-generated method stub
	    /**
	     * Set the game board to the desired values given in the 2D array.
	     * This method should use nested loops to copy each element from the
	     * provided array to your own internal array. Do not just assign the
	     * entire array object to your internal array object. Otherwise, your
	     * internal array may get corrupted by the array used in the JUnit
	     * test file. This method is mainly used by the JUnit tester.
	     * @param ref
	     */
		
		for (int i = 0; i < board.length; i++) {	
	        for (int j = 0; j < board[i].length; j++) {
	        	
	        	/* Sets the location on reference board to current board */
	            board[i][j] = ref[i][j]; 
	            
	        }
		}
	}

	@Override
	public Cell placeRandomValue() {
		// TODO Auto-generated method stub
	    /**
	     * Insert one random tile into an empty spot on the board.
	     *
	     * @return a Cell object with its row, column, and value attributes
	     *  initialized properly
	     *  
	     * @param empty number of nonempty spots
	     * @param r, r2 random number for a row
	     * @param c, c2 random number for a column
	     * @param n random number for values exponent
	     * @param result, result2 value to be placed on board
	     *
	     * @throws IllegalStateException when the board has no empty cell
	     */
		
		/* Sets empty to 0 */
		int empty = 0;
		
		/* Search through the board and if any empty spots are found they're added to empty */
		for (int i = 0; i < board.length; i++) {
	        for (int j = 0; j < board[i].length; j++) {
	            if (board[i][j] == 0){
	            	
	            	empty += 1;
	            	
	            }
	        }
		}
		
		/* If there are no empty spots on the board an exception is thrown */
		if (empty == 0){
			
			throw new IllegalStateException();
			
		}
        
		Random rand = new Random();
		
		/* Sets check trigger to 0 */
		int check = 0;
		
		/* Creates random numbers for rows, columns, and exponents */
		int  r = rand.nextInt((board.length + 1) - 1);
		int  c = rand.nextInt((board[0].length + 1) - 1);
		int  n = rand.nextInt((4 - 1) + 1) + 1;
		
		/* Set value of 2 to the power of n */
		int result = (int)Math.pow(2, n);
		
		/* Creates an array list of cells */
		ArrayList<Cell> list = new ArrayList<Cell>();
		
		while(check == 0){		 
			if (board[r][c] != 0){
				
				/* If the random number is on the same tile as another, the random number finds a new spot */
				r = rand.nextInt((board.length + 1) - 1);
				c = rand.nextInt((board[0].length + 1) - 1);
				
			}
			else {
				
				/* Places second random number on the board */
				board[r][c] = result;
				
				/* Exits the loop */
				check = 1;
				

			}
		
		}
		
		/* Returns the value of the location and value in a cell */
		return new Cell(r,c,board[r][c]);
	}
	
    private void pushLeft() {
	    /**
	     * move tiles with values as far left as possible
	     *
	     * @param row creates an array list that holds a boards row 
	     * @param current provides a counter to vairables in the row 
	     */
    	
        for (int i = 0; i < board.length; i++) {
        		
        		/* Creates row array and sets length to the current board length */
                int[] row = new int[board[i].length];
                
                for (int j = 0; j < board[i].length; j++) {
                        if (board[i][j] != 0) {
                        		
                        		/* If the board at the current spot is not 0, it sets current to 0 */
                                int current = 0;
                                
                                /* Cylces through the row at current unitl it's not equal to 0 */
                                while(row[current] != 0) {
                                		
                                		/* Adds one to current every cycle */
                                        current = current + 1;
                                        
                                }
                                
                                /* Put the value of the board into the row at current */
                                row[current] = board[i][j];
                        }
                }
                
                /* Sets the rows on the board equal to the row array */
                board[i] = row;
        }
        
    }
    
    
    private void rotateCW() {
	    /**
	     * Rotates the current board clockwise
	     *
	     * @param rotated is a replica of the current board
	     */
    	
    	/* Creates a copy the size of current board */
        int[][] rotated = new int[board.length][board[0].length];
        
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
            	
            	/* Cycles through the board and rotates the rows and columns */
                rotated[c][board[r].length - 1 - r] = board[r][c];
                
            }
            
        }
        
        /* Sets the rotated board as the actual board */
        board = rotated;
    }
    

    private void rotateCCW() {
    		/**
    		 * Rotates the current board counter clockwise
    		 *
    		 * @param rotated is a replica of the current board
    		 */
    		
    		/* Creates a copy the size of current board */
            int[][] rotated = new int[board.length][board[0].length];
            
            for (int r = 0; r < board.length; r++) {	
                    for (int c = 0; c < board[r].length; c++) {
                    		
                    		/* Cycles through the board and rotates the rows and columns */
                            rotated[board[r].length - 1 - c][r] = board[r][c];
                            
                    }
            }
            
            /* Sets the rotated board as the actual board */
            board = rotated;
    }
    
    private static int[][] deepCopy(int[][] board) {
	    /**
	     * Allows for an exact copy of a 2d array into another
	     *
	     * @param board the board to base the copy off
	     * @param copy a replica of board\
	     * 
	     * @return copy of the selected board
	     */
    	
        /* Creates a copy of the size of the selected board */
        int[][] copy = new int[board.length][board[0].length];
        
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
            	
                /* Copies the values from the selected board into the copy */
                copy[i][j] = board[i][j];
                
            }
            
        }
        
        return copy;
    }

	@Override
	public boolean slide(SlideDirection dir) {
		// TODO Auto-generated method stub
	    /**
	     * Slide all the tiles in the board in the requested direction
	     * 
	     * @param dir move direction of the tiles
	     * @param before a copy of the current board
	     *
	     * @return true when the board changes
	     */
    	
        /* Puts the current board into the stack */
		gameState.push(board);
		
        /* Creates a copy of the current board */
        int[][] before = deepCopy(board);
        
        /* Executes if the direction is left */
        if (dir == SlideDirection.LEFT) {
        	
            /* Pushed values left on the board */
            pushLeft();
            
            for (int i = 0; i < board.length; i++) {
                for (int j = 1; j < board[i].length; j++) {
 
                    /* Checks if the value to the left is the same as the current */
                    if (board[i][j-1] == board[i][j]) {
                    	
                        /* Doubles the value of the left value */
                        board[i][j-1] *= 2;
                        
                        /* Makes the current value 0 */
                        board[i][j] = 0;
                        
                    }
                }
            }
            
            /* Pushes left again and gets rid of anymore 0s in the way */
            pushLeft();
     
        }
        
        /* Executes is direction is up */
        else if (dir == SlideDirection.UP) {
        	
            /* Rotates the board so it's facing left, then slides values and rotates back */
            rotateCCW();
            slide(SlideDirection.LEFT);
            rotateCW();
            
        }
        
        /* Executes is direction is right */
        else if (dir == SlideDirection.RIGHT) {
        	
            /* Rotates the board so it's facing left, then slides values and rotates back */
            rotateCCW();
            rotateCCW();
            slide(SlideDirection.LEFT);
            rotateCW();
            rotateCW();
            
        }
        
        /* Executes is direction is down */
        else if (dir == SlideDirection.DOWN) {
        	
            /* Rotates the board so it's facing left, then slides values and rotates back */
            rotateCW();
            slide(SlideDirection.LEFT);
            rotateCCW();
            
        }
        
        /* Checks to see if the board changes */
        if (java.util.Arrays.deepEquals(before, board) == false){
    		
        	if (dir == SlideDirection.LEFT){
        		
                /* Places random value if the board has changed */
        		placeRandomValue();
        			
        	}	
    		
        	return true;
        
        }
        
        /* Executes if the board did not change */
        else{
        	
        	return false;
        	
        }
           
    }

	@Override
	public ArrayList<Cell> getNonEmptyTiles() {
	    /**
	     * @param list an array list of cells 
	     *
	     * @return an arraylist of Cells. Each cell holds the (row,column) and
	     * value of a tile
	     */
		
        /* Creats an array list of cells */
		ArrayList<Cell> list = new ArrayList<Cell>();
		
		for (int i = 0; i < board.length; i++) {
	        for (int j = 0; j < board[i].length; j++) {
	        	
	            /* If the boards location holds a value, add it too list */
	        	if (board[i][j] != 0){
	        		
	    		list.add( new Cell(i,j,board[i][j]));
	    		
	        	}
	        }
		}
		
		return list;
	}

	@Override
	public GameStatus getStatus() {
	    /**
	     * Return the current state of the game
	     * 
	     * @param empty counts nonempty spots on the board
	     * @param possible counts possible moves available 
	     * 
	     * @return one of the possible values of GameStatus enum
	     */
		
        /* Sets empty to 0 */
		int empty = 0;
		
		for (int i = 0; i < board.length; i++) {
	        for (int j = 0; j < board[i].length; j++) {
	        	
	            /* If the board has an empty spot it adds values to empty */
	            if (board[i][j] == 0){
	            	
	            	empty += 1;
	            	
	            }
	            
	            /* Checks if the winning value is on the board */
	            else if (board[i][j] == winningVal){
	            	
	            	return GameStatus.USER_WON;
	            	
	            }
	        }
		}
		
		if (empty == 0){
			int possible = 0;
			for (int i = 1; i < board.length - 1; i++) {
		        for (int j = 1; j < board[i].length - 1; j++) {
		        	
		            /* Checks for possible moves if the board is full */
		            if (board[i][j] == board[i - 1][j] || board[i][j] == board[i + 1][j] 
		            		|| board[i][j] == board[i][j + 1] || board[i][j] == board[i][j - 1]){
		            	
		                /* Adds to possible if moves are available */
		            	possible += 1;
		            	
		            }
		        }
			}
			
	        /* Checks to see if there are possible moves */
			if (possible == 0){
				
			return GameStatus.USER_LOST;
			
			}
		}
		
        /* Returns in progress if the game is not lost or won */
		return GameStatus.IN_PROGRESS;
	}

	@Override
	public void undo() {
	    /**
	     * Undo the most recent action, i.e. restore the board to its previous
	     * state. Calling this method multiple times will ultimately restore
	     * the gam to the very first initial state of the board holding two
	     * random values. Further attempt to undo beyond this state will throw
	     * an IllegalStateException.
	     *
	     * @throws IllegalStateException when undo is not possible
	     */
		try{
			
	        /* Pulls a copy of the board from the stack */
			board = gameState.pop();
			
		}
		
        /* Throws exception if the stack is empty */
		catch (EmptyStackException e) {
			
			throw new IllegalStateException(e);
			
		}
		
	}

}
