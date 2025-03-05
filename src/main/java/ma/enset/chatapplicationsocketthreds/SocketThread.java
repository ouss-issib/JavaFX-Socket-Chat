package ma.enset.chatapplicationsocketthreds;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class SocketThread extends Thread {
    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;
    private List<SocketThread> clients;
    private String username;

    public SocketThread(Socket socket, List<SocketThread> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);

            // Read username from client
            username = br.readLine();
            System.out.println(username + " has joined the chat.");
            broadcastMessage("🟢 " + username + " a rejoint la conversation.");

            String msg;
            while ((msg = br.readLine()) != null) {
                if (msg.equalsIgnoreCase("bye")) {
                    broadcastMessage("🔴 " + username + " a quitté la conversation.");
                    break;
                }
                System.out.println(username + ": " + msg);
                broadcastMessage(username + ": " + msg);
            }
        } catch (IOException e) {
            System.out.println(username + " disconnected.");
        } finally {
            closeConnection();
        }
    }

    public void sendMessage(String message) {
        if (pw != null) {
            pw.println(message);
        }
    }

    private void broadcastMessage(String message) {
        for (SocketThread client : clients) {
            if (client != this) {
                client.sendMessage(message);
            }
        }
    }

    private void closeConnection() {
        try {
            if (br != null) br.close();
            if (pw != null) pw.close();
            if (socket != null) socket.close();
            clients.remove(this);

            // If no users are left, stop the server
            if (clients.isEmpty()) {
                System.out.println("Tous les utilisateurs sont déconnectés. Fermeture du serveur...");
                System.exit(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
