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
            broadcastMessage("🟢 " + username + " has joined the chat.");

            String msg;
            while ((msg = br.readLine()) != null) {
                System.out.println(username + ": " + msg);
                broadcastMessage(username + ": " + msg);
            }
        } catch (IOException e) {
            System.out.println(username + " disconnected.");
        } finally {
            try {
                if (br != null) br.close();
                if (pw != null) pw.close();
                if (socket != null) socket.close();
                clients.remove(this);
                broadcastMessage("🔴 " + username + " has left the chat.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        if (pw != null) {
            pw.println(message);
        }
    }

    private void broadcastMessage(String message) {
        for (SocketThread client : clients) {
            if (client != this) {  // Don't send the message to the sender
                client.sendMessage(message);
            }
        }
    }
}
