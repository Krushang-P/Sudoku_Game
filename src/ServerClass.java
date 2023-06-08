import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;


public class ServerClass {

	// two vectors - one for client names, one for PrintWriters associated with each client
	private static Vector<String> clients = new Vector<String>();
	private static Vector<PrintWriter> clientWriters = new Vector<PrintWriter>();
	
	private static Vector<String> scoreTable = new Vector<String>();
	private static Vector<String> gameStorage = new Vector<String>();

	private static String[] getScore;
	private static String playerNameAndTime;
	private static int uploadedScore;
	private static int port;

	private JFrame frame = new JFrame("Sudoku Server!");

	private JPanel p = new JPanel();

	private static JTextArea serverTextArea = new JTextArea();

	private JScrollPane scrollPane = new JScrollPane(serverTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	private static String gameFromNetwork = "";

	private static int cliNumber = 0;

	private JLabel sudokuLogo = new JLabel(new ImageIcon(getClass().getResource("sudoku_logo_button.png")));

	//creates the server GUI
	public ServerClass() {

		p.setLayout(new BorderLayout(20, 25));

		serverTextArea.setBackground(new Color(0xFCF7CE));

		scrollPane.setPreferredSize(new Dimension(490, 600));

		sudokuLogo.setOpaque(false);

		serverTextArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		serverTextArea.setBorder(new EtchedBorder(15));

		scrollPane.setBorder(new EtchedBorder(15));

		serverTextArea.setVisible(true);
		scrollPane.setVisible(true);
		serverTextArea.setEditable(false);

		p.setPreferredSize(new Dimension(500, 670));
		sudokuLogo.setBorder(new EtchedBorder(33));
		p.add(sudokuLogo, BorderLayout.NORTH);
		p.add(scrollPane, BorderLayout.CENTER);
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		p.setBackground(new Color(0x9BDED2));

		frame.add(p);
		frame.setSize(570, 670);
		frame.setBackground(new Color(0xBAF1F4));
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
	}

	//Creates the server
	//listens for data from clients by using ClientHander
	public static void main(String[] args) throws IOException {

		if (args.length != 1) {// need to type one arg (port) to start server
			System.out.println("Error. Please enter port number as a command line argument.");
			System.exit(0);
		} else {

			try {
				//get port number from command line
				port = Integer.parseInt(args[0]);

				if (port < 10000 || port >= 65536) {// port input validation (accepts only between 10000 to 65535)
					throw new Exception();
				}
			} catch (Exception e) {
				// if not valid port -> set default port to 61001
				System.out.println("ERROR: Invalid port number.");
				port = 61001;
				System.out.println("Using default port: " + port);
			}
		}

		try (ServerSocket listener = new ServerSocket(port)) {

			ServerClass ps = new ServerClass();

			ps.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			ServerClass.serverTextArea.append("Now listening on port: " + port + "\n");

			//starts ClientHandler on a different thread
			while (true) {
				Runnable r = new ClientHandler(listener.accept());
				Thread t = new Thread(r);
				t.start();
			}
		} catch (BindException netErr) {
			// If port is in use, throw error
			System.out.println("ERROR: Port is in use.\n" + netErr.getMessage());
			System.out.println("\nPlease try again with a different port.");
		}
	}

	//message broadcasting from server to all connected client
	public static void messageBroadcast(String message) {

		for (PrintWriter pw : clientWriters) {
			pw.println(message);
		}
	}

	//This class listens for incoming messages to the server on a separate thread to prevent blocking 
	//Simply handles the client input for one server socket connection).
	static class ClientHandler implements Runnable {
		private Socket incoming;
		private Scanner in;
		private PrintWriter out;
		private String name;

		public ClientHandler(Socket socket) {
			this.incoming = socket;
		}

		public void run() {

			try {
				// create server I/O streams
				InputStream inStream = incoming.getInputStream();
				OutputStream outStream = incoming.getOutputStream();

				in = new Scanner(inStream);
				out = new PrintWriter(outStream, true);

				name = in.nextLine();

				clients.add(name);// adds new user in server user list. Stores username

				++cliNumber;
				
				serverTextArea.append("Inbound connection #" + cliNumber + "\n");

				messageBroadcast("SERVER: " + name + " has connected.");

				clientWriters.add(out);//ads new user to PrintWriter vector, which is used by server to write messages

				serverTextArea.append(name + " has connected." + "\n");

				in.nextLine();

				boolean done = false;

				while (!done && in.hasNextLine()) {

					String input = in.nextLine();

					if (input.toLowerCase().equals("/bye")) {

						out.println("/bye");
						done = true;
					}
					else if (input.toLowerCase().startsWith("[") && input.toLowerCase().endsWith("]]")) {

						gameFromNetwork = input;
						gameStorage.add(gameFromNetwork);
					}
					else if (input.contains("SCORECMVPGAME")) {

						getScore = input.split(",");
						playerNameAndTime = getScore[1] + ", " + getScore[2];
						uploadedScore = Integer.parseInt(getScore[3]);

						String scoreFormat = getScore[1] + ",\t" + getScore[2] + ",\t" + getScore[3];
						
						scoreTable.add(scoreFormat);
						serverTextArea.append("New game received!\n");
						serverTextArea.append("Score table updated with " + playerNameAndTime + ", " + uploadedScore + ".\n");
					} else if (input.toLowerCase().equals("/get")) {

						if(gameFromNetwork == "") {
							out.println("Nothing to load. Upload it first!\n");
							out.println("Try /show or /help command.\n");
						}
						else {
							out.println(gameFromNetwork);
							out.flush();
						}
					} else {

						messageBroadcast(name + ": " + input);
					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {

				if (out != null) {
					clientWriters.remove(out);//removes output streams which connected with particular clients
				}

				if (name != null) {
					serverTextArea.append(name + " has disconnected" + "\n");
					clients.remove(name);// user removed from clients vector
					messageBroadcast("SERVER: " + name + " has left.");
				}

				try {
					incoming.close();
				} catch (IOException e) {
					messageBroadcast("SERVER: Error while disconnecting.\n" + e.getMessage());
					serverTextArea.append("\nError while disconnecting.\n" + e.getMessage() + "\n");
				}
			}
		}
	}
}
