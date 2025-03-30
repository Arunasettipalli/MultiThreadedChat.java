import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5000);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to the server...");

            Thread listener = new Thread(() -> {
                String serverMessage;
                try {
                    while ((serverMessage = input.readLine()) != null) {
                        System.out.println("Server: " + serverMessage);
                    }
                } catch (IOException e) {
                    System.out.println("Connection closed.");
                }
            });
            listener.start();

            String userMessage;
            while ((userMessage = console.readLine()) != null) {
                output.println(userMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
