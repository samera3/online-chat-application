Online Chat Application

Author: Samera
Date: 2025-10-22

Overview

This chat application consists of a server and client implementation in a single Java file (ChatApp.java).
The server handles multiple client connections, allowing users to send and receive messages in real-time.
Clients connect to the server and can send messages to other connected clients using a text-based interface.



Features
	•	Server:
	•	Listens for incoming client connections on port 12345.
	•	Handles multiple clients simultaneously using a separate thread for each client (ClientHandler inner class).
	•	Assigns a unique name automatically to each connected client (e.g., User1, User2, etc.).
	•	Broadcasts messages received from one client to all other connected clients.
	•	Removes disconnected clients from the active client set (no advanced error handling).
	•	Client:
	•	Connects to the server on localhost:12345.
	•	Receives a unique name automatically from the server.
	•	Sends messages to the server.
	•	Receives and displays messages from other clients in real-time.


Requirements
	•	Java Development Kit (JDK 8 or higher)
	•	Command-line interface (CLI) for running Java commands


Getting Started

1. Compile the Code

javac ChatApp.java

2. Run the Server

java ChatApp server

Expected output:

Chat Server started...

3. Run Clients

Open one or more additional terminals.
For each client, run:

java ChatApp client

	•	Each client will automatically receive a unique name from the server.
	•	After receiving the name, clients can start sending messages.

4. Send Messages
	•	Type messages in one client’s terminal and press Enter.
	•	Messages appear in all other connected clients’ terminals.

5. Disconnect a Client
	•	Close the client terminal to disconnect.
	•	The server automatically removes the disconnected client from its active set.


Implementation Details

Server
	•	Purpose: Handles client connections and broadcasts messages to all other clients.
	•	Key Components:
	•	ServerSocket listens for incoming connections.
	•	ClientHandler inner class handles each client in a separate thread.
	•	Set<ClientHandler> stores all connected clients.
	•	broadcast() method sends messages to all clients except the sender.
	•	clientCount variable is used to assign unique names automatically.

Client
	•	Purpose: Connects to the server and allows sending and receiving messages.
	•	Key Components:
	•	Socket connects to the server.
	•	BufferedReader and PrintWriter handle input/output streams.
	•	Background thread continuously reads messages from the server while the main thread handles user input.
	•	Client receives a unique name automatically from the server.


Troubleshooting
	•	No Connection: Ensure the server is running and the client connects to localhost:12345.
	•	No Messages: Verify that messages are sent and the server broadcasts them correctly.
	•	Client Disconnection: Closing a client terminal removes it from the server automatically.


Notes
	•	All functionality is implemented in a single file (ChatApp.java).
	•	The application demonstrates multi-threaded client-server communication in Java.
	•	The interface is console-based, text-only.

