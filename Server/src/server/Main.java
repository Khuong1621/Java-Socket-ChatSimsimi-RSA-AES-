package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		
//		List all the clients thread
		ArrayList<ServerThread> threadList = new ArrayList<ServerThread>();
		try (ServerSocket serversocket = new ServerSocket(5000)) {
			while(true) {
				System.out.println("Server is running...");
				Socket socket = serversocket.accept();
				ServerThread serverThread = new ServerThread(socket, threadList);
				
//				Staring the thread
				threadList.add(serverThread);
				serverThread.start();
				
				
			}
		}
		catch (Exception e) {
			System.out.println("Error occured in main: " + e.getStackTrace());
		}
	}
}
