
/* * * * * * * * * * * * * * * * * * * * * * *
 * Import statements for the program run * * *
 * * * * * * * * * * * * * * * * * * * * * * * 
 */
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Formatter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * 
 * @author Krushang Patel <041-021-848>
 * @author Karnav Panchal <041-022-780>
 * @version 1.8.0_301
 * @see default package
 * @since 1.8.0_301 (Compiler's Version)
 * 
 *        This is the controller class of the game. It contains the actions
 *        which are action when the user interacts with the graphics on the UI
 *        which is developed.
 * 
 */
public class GameView extends JFrame implements ActionListener {
	
	
	//vpatel
	JTextField netChat = new JTextField();
	
	
	/**
	 * This variable stores the dimension of sudoku grid being displayed.
	 */
	private int gridDimension;
	/**
	 * This variable stores the play value which user wants to set on the particular
	 * grid box while playing the game.
	 */
	private String action;
	/**
	 * This variable acts as a generic counter variable
	 */
	private int i = 0;
	/**
	 * This variable stores row and column value of the specific grid button on
	 * which user action is being performed.
	 */
	private int row, col;
	/**
	 * This variable stores a 4 x 4 Two - dimensional button arrays for the grid.
	 */
	private JButton[][] gridTwoDim = new JButton[4][4];
	/**
	 * This variable stores a 9 x 9 Three - dimensional button arrays for the grid.
	 */
	private JButton[][] gridThreeDim = new JButton[9][9];
	/**
	 * This variable stores a 16 x 16 Four - dimensional button arrays for the grid.
	 */
	private JButton[][] gridFourDim = new JButton[16][16];
	/**
	 * This variable stores a 4 x 4 Two - dimensional button arrays for the sudoku
	 * board.
	 */
	private String[][] arrayTwoDim = new String[4][4];
	/**
	 * This variable stores a 9 x 9 Three - dimensional button arrays for the sudoku
	 * board.
	 */
	private String[][] arrayThreeDim = new String[9][9];
	/**
	 * This variable stores a 16 x 16 Four - dimensional button arrays for the
	 * sudoku board.
	 */
	private String[][] arrayFourDim = new String[16][16];
	/**
	 * This variable stores one dimensional array displaying multiple valid values
	 * which can be used in the present grid of 4 x 4
	 */
	private JButton[] hintTwoDim = new JButton[4];
	/**
	 * This variable stores one dimensional array displaying multiple valid values
	 * which can be used in the present grid 9 x 9
	 */
	private JButton[] hintThreeDim = new JButton[9];
	/**
	 * This variable stores one dimensional array displaying multiple valid values
	 * which can be used in the present grid 16 x 16
	 */
	private JButton[] hintFourDim = new JButton[16];
	/**
	 * This variable stores one dimensional array containing multiple valid values
	 * which can be used in the present grid 4 x 4.
	 */
	private static final String numvaluesTwoDim[] = { "1", "2", "3", "4" };
	/**
	 * This variable stores one dimensional array containing multiple valid values
	 * which can be used in the present grid 9 x 9.
	 */
	private static final String numvaluesThreeDim[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	/**
	 * This variable stores one dimensional array containing multiple valid values
	 * which can be used in the present grid 16 x 16.
	 */
	private static final String numvaluesFourDim[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
			"E", "F", "G" };

	/**
	 * It stores the value of DELAY in the timer
	 */
	private static final int TIMER_DELAY = 50;
	/**
	 * It stores the starting time of the timer
	 */
	private LocalTime startTime;
	/**
	 * It stores the stop / end time of the timer.
	 */
	private LocalTime stopTime;
	/**
	 * It is used to display the progression of timer.
	 */
	private static JTextField timeField = new JTextField("00:00");
	/**
	 * Instance of a timer class
	 */
	private Timer timer = null;
	private GameController gameControl = new GameController();
	private GameModel gameModel = new GameModel();

	private int[][] gridTwoSolution = new int[4][4];
	private int[][] gridTwoBoard = new int[4][4];

	private int[][] gridThreeSolution = new int[9][9];
	private int[][] gridThreeBoard = new int[9][9];

	private int[][] gridFourSolution = new int[16][16];
	private int[][] gridFourBoard = new int[16][16];

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Color,Text and Border Definitions * * * * * * * * * * * * * * * * * * * * * *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	/**
	 * This variable defines customized R,G,B value for coloring menu panel for the
	 * UI
	 */
	private static final Color DANUBE = new Color(92, 148, 203);
	/**
	 * This variable defines customized R,G,B value for coloring hint panel for the
	 * UI
	 */
	private static final Color SWAMP = new Color(0, 28, 35);
	/**
	 * This variable defines customized R,G,B value for coloring customize panel for
	 * the UI
	 */
	private static final Color CLOUDBURST = new Color(34, 53, 81);
	/**
	 * This variable defines customized R,G,B value for coloring grid panel for the
	 * UI
	 */
	private static final Color CATSKILLWHITE = new Color(228, 235, 243);
	/**
	 * This variable defines customized R,G,B value for coloring grid panel for the
	 * UI
	 */
	private static final Color BOTTICELLI = new Color(204, 216, 232);
	/**
	 * This variable defines customized R,G,B value for coloring option panel for
	 * the UI
	 */
	private static final Color POLOBLUE = new Color(140, 164, 199);
	/**
	 * This variable defines customized font for general text in UI
	 */
	private static final Font TEXTFONT = new Font("Arial", Font.BOLD, 20);
	/**
	 * This variable defines customized font for user panel in UI
	 */
	private static final Font USERFONT = new Font("Arial", Font.ITALIC, 15);
	/**
	 * This variable defines customized line style for general border in UI
	 */
	private static final Border BLACKLINE = BorderFactory.createLineBorder(Color.black, 5);
	/**
	 * This variable defines customized line style for general grids in UI
	 */
	private static final Border GRIDLINE = BorderFactory.createLineBorder(CATSKILLWHITE, 1);
	/**
	 * This variable defines customized line style for menu panel in UI
	 */
	private static final Border MENULINE = BorderFactory.createLineBorder(CLOUDBURST, 1);
	/**
	 * This variable defines customized line style for game logo in UI
	 */
	private static final Border LOGOLINE = BorderFactory.createLineBorder(SWAMP, 1);
	/**
	 * This variable defines customized line style for hint panel in UI
	 */
	private static String saveValues[][];
	private static final Border HINTLINE = BorderFactory.createLineBorder(BOTTICELLI, 1);
	private static final ImageIcon NEW_ICON = createImageIcon("piciconnew.gif");
	private static final ImageIcon EXIT_ICON = createImageIcon("piciconext.gif");
	private static final ImageIcon SOLUTION_ICON = createImageIcon("piciconsol.gif");
	private static final ImageIcon ABOUT_ICON = createImageIcon("piciconabt.gif");
	private static final ImageIcon COLOR_ICON = createImageIcon("piciconcol.gif");

	/**
	 * Non - parameterized constructor for the class
	 */
	public GameView() {

	}

	/**
	 * Parameterized constructor for the class.
	 * 
	 * @param numberOfGrids - It contains the number of grids to be displayed in the
	 *                      grid panel of the present UI, from 2(4 x 4), 3 (9 x 9)
	 *                      and 4 (16 x 16).
	 * 
	 *                      The constructor has the UI definition for the game. It
	 *                      is done using JFrame and its components.
	 */
	public GameView(int numberOfGrids) {
		this.gridDimension = numberOfGrids;
		int duration = 500;
		SplashScreen splashWindow = new SplashScreen(duration);
		splashWindow.showSplashWindow();

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * * * * Frame Definition for Sudoku GUI * * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JFrame sudoku = new JFrame("Sudoku Karnav & Krushang - Summer 2022");
		sudoku.setPreferredSize(new Dimension(1070, 925));
		
		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * Menu Panel - It Sub Panel in Main Frame * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */

		JPanel menuPanel = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		JMenu menuGame = new JMenu("Game");
		JMenu menuHelp = new JMenu("Help");
		JMenu newGame = new JMenu("New");
	
		JMenu network = new JMenu("Network");
		JMenuItem connection = new JMenuItem("New Connection");
		JMenuItem disconnect = new JMenuItem("Disconnect");
	
		disconnect.setEnabled(false);
		
		JMenuItem newGame2 = new JMenuItem("2_Dim", NEW_ICON);
		JMenuItem newGame3 = new JMenuItem("3_Dim", NEW_ICON);
		JMenuItem newGame4 = new JMenuItem("4_Dim", NEW_ICON);
		JMenuItem gameSolution = new JMenuItem("Solution", SOLUTION_ICON);
		JMenuItem gameExit = new JMenuItem("Exit", EXIT_ICON);
		JMenuItem gameColor = new JMenuItem("Color", COLOR_ICON);
		JMenuItem aboutGame = new JMenuItem("About", ABOUT_ICON);

		JTextArea userInput = new JTextArea();
		userInput.setBorder(BLACKLINE);
		userInput.setFont(USERFONT);
		userInput.setEditable(false);
		userInput.setFocusable(true);
		
		menuPanel.setPreferredSize(new Dimension(1055, 40));
		menuPanel.setBackground(DANUBE);
		menuPanel.setBorder(BLACKLINE);

		menuBar.setPreferredSize(new Dimension(1035, 20));
		menuBar.setAlignmentY(TOP_ALIGNMENT);
		menuBar.setBorder(MENULINE);
		menuBar.setBackground(DANUBE);

		menuGame.setFont(new Font("Arial", Font.BOLD, 18));
		newGame.setFont(new Font("Arial", Font.BOLD, 18));
		
		newGame2.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_2, ActionEvent.CTRL_MASK));
		newGame3.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_3, ActionEvent.CTRL_MASK));
		newGame4.setAccelerator(KeyStroke.getKeyStroke( KeyEvent.VK_4, ActionEvent.CTRL_MASK));
		
		gameSolution.setFont(new Font("Arial", Font.BOLD, 18));
		gameSolution.setEnabled(false);
		gameSolution.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));

		gameExit.setFont(new Font("Arial", Font.BOLD, 18));
		gameExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		
		

		menuHelp.setFont(new Font("Arial", Font.BOLD, 18));

		gameColor.setFont(new Font("Arial", Font.BOLD, 18));
		gameColor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));

		aboutGame.setFont(new Font("Arial", Font.BOLD, 18));
		aboutGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.ALT_MASK));
		
		gameControl.newGameTwoDim(newGame2, newGame3, newGame4, sudoku);
		
		gameControl.networkModelVC(sudoku, connection, disconnect, userInput, netChat);
	
		network.add(connection);
		network.add(disconnect);
		menuGame.add(newGame);
		newGame.add(newGame2);
		newGame.add(newGame3);
		newGame.add(newGame4);
		menuGame.add(gameSolution);
		menuGame.add(gameExit);
		menuBar.add(menuGame);
		menuHelp.add(gameColor);
		menuHelp.add(aboutGame);
		menuBar.add(network);
		menuBar.add(menuHelp);
		menuPanel.add(menuBar);

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * *Bottom Panel - Sub Panel in Main Frame * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */

		JPanel bottomPanel = new JPanel();
		//bottomPanel.setPreferredSize(new Dimension(1065, 200));

		// bottom pannel for networking
		bottomPanel.setPreferredSize(new Dimension(1065, 250));
		
		
		
		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * *Left Panel - Sub Panel in Bottom Panel * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(400, 100));
		leftPanel.setBackground(CLOUDBURST);
		leftPanel.setBorder(BLACKLINE);

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * Timer - Used to set the time for the present game * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JLabel time = new JLabel("Timer :");
		time.setFont(new Font("Arial", Font.BOLD, 20));
		time.setForeground(Color.WHITE);
		timeField = new JTextField();
		timeField.setText("00:00:00");
		timeField.setFont(new Font("Arial", Font.BOLD, 20));
		timeField.setPreferredSize(new Dimension(100, 40));
		timeField.setHorizontalAlignment(JTextField.CENTER);
		timeField.setBackground(Color.WHITE);
		timeField.setBorder(BLACKLINE);
		timeField.setEditable(false);
		timeField = new JTextField("");
		timeField.setFont(new Font("Arial", Font.BOLD, 18));
		timeField.setPreferredSize(new Dimension(90, 30));
		timeField.setBackground(Color.white);
		// reset startTime
		startTime = LocalTime.now();
		// if timer running, stop it
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
		timer = new Timer(TIMER_DELAY, new TimerListener());
		timer.start();
		JLabel timeLabel = new JLabel("Time ");
		timeLabel.setFont(new Font("Arial", Font.BOLD, 18));

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * *Score - Used to display the score for the present game * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JLabel score = new JLabel("Score :");
		score.setFont(new Font("Arial", Font.BOLD, 20));
		score.setForeground(Color.WHITE);
		JTextField points = new JTextField();
		points.setText("22");
		points.setFont(new Font("Arial", Font.BOLD, 20));
		points.setPreferredSize(new Dimension(100, 40));
		points.setHorizontalAlignment(JTextField.CENTER);
		points.setBackground(Color.WHITE);
		points.setBorder(BLACKLINE);
		points.setEditable(false);

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * *Mistakes - Used to display the mistakes for the present game * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * * * *
		 */
		JLabel mistakes = new JLabel("Mistakes :");
		mistakes.setFont(new Font("Arial", Font.BOLD, 20));
		mistakes.setForeground(Color.WHITE);
		JTextField mistake = new JTextField();
		mistake.setText("2");
		mistake.setFont(new Font("Arial", Font.BOLD, 20));
		mistake.setPreferredSize(new Dimension(100, 40));
		mistake.setHorizontalAlignment(JTextField.CENTER);
		mistake.setBackground(Color.WHITE);
		mistake.setBorder(BLACKLINE);
		mistake.setEditable(false);

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * *Quit - Used to quit the present game * * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JButton quitButton = new JButton("Quit");
		quitButton.setFont(TEXTFONT);
		quitButton.setBorder(BLACKLINE);
		quitButton.setPreferredSize(new Dimension(100, 40));
		quitButton.setBackground(CATSKILLWHITE);
		gameControl.addMessages(quitButton, "The user quits the game.");

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * *Reset - Used to reset the present game board * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JButton resetButton = new JButton("Reset");
		resetButton.setFont(TEXTFONT);
		resetButton.setBorder(BLACKLINE);
		resetButton.setPreferredSize(new Dimension(100, 40));
		resetButton.setBackground(CATSKILLWHITE);
		gameControl.addMessages(resetButton, "The user resets the game");

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * * Adding all the elements to the left panel of the game * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JPanel timerPanel = new JPanel();
		timerPanel.setBackground(CLOUDBURST);
		timerPanel.add(time);
		timerPanel.add(timeField);
		timerPanel.setLayout(new FlowLayout(0, 40, 0));
		JPanel scorePanel = new JPanel();
		scorePanel.setBackground(CLOUDBURST);
		scorePanel.add(score);
		scorePanel.add(points);
		scorePanel.setLayout(new FlowLayout(0, 40, 0));
		JPanel mistakePanel = new JPanel();
		mistakePanel.setBackground(CLOUDBURST);
		mistakePanel.add(mistakes);
		mistakePanel.add(mistake);
		mistakePanel.setLayout(new FlowLayout(0, 20, 0));
		JPanel resetQuit = new JPanel();
		resetQuit.setBackground(CLOUDBURST);
		resetQuit.add(resetButton);
		resetQuit.add(quitButton);
		resetQuit.setLayout(new FlowLayout(0, 50, 0));
		leftPanel.add(timerPanel);
		leftPanel.add(scorePanel);
		leftPanel.add(mistakePanel);
		leftPanel.add(resetQuit);

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * Right Panel - Sub Panel in Bottom Panel * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */

		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(650, 100));
		rightPanel.setBackground(CLOUDBURST);
		rightPanel.setBorder(BLACKLINE);

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * Designing the MODES of the game in right Panel* * * * * * * * * * * * * * * *
		 * * * * * (i) Design Mode * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * * * * *(ii) Play Mode * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JPanel modePanel = new JPanel();
		modePanel.setPreferredSize(new Dimension(600, 30));
		modePanel.setBackground(CLOUDBURST);

		JLabel mode = new JLabel(("Choose Mode: "));
		mode.setFont(TEXTFONT);
		mode.setForeground(Color.WHITE);

		JRadioButton design = new JRadioButton("Design");
		design.setBackground(CLOUDBURST);
		design.setFont(TEXTFONT);
		design.setForeground(Color.WHITE);
		design.setSelected(true);

		JRadioButton play = new JRadioButton("Play");
		play.setBackground(CLOUDBURST);
		play.setFont(TEXTFONT);
		play.setForeground(Color.WHITE);

		ButtonGroup modeChoice = new ButtonGroup();
		modeChoice.add(design);
		modeChoice.add(play);

		modePanel.add(mode);
		modePanel.add(design);
		modePanel.add(play);
		modePanel.setLayout(new FlowLayout(0, 30, 0));

		gameControl.addMessage(design, "Switched to Design Mode");
		gameControl.addMessage(play, "Switched to Play Mode");

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * *Designing the CUSTOMISATION PHASE of the game in right Panel * * * * * * * *
		 * (i) Difficulty (Easy / Medium / Hard) * * * * * * * * * * * * * * * * * * *
		 * *(ii)Dimensions ( 2 , 3 or 4) * * * * * * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JPanel customizePanel = new JPanel();
		JLabel dimension = new JLabel("Dimension: ");
		JButton button1 = new JButton("2");
		JButton button2 = new JButton("3");
		JButton button3 = new JButton("4");

		customizePanel.setPreferredSize(new Dimension(600, 50));
		customizePanel.setBackground(CLOUDBURST);
		dimension.setFont(TEXTFONT);
		dimension.setForeground(Color.WHITE);

		button1.setPreferredSize(new Dimension(50, 50));
		button1.setFont(TEXTFONT);
		button1.setBorder(LOGOLINE);

		button2.setPreferredSize(new Dimension(50, 50));
		button2.setFont(TEXTFONT);
		button2.setBorder(LOGOLINE);

		button3.setPreferredSize(new Dimension(50, 50));
		button3.setFont(TEXTFONT);
		button3.setBorder(LOGOLINE);

		gameControl.changeTwoDimension(button1, sudoku, timer);
		gameControl.changeThreeDimension(button2, sudoku, timer);
		gameControl.changeFourDimension(button3, sudoku, timer);

		customizePanel.add(dimension);
		customizePanel.add(button1);
		customizePanel.add(button2);
		customizePanel.add(button3);

		String gameLev[] = { "Easy", "Medium", "Hard" };
		JComboBox<String> lev = new JComboBox<>(gameLev);
		gameControl.getSelected(lev);

		lev.setForeground(Color.BLACK);
		lev.setBackground(POLOBLUE);
		lev.setFont(TEXTFONT);
		lev.setEnabled(false);

		customizePanel.add(lev);
		customizePanel.setLayout(new FlowLayout(0, 30, 0));

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * *Designing the ADVANCE PHASE of the game in right Panel * * * * * * * * * * *
		 * *Gives the options to save, load, show, hide and random generation of the * *
		 * board according to the modes. * * * * * * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JPanel advOptions = new JPanel();
		advOptions.setPreferredSize(new Dimension(600, 80));
		advOptions.setBackground(CLOUDBURST);
		JLabel advOptHeader = new JLabel("Advance Options: ");
		advOptHeader.setPreferredSize(new Dimension(600, 30));
		advOptHeader.setFont(TEXTFONT);
		advOptHeader.setForeground(Color.WHITE);
		JButton show = new JButton("Show");
		show.setBackground(POLOBLUE);
		show.setFont(TEXTFONT);
		show.setForeground(Color.BLACK);
		show.setEnabled(false);
		JButton save = new JButton("Save");
		save.setBackground(POLOBLUE);
		save.setFont(TEXTFONT);
		save.setForeground(Color.BLACK);
		JButton load = new JButton("Load");
		load.setBackground(POLOBLUE);
		load.setEnabled(false);
		load.setFont(TEXTFONT);
		load.setForeground(Color.BLACK);
		JButton rand = new JButton("Rand");
		rand.setBackground(POLOBLUE);
		rand.setFont(TEXTFONT);
		rand.setForeground(Color.BLACK);
		gameControl.addMessages(show, "Solution is Visible");
		gameControl.addMessages(save, "Game configuration is saved");
		gameControl.addMessages(load, "Game is being loaded");
		gameControl.addMessages(rand, "Random board is generated");
		gameControl.enableLevel(play, lev, show, save, load, gameSolution);
		gameControl.disableLevel(design, lev, show, save, load, gameSolution);
		gameControl.exitGame(quitButton, gameExit);
		advOptions.add(advOptHeader);
		advOptions.add(show);
		advOptions.add(save);
		advOptions.add(load);
		advOptions.add(rand);
		advOptions.setLayout(new FlowLayout(0, 30, 10));
		rightPanel.add(modePanel);
		rightPanel.add(customizePanel);
		rightPanel.add(advOptions);
		

		
		bottomPanel.setLayout(new BorderLayout(0,0));
		bottomPanel.add(leftPanel, BorderLayout.WEST);
		bottomPanel.add(rightPanel,BorderLayout.EAST);
		
		
		

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * Central Panel - Sub Panel in Main Frame * * * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */

		JPanel centerPanel = new JPanel();
		centerPanel.setPreferredSize(new Dimension(1200, 600));if(numberOfGrids == 2) {
			save.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					Formatter format = null;
					JFileChooser fileChooser = new JFileChooser();
					Component pane = null;
					File file = null;
					if (fileChooser.showSaveDialog(pane) == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
					}
					if(file != null) {
						try {
							format = new Formatter(file);
							format.format("2,", gridTwoBoard[row][col]);
							for(int row = 0 ; row < 4; row++) {
								for (int col = 0; col < 4; col++) {
									format.format("%s", saveValues[row][col]);
								}
							}
//							JOptionPane.showMessageDialog(frame,"File Saved Successfully");
						} catch (Exception ae) {
							System.err.println("Unable to save the file");
						} finally {
							format.close();
						}
					}
				}
			});
		}
		if(numberOfGrids == 3 ) {
			
			save.addActionListener(new ActionListener(){
				//// saving the files 
				public void actionPerformed(ActionEvent e) {
					Formatter format = null;
					JFileChooser fileChooser = new JFileChooser();
					Component pane = null;
					File file = null;
					if (fileChooser.showSaveDialog(pane) == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
					}
					if(file != null) {
						try {
							format = new Formatter(file);
							format.format("3,", gridTwoSolution[row][col]);
							for(int row = 0 ; row < 9; row++) {
								for (int col = 0; col < 9; col++) {
									format.format("%s", saveValues[row][col]);
								}
							}
//							JOptionPane.showMessageDialog(frame,"File Saved Successfully");
						} catch (Exception ae) {
							System.err.println("Unable to save the file");
						} finally {
							format.close();
						}
					}
				}
			});
		}
		
		if (numberOfGrids == 4 ) {
			
			save.addActionListener(new ActionListener(){
				//// saving the files 
				public void actionPerformed(ActionEvent e) {
					Formatter format = null;
					JFileChooser fileChooser = new JFileChooser();
					Component pane = null;
					File file = null;
					if (fileChooser.showSaveDialog(pane) == JFileChooser.APPROVE_OPTION) {
						file = fileChooser.getSelectedFile();
					}
					if(file != null) {
						try {
							format = new Formatter(file);
							format.format("2,", gridTwoSolution[row][col]);
							for(int row = 0 ; row < 16; row++) {
								for (int col = 0; col < 16; col++) {
									format.format("%s", saveValues[row][col]);
								}
							}
//							JOptionPane.showMessageDialog(frame,"File Saved Successfully");
						} catch (Exception ae) {
							System.err.println("Unable to save the file");
						} finally {
							format.close();
						}
					}
				}
			});
		}

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * Hint Panel - Sub Panel in Central Frame * * * * * * * * * * * * * * * * * * *
		 * It is used to show hint buttons to fill in sudoku * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JPanel hintPanel = new JPanel();
		hintPanel.setPreferredSize(new Dimension(100, 600));
		hintPanel.setBackground(SWAMP);
		hintPanel.setBorder(BLACKLINE);

		if (numberOfGrids == 2) {
			for (i = 0; i < 4; i++) {
				hintTwoDim[i] = new JButton(numvaluesTwoDim[i]);
				hintTwoDim[i].setActionCommand(numvaluesTwoDim[i]);
				hintTwoDim[i].setBackground(CATSKILLWHITE);
				hintTwoDim[i].setPreferredSize(new Dimension(80, 80));
				hintTwoDim[i].setBorder(HINTLINE);
				hintTwoDim[i].setFont(TEXTFONT);
				hintTwoDim[i].addActionListener(this);
				gameControl.addMessages(hintTwoDim[i], i + 1 + " number is chosen");
				hintPanel.add(hintTwoDim[i]);
			}
			hintPanel.setLayout(new FlowLayout(1, 1, 50));
		}

		else if (numberOfGrids == 3) {
			for (i = 0; i < 9; i++) {
				hintThreeDim[i] = new JButton(numvaluesThreeDim[i]);
				hintThreeDim[i].setActionCommand(numvaluesThreeDim[i]);
				hintThreeDim[i].setBackground(CATSKILLWHITE);
				hintThreeDim[i].setPreferredSize(new Dimension(50, 50));
				hintThreeDim[i].setBorder(HINTLINE);
				hintThreeDim[i].addActionListener(this);
				gameControl.addMessages(hintThreeDim[i], i + 1 + " number is chosen");
				hintPanel.add(hintThreeDim[i]);
			}
			hintPanel.setLayout(new FlowLayout(1, 1, 10));
		}

		else if (numberOfGrids == 4) {
			for (i = 0; i < 16; i++) {
				hintFourDim[i] = new JButton(numvaluesFourDim[i]);
				hintFourDim[i].setActionCommand(numvaluesFourDim[i]);
				hintFourDim[i].setBackground(CATSKILLWHITE);
				hintFourDim[i].setPreferredSize(new Dimension(35, 35));
				hintFourDim[i].setBorder(HINTLINE);
				hintFourDim[i].addActionListener(this);
				gameControl.addMessages(hintFourDim[i], numvaluesFourDim[i] + " number is chosen");
				hintPanel.add(hintFourDim[i]);
			}

			hintPanel.setLayout(new FlowLayout(1, 30, 1));
		}

		/*
		 * Solution Panel
		 */
		JPanel solutionPanel = new JPanel();
		if (numberOfGrids == 2) {
			solutionPanel.setPreferredSize(new Dimension(300, 300));
			solutionPanel.setBorder(BLACKLINE);
			gridTwoSolution = gameControl.generateTwoSolution();
			GridLayout glCenter = new GridLayout(4, 4);
			solutionPanel.setLayout(glCenter);
			for (int y = 0; y < 4; y++) {
				for (int x = 0; x < 4; x++) {
					JLabel gridSquare = new JLabel(String.valueOf(gridTwoSolution[y][x]), SwingConstants.CENTER);
					gridSquare.setBackground(CATSKILLWHITE);
					gridSquare.setOpaque(true);
					gridSquare.setPreferredSize(new Dimension(75, 75));
					gridSquare.setBorder(BLACKLINE);
					gridSquare.setFont(TEXTFONT);
					solutionPanel.add(gridSquare);
				}
			}
			gameControl.showSolution(show, gameSolution, solutionPanel, timer);
		}
		if (numberOfGrids == 3) {
			solutionPanel.setPreferredSize(new Dimension(450, 450));
			gridThreeSolution = gameControl.generateThreeSolution();
			GridLayout glCenter = new GridLayout(9, 9);
			solutionPanel.setLayout(glCenter);
			for (int y = 0; y < 9; y++) {
				for (int x = 0; x < 9; x++) {
					JLabel gridSquare = new JLabel(String.valueOf(gridThreeSolution[y][x]), SwingConstants.CENTER);
					gridSquare.setOpaque(true);
					gridSquare.setPreferredSize(new Dimension(50, 50));
					gridSquare.setBorder(BLACKLINE);
					gridSquare.setFont(TEXTFONT);
					solutionPanel.add(gridSquare);
				}
			}
			gameControl.showSolution(show, gameSolution, solutionPanel, timer);
			
		}
		if (numberOfGrids == 4) {
			solutionPanel.setPreferredSize(new Dimension(480, 480));
			gridFourSolution = gameControl.generateFourSolution();
			GridLayout glCenter = new GridLayout(16, 16);
			solutionPanel.setLayout(glCenter);
			for (int y = 0; y < 16; y++) {
				for (int x = 0; x < 16; x++) {
					JLabel gridSquare;
					
					if(gridFourSolution[y][x] == 10) {
						gridSquare = new JLabel("A", SwingConstants.CENTER);
					}
					else if(gridFourSolution[y][x] == 11) {
						gridSquare = new JLabel("B", SwingConstants.CENTER);
					}
					else if(gridFourSolution[y][x] == 12) {
						gridSquare = new JLabel("C", SwingConstants.CENTER);
					}
					else if(gridFourSolution[y][x] == 13) {
						gridSquare = new JLabel("D", SwingConstants.CENTER);
					}
					else if(gridFourSolution[y][x] == 14) {
						gridSquare = new JLabel("E", SwingConstants.CENTER);
					}
					else if(gridFourSolution[y][x] == 15) {
						gridSquare = new JLabel("F", SwingConstants.CENTER);
					}
					else if(gridFourSolution[y][x] == 16) {
						gridSquare = new JLabel("G", SwingConstants.CENTER);
					}
					else {
						gridSquare = new JLabel(String.valueOf(gridFourSolution[y][x]), SwingConstants.CENTER);
					}
					gridSquare.setOpaque(true);
					gridSquare.setFont(TEXTFONT);
					gridSquare.setPreferredSize(new Dimension(30, 30));
					gridSquare.setBorder(HINTLINE);
					solutionPanel.add(gridSquare);
				}
			}
			gameControl.showSolution(show, gameSolution, solutionPanel, timer);

		}

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * Grid Panel - Sub Panel in Central Frame * * * * * * * * * * * * * * * * * * *
		 * It is used to show grid buttons to fill in sudoku * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JPanel gridPanel = new JPanel();
		gridPanel.setPreferredSize(new Dimension(600, 600));
		gridPanel.setBackground(BOTTICELLI);
		gridPanel.setBorder(BLACKLINE);

		GridBagConstraints gbcCenter = new GridBagConstraints();
		/*
		 * Setting the grid buttons in the panel
		 */
		if (numberOfGrids == 2) {
			gridTwoBoard = gameControl.generateTwoBoard();
			GridLayout glCenter = new GridLayout(4, 4);
			gridPanel.setLayout(glCenter);
			for (int y = 1; y <= 4; y++) {
				for (int x = 1; x <= 4; x++) {
					gridTwoDim[y - 1][x - 1] = new JButton();
					gridTwoDim[y - 1][x - 1].setOpaque(true);
					gridTwoDim[y - 1][x - 1].setBackground(BOTTICELLI);
					gridTwoDim[y - 1][x - 1].setPreferredSize(new Dimension(50, 50));
					gridTwoDim[y - 1][x - 1].setBorder(GRIDLINE);
					gridTwoDim[y - 1][x - 1].addActionListener(this);
					gridPanel.add(gridTwoDim[y - 1][x - 1]);
					gameControl.addMessages(gridTwoDim[y - 1][x - 1], "[" + y + " , " + x + "] button is pressed");
					gameControl.generateGame(play, gridTwoDim[y - 1][x - 1], y - 1, x - 1, gridTwoBoard, timer);
					new GameView(gridTwoDim[y - 1][x - 1], y, x);
				}
			}
		} else if (numberOfGrids == 3) {
			gridThreeBoard = gameControl.generateThreeBoard();
			GridLayout glCenter = new GridLayout(9, 9);
			gridPanel.setLayout(glCenter);
			for (int y = 1; y <= 9; y++) {
				for (int x = 1; x <= 9; x++) {
					gridThreeDim[y - 1][x - 1] = new JButton();
					gridThreeDim[y - 1][x - 1].setOpaque(true);
					gridThreeDim[y - 1][x - 1].setBackground(BOTTICELLI);
					gridThreeDim[y - 1][x - 1].setPreferredSize(new Dimension(65, 65));
					gridThreeDim[y - 1][x - 1].setBorder(GRIDLINE);
					gridThreeDim[y - 1][x - 1].addActionListener(this);
					gridPanel.add(gridThreeDim[y - 1][x - 1]);
					gameControl.addMessages(gridThreeDim[y - 1][x - 1], "[" + y + " , " + x + "] button is pressed");
					gameControl.generateGame(play, gridThreeDim[y - 1][x - 1], y - 1, x - 1, gridThreeBoard, timer);
					gameControl.generateGame(play, gridThreeDim[y - 1][x - 1], y - 1, x - 1, gridThreeBoard, timer);

					new GameView(gridThreeDim[y - 1][x - 1], y, x);

				}
			}
		} else if (numberOfGrids == 4) {
			gridFourBoard = gameControl.generateFourBoard();
			GridLayout glCenter = new GridLayout(16, 16);
			gridPanel.setLayout(glCenter);
			for (int y = 1; y <= 16; y++) {
				for (int x = 1; x <= 16; x++) {
					gridFourDim[y - 1][x - 1] = new JButton();
					gridFourDim[y - 1][x - 1].setOpaque(true);
					gridFourDim[y - 1][x - 1].setBackground(BOTTICELLI);
					gridFourDim[y - 1][x - 1].setPreferredSize(new Dimension(65, 65));
					gridFourDim[y - 1][x - 1].setBorder(GRIDLINE);
					gridFourDim[y - 1][x - 1].addActionListener(this);
					gridPanel.add(gridFourDim[y - 1][x - 1]);
					gameControl.addMessages(gridFourDim[y - 1][x - 1], "[" + y + " , " + x + "] button is pressed");
					gameControl.generateGame(play, gridFourDim[y - 1][x - 1], y - 1, x - 1, gridFourBoard, timer);
					new GameView(gridFourDim[y - 1][x - 1], y, x);
				}
			}
		}

		/*
		 * Defining the grid bag constants for (5x5) grid buttons.
		 */
		gbcCenter.gridx = 1;
		gbcCenter.gridy = 1;
		gbcCenter.gridwidth = 2;
		gbcCenter.gridheight = 2;
		gbcCenter.weightx = 0.2;
		gbcCenter.weighty = 0.2;
		gbcCenter.insets = new Insets(0, 0, 0, 0);

		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * *UserInput Panel - Sub Panel in Central Frame * * * * * * * * * * * * * * * *
		 * *It is used to trace the user interaction to fill in sudoku * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		JPanel userInputPanel = new JPanel();
		userInputPanel.setPreferredSize(new Dimension(350, 600));
		userInputPanel.setBackground(SWAMP);
		userInputPanel.setBorder(BLACKLINE);

		JButton logoButton = new JButton(new ImageIcon(getClass().getResource("sudoku_logo_button.png")));
		logoButton.setPreferredSize(new Dimension(300, 100));
		logoButton.setForeground(Color.WHITE);
		logoButton.setBackground(SWAMP);
		logoButton.setBorder(LOGOLINE);
		JLabel inputHeader = new JLabel("User Input :", SwingConstants.LEFT);
		inputHeader.setPreferredSize(new Dimension(200, 20));
		inputHeader.setFont(TEXTFONT);
		inputHeader.setBackground(CLOUDBURST);
		inputHeader.setForeground(Color.WHITE);
		inputHeader.setAlignmentX(LEFT_ALIGNMENT);
		
		PrintStream printStream = new PrintStream(new UserInteraction(userInput));
		System.setOut(printStream);
		System.setErr(printStream);

		JScrollPane scrollableTextArea = new JScrollPane(userInput);
		scrollableTextArea.setPreferredSize(new Dimension(300, 400));
		scrollableTextArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollableTextArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollableTextArea.setAutoscrolls(true);

		userInputPanel.add(logoButton);
		userInputPanel.add(inputHeader);
		userInputPanel.add(scrollableTextArea);

		gameControl.aboutGame(logoButton, aboutGame);

		centerPanel.add(hintPanel);
		centerPanel.add(gridPanel);
		centerPanel.add(userInputPanel);
		centerPanel.setLayout(new FlowLayout(0, 0, 0));

		gameControl.KeyFunctions(sudoku);
		
		
		
		
		
		//vpatel
		class networkChatPanel extends JPanel {

			networkChatPanel(){
				
				this.setBackground(new Color(0x37BFBF));
				this.setLayout(new BorderLayout());
				
				netChat.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
				netChat.setPreferredSize(new Dimension(200, 30));
				netChat.setBorder(new EtchedBorder(15));
				
				this.setBorder(new EtchedBorder(15));
				
				netChat.setEditable(false);
				
				this.add(netChat, BorderLayout.CENTER);
			};
		}
		
		
		/*
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 * *Merging all the components in Sudoku Frame * * * * * * * * * * * * * * * * *
		 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
		 */
		sudoku.add(menuPanel, BorderLayout.NORTH);
		sudoku.add(centerPanel, BorderLayout.CENTER);
		
		
		//vpatel
		bottomPanel.add(new networkChatPanel(), BorderLayout.SOUTH);
		
		sudoku.add(bottomPanel, BorderLayout.SOUTH);
		
		sudoku.setVisible(true);
		sudoku.setResizable(false);
		sudoku.setDefaultCloseOperation(EXIT_ON_CLOSE);
		sudoku.pack();
	}

	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = GameView.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}

	}
	
	public void actionPerformed(ActionEvent e) {
		if (gridDimension == 2) {
			for (int j = 0; j < 4; j++) {
				if (e.getSource() == hintTwoDim[j]) {
					action = e.getActionCommand();
					System.out.println(action);
					break;
				}
			}
			boolean finished = false;
			for (int a = 0; a < 4; a++) {
				for (int b = 0; b < 4; b++) {
					if (e.getSource() == gridTwoDim[a][b]) {
						boolean foundInRow2 = gameModel.checkInRow2(a, action, gridTwoDim);
						boolean foundInCol2 = gameModel.checkInCol2(b, action, gridTwoDim);
						boolean checkInBox2 = gameModel.checkInBox2(action, a, b, gridTwoDim);
						if (!foundInRow2 && !foundInCol2 && !checkInBox2) {
							gridTwoDim[a][b].setText(action);
							arrayTwoDim[a][b] = action;
							System.out.println(arrayTwoDim[a][b] + " Changes made");
							finished = true;
							break;
						}
					}
				}
				if (finished)
					break;
			}
		}

		if (gridDimension == 3)

		{
			for (int j = 0; j < 9; j++) {
				if (e.getSource() == hintThreeDim[j]) {
					action = e.getActionCommand();
					System.out.println(action);
					break;
				}
			}
			boolean finished = false;
			for (int a = 0; a < 9; a++) {
				for (int b = 0; b < 9; b++) {
					if (e.getSource() == gridThreeDim[a][b]) {
						boolean foundInRow3 = gameModel.checkInRow3(a, action, gridThreeDim);
						boolean foundInCol3 = gameModel.checkInCol3(b, action, gridThreeDim);
						boolean checkInBox3 = gameModel.checkInBox3(action, a, b, gridThreeDim);
						if (!foundInRow3 && !foundInCol3 && !checkInBox3) {
							gridThreeDim[a][b].setText(action);
							arrayThreeDim[a][b] = action;
							finished = true;
							break;
						}

					}
				}
				if (finished)
					break;
			}
		}

		if (gridDimension == 4) {
			for (int j = 0; j < 16; j++) {
				if (e.getSource() == hintFourDim[j]) {
					action = e.getActionCommand();
					System.out.println(action);
					break;
				}
			}
			boolean finished = false;
			for (int a = 0; a < 16; a++) {
				for (int b = 0; b < 16; b++) {
					if (e.getSource() == gridFourDim[a][b]) {
						boolean foundInRow4 = gameModel.checkInRow4(a, action, gridFourDim);
						boolean foundInCol4 = gameModel.checkInCol4(b, action, gridFourDim);
						boolean checkInBox4 = gameModel.checkInBox4(action, a, b, gridFourDim);
						if (!foundInRow4 && !foundInCol4 && !checkInBox4) {
							gridFourDim[a][b].setText(action);
							arrayFourDim[a][b] = action;
							finished = true;
							break;
						}
					}
				}
				if (finished)
					break;
			}
		}
	}

	/**
	 * Parameterized Controller This controller instantiates row and columns
	 * according to the actions performed on JButton
	 * 
	 * @param button It is the button on which action is performed
	 * @param row    It is the row to which button belongs to, from the grid
	 * @param col    It is the column to which the button belongs to, from the grid.
	 */
	public GameView(JButton button, int row, int col) {
		button.addActionListener(a1 -> this.row = row);
		button.addActionListener(a1 -> this.col = col);
		button.addActionListener(a1 -> System.out.println("Row is  " + row));
		button.addActionListener(a1 -> System.out.println("Column is  " + col));
	}

	/*
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * Method Main for the Class * * * * * * * * * * * * * * * * * * * * * * * * * *
	 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
	 */
	/**
	 * It is the main method for the class. The program starts its run from here.
	 * 
	 * @param args - arguments - Array of string to be compiled in the program.
	 */
	public static void main(String[] args) {
		new GameView(2);
	}

	/**
	 * 
	 * @author Krushang Patel <041-021-848>
	 * @author Karnav Panchal < >
	 * @version 1.8.0_301
	 * @see default package
	 * @since 1.8.0_301 (Compiler's Version)
	 * 
	 *        It is used to display the start splash for the screen when the game
	 *        begins its play.
	 *
	 */
	public class SplashScreen extends JWindow {
		/**
		 * It is for the unique identification of the class
		 */
		private static final long serialVersionUID = 6248477390124803341L;
		/**
		 * It is the time duration for which splash will be displayed.
		 */
		private final int duration;

		/**
		 * Parameterized constructor where duration is instantiated.
		 * 
		 * @param duration It is the time duration for which splash will be displayed.
		 */
		public SplashScreen(int duration) {
			this.duration = duration;
		}

		/**
		 * This method is used to display the splash screen and fix the frame and size
		 * for that.
		 */
		public void showSplashWindow() {
			JPanel content = new JPanel(new BorderLayout());
			content.setBackground(SWAMP);
			int width = 530 + 10;
			int height = 190 + 10;
			Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (screen.width - width) / 2;
			int y = (screen.height - height) / 2;
			setBounds(x, y, width, height);
			Icon image = new ImageIcon(getClass().getResource("sudoku.png"));
			JLabel label = new JLabel(image);
			JLabel demo = new JLabel("Sudoku - Krushang & Karnav - Summer 2022", JLabel.CENTER);
			demo.setForeground(BOTTICELLI);
			demo.setFont(new Font(Font.SANS_SERIF, Font.ROMAN_BASELINE, 15));
			content.add(label, BorderLayout.CENTER);
			content.add(demo, BorderLayout.SOUTH);

			setContentPane(content);
			setVisible(true);
			try {
				Thread.sleep(duration);
				dispose();
			} catch (InterruptedException e) {
			}
			dispose();
		}
	}

	public class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalTime endTime = LocalTime.now();
			long secondDifference = startTime.until(endTime, ChronoUnit.SECONDS);
			int minutes = (int) (secondDifference / 60);
			int seconds = (int) (secondDifference % 60);
			String timeText = String.format("%02d:%02d", minutes, seconds);
			timeField.setText(timeText);
		}
	}

	/**
	 * @author Krushang Patel <041-021-848>
	 * @author Karnav Panchal < >
	 * @version 1.8.0_301
	 * @see default package
	 * @since 1.8.0_301 (Compiler's Version)
	 *
	 *        UserInteraction - Inner Class It is used to direct user interaction on
	 *        the board to text area
	 * 
	 * 
	 */
	public class UserInteraction extends OutputStream {

		/**
		 * Name of the text area where output is redirected
		 */
		private JTextArea textArea;

		/**
		 * Parameterized constructor to instantiate the textArea
		 * 
		 * @param textArea Name of the variable where output is redirected
		 */
		public UserInteraction(JTextArea textArea) {
			this.textArea = textArea;
		}

		/**
		 * This method is used to write the text into the desired direction from the
		 * console, upon the interaction of users.
		 */
		@Override
		public void write(int b) throws IOException {
			// redirects data to the text area
			textArea.append(String.valueOf((char) b));
			// scrolls the text area to the end of data

			textArea.setCaretPosition(textArea.getDocument().getLength());

		}
	}
	
	
	
	
	//vpatel
	
	public JTextField getNetChat() {
		return netChat;
	}

	public void setNetChat(JTextField netChat) {
		this.netChat = netChat;
	}

}