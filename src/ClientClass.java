
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientClass {

    String serverAddress;
    Scanner in;
    PrintWriter out;
    String username;
    int port;
    Socket socket;

    public ClientClass(String serverAddress, int port, String username) {
        this.serverAddress = serverAddress;
        this.port = port;
        this.username = username;
    }
    
    public String getName() {
    	return this.username;
    }
    
    public Socket getSocket() {
    	return this.socket;
    }

    public void createClient() throws IOException {

        try {
        	//instantiates necessary connection and I/O stream that the client uses
            socket = new Socket(serverAddress, port);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(this.username);
        }
        finally {}
        //controller determines whether server is running before connecting, to avoid throwing exceptions.
    }
}
