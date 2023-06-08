import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;


/**
	 * 
	 * @author Krushang Patel <041-021-848>
	 * @author Karnav Panchal <041-022-780>
	 * @version 1.8.0_301
	 * @see default package
	 * @since 1.8.0_301 (Compiler's Version)
	 * 
	 *        GameInteraction - It is an Inner Class. It is used to assign the
	 *        actions to the components when they are triggered by user
	 *
	 */
	public class GameController extends JFrame {
		/**
		 * It is for the unique identification of the class
		 */
		private static final long serialVersionUID = -6784904236778611860L;
		private int[][] gridBoard;
		private int[][] gridSolution;
		private int[][] gridSolution2;
		private GameModel gameModel;
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
		
		private SudokuNetworkModel sConnect = new SudokuNetworkModel();
		private boolean connectionStatus;
		private String clientName;
		private String serverAddress;
		private int port;
		private ClientClass client;
		private Scanner in;
		private PrintWriter out;
		private Socket socket;
		private Thread connectedClient;
		private String messageFromServer;

		
		/**
		 * Non - Parameterized constructor
		 */
		public GameController() {

		}

		/**
		 * This method is used to add text message on the actions by user on the radio
		 * button
		 * 
		 * @param component The radio button on which action listener is being added.
		 * @param message   The string which will be displayed on the actions.
		 */
		public void addMessage(JRadioButton component, String message) {
			component.addActionListener(a1 -> System.out.println(message));
		}

		/**
		 * This method is used to add text message on the actions by user on the button
		 * 
		 * @param component The button on which action listener is being added.
		 * @param text      The string which will be displayed on the actions.
		 */
		public void addMessages(JButton component, String text) {
			component.addActionListener(a1 -> System.out.println(text));
		}
		
		

		/**
		 * This method is used to present a dialog box when a user clicks on the
		 * particular JButton specifically the game logo.
		 * 
		 * @param component The button on which action listener is being added.
		 */
		public void aboutGame(JButton component, JMenuItem menuItem) {
			JDialog dialog = new JDialog();
			Icon image = new ImageIcon(getClass().getResource("sudoku_about.png"));
			JLabel label = new JLabel(image);
			JButton about = new JButton("About the Game");
			dialog.setTitle("About the Game");
			dialog.setSize(650,475);
			dialog.add(label);
			menuItem.addActionListener(a1 -> dialog.setVisible(true));
			component.addActionListener(a1 -> dialog.setVisible(true));	
		}

		/**
		 * The method is used to enable the interactivity of user with particular combo
		 * box depending upon the mode of the game board.
		 * 
		 * @param component JRadiobutton on which action listener is being added.
		 * @param comboBox  JComboBox which is being enabled on the actions.
		 */
		public void enableLevel(JRadioButton component, JComboBox<String> comboBox, JButton button1, JButton button2, JButton button3, JMenuItem menuItem) {
			component.addActionListener(a1 -> comboBox.setEnabled(true));
			component.addActionListener(a1 -> button1.setEnabled(true));
			component.addActionListener(a1 -> menuItem.setEnabled(true));
			component.addActionListener(a1 -> button3.setEnabled(true));
			component.addActionListener(a1 -> button2.setEnabled(false));
		}

		/**
		 * The method is used to disable the interactivity of user with particular combo
		 * box depending upon the mode of the game board.
		 * 
		 * @param component JRadiobutton on which action listener is being added.
		 * @param comboBox  JComboBox which is being enabled on the actions.
		 */
		public void disableLevel(JRadioButton component, JComboBox<String> comboBox, JButton button1, JButton button2, JButton button3, JMenuItem menuItem) {
			component.addActionListener(a1 -> comboBox.setEnabled(false));
			component.addActionListener(a1 -> button1.setEnabled(false));
			component.addActionListener(a1 -> menuItem.setEnabled(false));
			component.addActionListener(a1 -> button3.setEnabled(false));
			component.addActionListener(a1 -> button2.setEnabled(true));
		}

		/**
		 * The method is used to change the dimension to 2 with a grid of 4 x 4 buttons
		 * 
		 * @param component The button on which action listener is added.
		 * @param frame     The frame which is to be disposed when a new grid is
		 *                  created.
		 */
		public void changeTwoDimension(JButton component, JFrame frame, Timer timer) {
			component.addActionListener(a1 -> frame.dispose());
			component.addActionListener(a1 -> timer.stop());
			component.addActionListener(a1 -> new GameView(2));
			component.addActionListener(a1 -> timer.start());
		}

		/**
		 * The method is used to change the dimension to 3 with a grid of 9 x 9 buttons
		 * 
		 * @param component The button on which action listener is added.
		 * @param frame     The frame which is to be disposed when a new grid is
		 *                  created.
		 */
		public void changeThreeDimension(JButton component, JFrame frame, Timer timer) {
			component.addActionListener(a1 -> frame.dispose());
			component.addActionListener(a1 -> timer.stop());
			component.addActionListener(a1 -> new GameView(3));
			component.addActionListener(a1 -> timer.start());
		}

		/**
		 * The method is used to change the dimension to 3 with a grid of 9 x 9 buttons
		 * 
		 * @param component The button on which action listener is added.
		 * @param frame     The frame which is to be disposed when a new grid is
		 *                  created.
		 */
		public void changeFourDimension(JButton component, JFrame frame, Timer timer) {
			component.addActionListener(a1 -> frame.dispose());
			component.addActionListener(a1 -> timer.stop());
			component.addActionListener(a1 -> new GameView(4));
		}

			
		/** The method is used for generating the solution for 2X2 grid
		 * @return - grid solution 
		 */
		public int[][] generateTwoSolution() {
			gameModel = new GameModel(4,5);
			gameModel.generateFilledBoard();
			gridSolution = gameModel.getFilledBoard();
			gridSolution2 = gridSolution.clone();
			return gridSolution;
		}
		
		/**
		 * The method is used for generating the random board for 2X2 grid
		 * @return - grid board 
		 */
		public int[][] generateTwoBoard(){
			gameModel.generateEmptyBoard(gridSolution2);
			gridBoard = gameModel.getUnFilledBoard();
			return gridBoard;
		}
	

		/**
		 * The method is used for generating the solution for 3x3 grid
		 * @return - grid solution 
		 */
		public int[][] generateThreeSolution() {
			gameModel = new GameModel(9,20);
			gameModel.generateFilledBoard();
			gridSolution = gameModel.getFilledBoard();
			gridSolution2 = gridSolution.clone();
			return gridSolution;
		}
		
		/**
		 * The method is used for generating the random board for 3x3 grid
		 * @return - grid board 3x3
		 */
		public int[][] generateThreeBoard(){
			gameModel.generateEmptyBoard(gridSolution2);
			gridBoard = gameModel.getUnFilledBoard();
			return gridBoard;
		}
		
		/**
		 * The method is used for generating the solution for 4x4 grid
		 * @return - grid solution 
		 */
		public int[][] generateFourSolution() {
			gameModel = new GameModel(16,25);
			gameModel.generateFilledBoard();
			gridSolution = gameModel.getFilledBoard();
			gridSolution2 = gridSolution.clone();
			return gridSolution;
		}
		
		/**
		 * The method is used for generating the random board for 4x4 grid
		 * @return - grid board 4x4
		 */
		public int[][] generateFourBoard(){
			gameModel.generateEmptyBoard(gridSolution2);
			gridBoard = gameModel.getUnFilledBoard();
			return gridBoard;
		}
		
		/**
		 * This method generates the game
		 * @param button1 - R button 
		 * @param button2 - R button 
		 * @param i - local variable 
		 * @param j - local variable 2 
		 * @param gameBoard - game board 
		 * @param timer - timer of the game
		 */
		public void generateGame(JRadioButton button1, JButton button2, int i, int j, int[][] gameBoard, Timer timer) {
			String value;
			if(gameBoard[i][j] == 0) {
				value = " ";
				button1.addActionListener(a1 -> button2.setText(value));
			}
			else if(gameBoard[i][j] == 10) {
				button1.addActionListener(a1 -> button2.setText("A"));
				button1.addActionListener(a1 -> button2.setEnabled(false));
			}
			else if(gameBoard[i][j] == 11) {
				button1.addActionListener(a1 -> button2.setText("B"));
				button1.addActionListener(a1 -> button2.setEnabled(false));
			}
			else if(gameBoard[i][j] == 12) {
				button1.addActionListener(a1 -> button2.setText("C"));
				button1.addActionListener(a1 -> button2.setEnabled(false));
			}
			else if(gameBoard[i][j] == 13) {
				button1.addActionListener(a1 -> button2.setText("D"));
				button1.addActionListener(a1 -> button2.setEnabled(false));
			}
			else if(gameBoard[i][j] == 14) {
				button1.addActionListener(a1 -> button2.setText("E"));
				button1.addActionListener(a1 -> button2.setEnabled(false));
			}
			else if(gameBoard[i][j] == 15) {
				button1.addActionListener(a1 -> button2.setText("F"));
				button1.addActionListener(a1 -> button2.setEnabled(false));
			}
			else if(gameBoard[i][j] == 16) {
				button1.addActionListener(a1 -> button2.setText("G"));
				button1.addActionListener(a1 -> button2.setEnabled(false));
			}
			else {
				value = String.valueOf(gameBoard[i][j]);
				button1.addActionListener(a1 -> button2.setText(value));
				button1.addActionListener(a1 -> button2.setEnabled(false));
			}	
		}
		
		/**
		 * The method is used for generating solution 
		 * @param panel - Jpanel
		 * @param timer - Timer
		 */
		public void generateSolution(JPanel panel, Timer timer) {
			JDialog dialog = new JDialog();
			dialog.setTitle("Solution for current game.");
			dialog.setSize(600, 600);
			dialog.setContentPane(panel);
			dialog.setVisible(true);
			dialog.setResizable(false);
		}
		
		/**
		 * This method is used to show the solution of the game 
		 * @param button - button 1 
		 * @param menuItem - menu S
		 * @param panel - upper panel 
		 * @param timer - Timer 
		 */
		public void showSolution(JButton button, JMenuItem menuItem, JPanel panel, Timer timer) {
			button.addActionListener(a1 -> {
				generateSolution(panel, timer);
			});
			
			menuItem.addActionListener(a1 -> {
				generateSolution(panel, timer);
			});
		}
		
		/**
		 * The method id used for generating whole new game for two dimension
		 * @param menuItem1 - item 1 
		 * @param menuItem2 - item 2
		 * @param menuItem3 - item 3
		 * @param frame - menu frame 
		 */
		public void newGameTwoDim(JMenuItem menuItem1 , JMenuItem menuItem2, JMenuItem menuItem3, JFrame frame) {
			menuItem1.addActionListener(a1 -> frame.dispose());
			menuItem1.addActionListener(a1 -> new GameView(2));
			
			menuItem2.addActionListener(a1 -> frame.dispose());
			menuItem2.addActionListener(a1 -> new GameView(3));
			
			
			menuItem3.addActionListener(a1 -> frame.dispose());
			menuItem3.addActionListener(a1 -> new GameView(4));
		}
		
		
		//vpatel
		public void networkModelVC(JFrame gameView, JMenuItem connectionMenu, JMenuItem disconnectMenu, JTextArea userInput, JTextField netChat) {
			SudokuNetworkModel netModel = new SudokuNetworkModel(gameView);
			
			netChat.addActionListener(a1 -> {
				out.println(netChat.getText());
				netChat.setText(null);
			});
			
			connectionMenu.addActionListener(a1 -> {
				netModel.setLocationRelativeTo(null);
				netModel.setVisible(true);
				
				connectionStatus = false;
				
				if (netModel.pressedConnect()) {
					
					clientName = netModel.getName();
					serverAddress = netModel.getAddress();
					port = netModel.getPort();

					Thread t = new Thread() {

						public void run() {

							try {

								userInput.append(
										"Negotiating Connection to " + serverAddress + " on port " + port + "\n");

								client = new ClientClass(serverAddress, port, clientName);

								client.createClient();
								socket = client.getSocket();

								in = new Scanner(socket.getInputStream());
								out = new PrintWriter(socket.getOutputStream(), true);

								userInput.append("Connection successful.\n");
								userInput.append("Welcome to Sudoku Server.\n");
								userInput.append("Use '/help' for commands.\n");

								out.println(client.getName());// sends user name to server

								connectedClient = new NetControllerThread(userInput, connectionMenu, disconnectMenu, netChat);

								connectedClient.start();

								connectionStatus = true;

							} catch (IOException exc) {
								userInput.append(
										"Connection refused as the given \nhostname and port are invalid or not available:\n"
												+ exc.getMessage() + ", " + port);

								userInput.append("\nPlease, try again to connect. :)\n");
							}

							if (connectionStatus == true) {

								netChat.setEditable(true);
								
								disconnectMenu.setEnabled(true);
								
								connectionMenu.setEnabled(false);
							}
						}
					};

					t.start();
				}
			});
		}
		
		/**
		 * key listeners 
		 * @param frame - key frame 
		 */
		public void KeyFunctions(JFrame frame) {
			frame.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					/*
					 * New Game for two dimension
					 */
					if ((e.getKeyCode() == KeyEvent.VK_2) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
						frame.dispose();
						timer.stop();
						new GameView(2);
						timer.start();
					}
					/*
					 * New Game for three dimension
					 */
					if ((e.getKeyCode() == KeyEvent.VK_3) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
						frame.dispose();
						timer.stop();
						new GameView(3);
						timer.start();
					}
					/*
					 * New Game for four dimension
					 */
					if ((e.getKeyCode() == KeyEvent.VK_4) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
						frame.dispose();
						timer.stop();
						new GameView(4);
						timer.start();
					}
					/*
					 * Show Solution
					 */
					if ((e.getKeyCode() == KeyEvent.VK_S) && ((e.getModifiers() & KeyEvent.ALT_MASK) != 0)) {
						showSolution(null, null,  null, null);
					}
					
					if ((e.getKeyCode() == KeyEvent.VK_A) && ((e.getModifiers() & KeyEvent.ALT_MASK) != 0)) {
						aboutGame(null, null);
					}
					
					if ((e.getKeyCode() == KeyEvent.VK_E) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
						aboutGame(null, null);
					}
							
				}

				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub

				}

				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub

				}
			});
		}
		
		/**
		 * The method is used for exiting the game console
		 */
		
		public void exitPane() {

			JOptionPane exit = new JOptionPane();
			Object[] options = { "Yes", "No" };
			int number = JOptionPane.showOptionDialog(exit, "Are you sure you want to exit?", "Exit Game",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[1]);

			if (number == 0) {
				System.exit(0);
			}

		}
		
		/**
		 * The method exits the game
		 * @param button - menu button 
		 * @param menuItem - menu items 
		 */
		public void exitGame(JButton button, JMenuItem menuItem) {
			button.addActionListener(a1 -> exitPane());
			menuItem.addActionListener(a1 -> exitPane());
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
		
		public void getSelected(JComboBox<String> a) {
			a.addActionListener(a1 -> System.out.println( a.getSelectedItem().toString()));
		}


		//vpatel
		class NetControllerThread extends Thread {
			
			JTextArea userInput;
			JMenuItem connectionMenu;
			JMenuItem disconnectMenu;
			JTextField netChat;
			
			NetControllerThread(JTextArea userInput, JMenuItem connectionMenu, JMenuItem disconnectMenu, JTextField netChat){
				this.userInput = userInput;
				this.connectionMenu = connectionMenu;
				this.disconnectMenu = disconnectMenu;
				this.netChat = netChat;
				this.userInput.setEditable(false);
			}
			
			public void run() {

				while (!Thread.currentThread().isInterrupted() && in.hasNextLine()) {

					messageFromServer = in.nextLine();

					try {

						EventQueue.invokeAndWait(new Runnable() {

							@Override
							public void run() {

								if (messageFromServer != null && !messageFromServer.equals("")) {

									if (messageFromServer.toLowerCase().startsWith("[")
											&& messageFromServer.toLowerCase().endsWith("]]")) {

										gameModel.getGameFromNetwork(messageFromServer);
										setGameFromNetwork();
										
										
										//make your method or anything to reset game
										//picView.resetGameView();
										
					
									} else if (messageFromServer.equals("/bye")) {

										netChat.setText(null);
										netChat.setEditable(false);
										disconnectMenu.setEnabled(false);
										connectionMenu.setEnabled(true);
										
										userInput.requestFocus();
										
										return;
									} else if (messageFromServer.contains("SCORECMVPGAME")) {
										
									} else {
										userInput.append(messageFromServer + "\n");
									}
								}
							}
						});
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		
		public void setGameFromNetwork() {
			
			//here, put stuff which generates your hints and display
			//this.picModel.generateHints();
			//this.picView.hintLabels();
		}
	}