Online Chat Application

Author: Samera
Date: 2025-10-22

Description

This is a simple online chat application developed in Java.
It allows multiple users to connect to a central server, send messages,
and receive messages from other users in real-time.

The application demonstrates basic socket programming, client-server communication,
and a text-based user interface.



How to Run
1.	Compile the program:

javac ChatApp.java

2.	Start the server:

java ChatApp server

3.	Start each client in a separate terminal or console:

java ChatApp client

4.	Enter your name when prompted and start chatting.
5.	Send messages by typing and pressing Enter.
6.	Exit the chat by typing exit.



Files
	•	ChatApp.java – Contains both the server and client implementation in a single file.



Notes
	•	Make sure to start the server first before connecting clients.
	•	Each client runs on a separate thread to allow simultaneous messaging.
	•	The server broadcasts messages to all connected clients except the sender.
	•	The application is console-based and demonstrates multi-threaded client-server communication in Java.

