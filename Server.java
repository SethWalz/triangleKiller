import java.util.*;
import java.io.*;
import java.net.*;

public class Server {

	private static final int PORT = 8000;
    private static HashSet<Double> players = new HashSet<>();

    public static void main(String[] args) {
        System.out.println("Server online.");

        try (ServerSocket chatServer = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = chatServer.accept();
                new Handler(socket).start();
            }
        } catch (IOException ioe) {}
    }

    private static class Handler extends Thread {
        private Socket socket;
        private BufferedReader in;
        private PrintWriter out;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);


                
            } catch (IOException e) {

            } finally {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }

    
}
