package GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import game1024.Cell;
import game1024.GameStatus;
import game1024.NumberGame;
import game1024.NumberSlider;
import game1024.SlideDirection;

import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JEditorPane;
import javax.swing.JTable;
import java.awt.Color;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EmptyStackException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.FlowLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.Dimension;

/*****************************************************************
Creates the GUI for the 1024 game

@author Colton Scranton
@version Winter 2017
*****************************************************************/
public class JFrame extends javax.swing.JFrame implements KeyListener{
	
	/* creates the game */
    private NumberSlider game;
    
	/* creates the grid */
    private int[][] grid;
    
	/* creates the panel */
	private JPanel contentPane;
	
	/* creates the rows and columns */
	private int rows = 4;	
	private int cols = 4;
	private int rows2 = 4;	
	private int cols2 = 4;
	
	/* Creates winning values */
	private int winningVal = 1024;
	
	/* Creates the tiles */
	private JTextField[][] squares = new JTextField[rows][cols];
    JTextField[][] squares2 = new JTextField[20][20];
    
	/* creates the wins and plays counters */
	private int numberOfWins = 0;
	private int numberOfPlays = 0;
	
	/* creates the timer value */
	private int secondsToWait = 60;
	
	/* creates the container for the tile values */
	private String number;
	
	/* creates a panel */
	JPanel panel = new JPanel();
	
	/* creates the menus, submenus and menu items */
	JMenuBar menuBar;
	JMenu menu, submenu;
	JMenuItem menuItem;
	
	/* creates the south panel */
	private JPanel panel_1;
	
	/* creates the statistic text feilds */
	private JTextField txtWinPercentage;
	private JTextField txtSecondsLeft;
	

