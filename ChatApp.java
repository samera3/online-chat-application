

// ChatApp.java
// Full chat server & client in one file

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class ChatApp {
    private static final int PORT = 1234;

    // Entry point: choose mode (server or client)
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("  java ChatApp server   → start the chat server");
            System.out.println("  java ChatApp client   → start a chat client");
            return;
        }

        if (args[0].equalsIgnoreCase("server")) {
            new ChatServer().startServer();
        } else if (args[0].equalsIgnoreCase("client")) {
            new ChatClient("localhost", PORT).startClient();
        } else {
            System.out.println("Invalid argument. Use 'server' or 'client'.");
        }
    }
}

// ==================== SERVER SECTION ====================
class ChatServer {
    private static int clientIdCounter = 1;
    private static final Set<ClientHandler> clients = ConcurrentHashMap.newKeySet();

    public void startServer() {
        System.out.println("Chat Server started...");
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                String userId = "User" + clientIdCounter++;
                System.out.println(userId + " connected.");

                ClientHandler handler = new ClientHandler(clientSocket, userId);
                clients.add(handler);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) client.sendMessage(message);
        }
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client);
        System.out.println(client.getUserId() + " disconnected.");
    }
}

// ==================== CLIENT HANDLER SECTION ====================
class ClientHandler implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private String userId;

    public ClientHandler(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println("Welcome " + userId + "! You can now start chatting.");

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(userId + ": " + message);
                ChatServer.broadcast(userId + ": " + message, this);
            }

        } catch (IOException e) {
            System.out.println("Connection lost with " + userId);
        } finally {
            ChatServer.removeClient(this);
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing connection for " + userId);
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}

// ==================== CLIENT SECTION ====================
class ChatClient {
    private String serverAddress;
    private int port;

    public ChatClient(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }

    public void startClient() {
        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connected to chat server!");
            new Thread(() -> {
                try {
                    String serverMsg;
                    while ((serverMsg = input.readLine()) != null) {
                        System.out.println(serverMsg);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server.");
                }
            }).start();

            String msg;
            while (true) {
                msg = consoleInput.readLine();
                if (msg.equalsIgnoreCase("exit")) {
                    System.out.println("You left the chat.");
                    break;
                }
                output.println(msg);
            }
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
        }
    }
}
