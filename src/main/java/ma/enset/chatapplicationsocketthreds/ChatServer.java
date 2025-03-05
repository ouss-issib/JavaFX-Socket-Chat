package ma.enset.chatapplicationsocketthreds;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private static final int PORT = 9091;
    private static final List<SocketThread> clients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Chat Server started on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected.");

                // Create a new thread for each client
                SocketThread clientThread = new SocketThread(clientSocket, clients);
                clients.add(clientThread);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void broadcastMessage(String message, SocketThread sender) {
        for (SocketThread client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}