	public static void main(String[] args) {
		/**
		 * Launch the application.
		 */
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame frame = new JFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
	

    private void renderBoard() {
        /**
         * Puts the current gameboard inside of a 2D array
         *
         */
    	
        /* reset all the 2D array elements to ZERO */
        for (int k = 0; k < grid.length; k++)
            for (int m = 0; m < grid[k].length; m++)
                grid[k][m] = 0;
        /* fill in the 2D array using information for non-empty tiles */
        for (Cell c : game.getNonEmptyTiles())
            grid[c.row][c.column] = c.value;
    }
	
	public JFrame() throws InterruptedException {
		/**
		 * Create the frame.
		 * @throws InterruptedException 
		 */
        
		/* creates keylistener */
		this.addKeyListener(this);
		
		/* starts new game */
	    game = new NumberGame();
	    
		/* sets the board size and winning value */
        game.resizeBoard(rows, cols, winningVal);
        
    	/* sets the grid size */
        grid = new int[rows][cols];
        
    	/* places 2 values and renders the game board */
        game.placeRandomValue();
        game.placeRandomValue();
        renderBoard();

    	/* sets the size of the GUI */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 450);
		
		/* sets up the panel */
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		/* sets dementions for the center panel */
		panel.setMaximumSize(new Dimension(400, 400));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

		/* sets the style for the panel */
		panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(panel, BorderLayout.CENTER);
		
		/* puts a gridlayout in the panel */
		panel.setLayout(new GridLayout(rows, cols, 0, 0));
		
		/* creates the southern panel */
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		/* creates the textbox that holds the winning percent */
		txtWinPercentage = new JTextField();
		txtWinPercentage.setBackground(Color.WHITE);
		txtWinPercentage.setHorizontalAlignment(SwingConstants.CENTER);
		txtWinPercentage.setDisabledTextColor(Color.BLACK);
		txtWinPercentage.setForeground(Color.BLACK);
		txtWinPercentage.setEnabled(false);
		txtWinPercentage.setEditable(false);
		txtWinPercentage.setText("Win Percentage: 0/0");
		panel_1.add(txtWinPercentage);
		txtWinPercentage.setColumns(15);
		
		/* creates the textbox that holds the timer */
		txtSecondsLeft = new JTextField();
		txtSecondsLeft.setDisabledTextColor(Color.BLACK);
		txtSecondsLeft.setEnabled(false);
		txtSecondsLeft.setEditable(false);
		txtSecondsLeft.setText("Seconds left: " + secondsToWait);
		txtSecondsLeft.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(txtSecondsLeft);
		txtSecondsLeft.setColumns(15);
		
		/* sets timer to run every second */
        int delay = 1000; //milliseconds
        
    	/* creates the listener with the timer */
        ActionListener taskPerformer = new ActionListener() {
        	
        public void actionPerformed(ActionEvent evt) {
        	/**
        	* Counts down the seconds on GUI and forces fail state if
        	* the timer runs to zero
        	*
            */
                
        	/* drops the timer by a second */
	        secondsToWait--;
	        	
	        /* updates the text */
	    	txtSecondsLeft.setText("Seconds left: " + secondsToWait);
	    		
	    	/* checks if the timer is at 0 */
	        if (secondsToWait == 0) {
	            	
	            /* sets timer to 60 */
	           	secondsToWait = 60;
	            	
	           	/* resets the game state*/
	           	game.reset();
	            renderBoard();
	            numberOfPlays++;
	                
	           	/* updates the winning percent */
	       		txtWinPercentage.setText("Win Percentage: " + numberOfWins + "/" + numberOfPlays);
	                
	       		/* adds text to the game tiles */
           		for (int i = 0; i < rows; i++){
            			
            		for (int j = 0; j < cols; j++){
            				
           				number = String.valueOf(grid[i][j]);
           				
            			if (grid[i][j] != 0){
            					squares[i][j].setText(number);
            			}
           				else{
            					squares[i][j].setText("");
           				}
           			}
            			
           		}
            		
           		/* shows losing message */
	           	JOptionPane.showMessageDialog(null, "You Lose.");
	           }
	           }
	       };
            
	    /* sets timer */
        new Timer(delay, taskPerformer).start();
		
    	/* sets up the starting tiles */
		for (int i = 0; i < rows; i++){
			
			for (int j = 0; j < cols; j++){
				
				/* converts numbers to string */
				number = String.valueOf(grid[i][j]);
				
				/* sets up tiles */
				squares[i][j] = new JTextField();
				squares[i][j].setFont(new Font("Tahoma", Font.BOLD, 32));
				squares[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				
				/* checks if value should be on tile and places it */
				if (grid[i][j] != 0){
					squares[i][j].setText(number);
				}
				else{
					squares[i][j].setText("");
				}
				
				squares[i][j].setForeground(Color.BLACK);
				squares[i][j].setFocusable(false);
				squares[i][j].setColumns(1);
				
				/* adds tile to panel */
				panel.add(squares[i][j]);
				
			}
			
		}
		
		/* creates menu bar */
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		/* sets up main menu */
		menu = new JMenu("Menu");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);

		/* creates the reset menu item */
		menuItem = new JMenuItem("Reset",
		                         KeyEvent.VK_T);
		
		/* creates menu shortcut */
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_1, ActionEvent.ALT_MASK));
		
		menuItem.addActionListener((ActionEvent event) -> {
		
		/* resets game state */
    	game.reset();
        renderBoard();
        numberOfPlays++;
        
    	/* updates win percentage */
		txtWinPercentage.setText("Win Percentage: " + numberOfWins + "/" + numberOfPlays);
		
		/* updates tiles */
		for (int i = 0; i < rows; i++){
			
			for (int j = 0; j < cols; j++){
				
				number = String.valueOf(grid[i][j]);
				
				if (grid[i][j] != 0){
					squares[i][j].setText(number);
				}
				else{
					squares[i][j].setText("");
				}
			}
			
		}
		});
		
		/* adds second menu item */
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Undo",
                KeyEvent.VK_T);
		
		/* sets menu shortcut */
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_2, ActionEvent.ALT_MASK));

		menuItem.addActionListener((ActionEvent event) -> {
			
			/* checks if stack is empty */
            try {
            	
            	/* undoes last turn */
                game.undo();
                renderBoard();
                
            } catch (IllegalStateException exp) {
            	
            	/* throws message is stack is empty */
            	JOptionPane.showMessageDialog(null, "Can't Undo Further");
            }
            
        	/* resets tiles */
			for (int i = 0; i < rows; i++){

				for (int j = 0; j < cols; j++){

					number = String.valueOf(grid[i][j]);

					if (grid[i][j] != 0){
						squares[i][j].setText(number);
					}
					else{
						squares[i][j].setText("");
					}
				}

			}
		});
		
		/* adds third menu item */
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Resize Board",
                KeyEvent.VK_T);
		
		/* creates menu shortcut */
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_3, ActionEvent.ALT_MASK));

		menuItem.addActionListener((ActionEvent event) -> {
			
		/* resets game state */
	    game.reset();
	    renderBoard();
	    numberOfPlays++;
	    txtWinPercentage.setText("Win Percentage: " + numberOfWins + "/" + numberOfPlays);
			
	    /* creates messages that take in input */
	    String test1= JOptionPane.showInputDialog("Please input number of rows: ");
	    String test2= JOptionPane.showInputDialog("Please input number of colums: ");
	        
	    /* puts input in integers */
	    rows2 = Integer.parseInt(test1);
	    cols2 = Integer.parseInt(test2);
	        
	    /* resizes grid */
	    grid = new int[rows2][cols2];
	        
	    /* resets game state */
	    game.resizeBoard(rows2, cols2, winningVal);
	    game.reset();
	    renderBoard();
	        
	    /* creates secondary tiles to add */
	    JTextField[][] squares2 = new JTextField[rows2][cols2];
	        
	    	/* adds secondary tiles to panel */
			for (int i = rows; i < rows2 ; i++){

				for (int j = cols; j < cols2; j++){

					number = String.valueOf(grid[i][j]);
						
					squares2[i][j] = new JTextField();
					squares2[i][j].setFont(new Font("Tahoma", Font.BOLD, 32));
					squares2[i][j].setHorizontalAlignment(SwingConstants.CENTER);
					if (grid[i][j] != 0){
						squares2[i][j].setText(number);
					}
					else{
						squares2[i][j].setText("");
					}
					squares2[i][j].setForeground(Color.BLACK);
					squares2[i][j].setFocusable(false);
					squares2[i][j].setColumns(1);
					panel.add(squares2[i][j]);
					panel.revalidate();
					panel.repaint();
				}

			}
		});
		
		/* adds item to menu */
		menu.add(menuItem);
		
	}
			
	
    /**
     * Listens for the directions keys to slide the values on the 
     * game board
     *
     */
	public void keyPressed(KeyEvent e) {
				
				/* listens for right key input */
		        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		        	
		        	/* slides direction to the right */
                    game.slide(SlideDirection.RIGHT);
                    renderBoard();
                   
                	/* sets the grid values into the tile */
            		for (int i = 0; i < rows2; i++){
            			
            			for (int j = 0; j < cols2; j++){
            				
            				/* converts tile value into string */
            				number = String.valueOf(grid[i][j]);
            				
            				if (grid[i][j] != 0 && j < 5 && i< 5){
            					squares[i][j].setText(number);
            				}
            				else if (j < 5 && i < 5){
            					squares[i][j].setText("");
            				}
            				else if (grid[i][j] != 0){
            					squares2[i][j].setText(number);
            				}
            				else{
            					squares2[i][j].setText("");
            				}
            			}
            			
            		}
            		
            		
		        }
		        
				/* listens for left key input */
		        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.slide(SlideDirection.LEFT);
                    renderBoard();
                   
            		for (int i = 0; i < rows; i++){
            			
            			for (int j = 0; j < cols; j++){
            				
            				/* converts tile value into string */
            				number = String.valueOf(grid[i][j]);
            				
            				if (grid[i][j] != 0 && j < 5 && i< 5){
            					squares[i][j].setText(number);
            				}
            				else if (j < 5 && i < 5){
            					squares[i][j].setText("");
            				}
            				else if (grid[i][j] != 0){
            					squares2[i][j].setText(number);
            				}
            				else{
            					squares2[i][j].setText("");
            				}
            			}
            			
            		}
            		
            		
		        }
		        
				/* listens for up key input */
		        if (e.getKeyCode() == KeyEvent.VK_UP) {
                    game.slide(SlideDirection.UP);
                    renderBoard();
                   
            		for (int i = 0; i < rows; i++){
            			
            			for (int j = 0; j < cols; j++){
            				
            				/* converts tile value into string */
            				number = String.valueOf(grid[i][j]);
            				
            				if (grid[i][j] != 0 && j < 5 && i< 5){
            					squares[i][j].setText(number);
            				}
            				else if (j < 5 && i < 5){
            					squares[i][j].setText("");
            				}
            				else if (grid[i][j] != 0){
            					squares2[i][j].setText(number);
            				}
            				else{
            					squares2[i][j].setText("");
            				}
            			}
            			
            		}
            		
            		
		        }
		        
				/* listens for down key input */
		        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    game.slide(SlideDirection.DOWN);
                    renderBoard();
                   
            		for (int i = 0; i < rows; i++){
            			
            			for (int j = 0; j < cols; j++){
            				
            				/* converts tile value into string */
            				number = String.valueOf(grid[i][j]);
            				
            				if (grid[i][j] != 0 && j < 5 && i< 5){
            					squares[i][j].setText(number);
            				}
            				else if (j < 5 && i < 5){
            					squares[i][j].setText("");
            				}
            				else if (grid[i][j] != 0){
            					squares2[i][j].setText(number);
            				}
            				else{
            					squares2[i][j].setText("");
            				}
            			}
            			
            		}
            		
            		
		        }
		        
				/* check if the user won the game */
	            if (game.getStatus() == GameStatus.USER_WON){
	            	
					/* resets the game state */
	            	game.reset();
	                renderBoard();
	                numberOfWins++;
	                numberOfPlays++;
	                
					/* resets the timer */
            		secondsToWait = 60;
            		
    				/* updates the win percantage */
	        		txtWinPercentage.setText("Win Percentage: " + numberOfWins + "/" + numberOfPlays);
	                
					/* updates game tiles */
            		for (int i = 0; i < rows; i++){
            			
            			for (int j = 0; j < cols; j++){
            				
            				number = String.valueOf(grid[i][j]);
            				
            				if (grid[i][j] != 0){
            					squares[i][j].setText(number);
            				}
            				else{
            					squares[i][j].setText("");
            				}
            			}
            			
            		}
	                
    				/* Prints winning message */
	            	JOptionPane.showMessageDialog(null, "You Win!");
	            }
	            
				/* checks if user lost */
	            if (game.getStatus() == GameStatus.USER_LOST){
	            	
					/* resets game state */
	            	game.reset();
	                renderBoard();
	                numberOfPlays++;
	        		txtWinPercentage.setText("Win Percentage: " + numberOfWins + "/" + numberOfPlays);
	                
					/* updates the game tiles */
            		for (int i = 0; i < rows; i++){
            			
            			for (int j = 0; j < cols; j++){
            				
            				number = String.valueOf(grid[i][j]);
            				
            				if (grid[i][j] != 0){
            					squares[i][j].setText(number);
            				}
            				else{
            					squares[i][j].setText("");
            				}
            			}
            			
            		}
            		
    				/* resets timer */
            		secondsToWait = 60;
            		
    				/* prints the losing message */
	            	JOptionPane.showMessageDialog(null, "You Lose");
	            }
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		
	}


