package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Base64;
import java.util.StringTokenizer;

public class ServerThread extends Thread {
	private Socket socket;
	private ArrayList<ServerThread> threadList;
	private PrintWriter output;
	private String keyAES;
	
	public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
		this.socket = socket;
		this.threadList = threads;
	}
	
	@Override
	public void run() {
		try {
//			Reading input from client
			BufferedReader input = new BufferedReader( new InputStreamReader(socket.getInputStream()));

			
//			returning the output to the client : true statement is to flush the buffer otherwise we have to do it manually
			output = new PrintWriter(socket.getOutputStream(), true);

			
			//Create public key and private key
			GenerateKeys key = new GenerateKeys(1024);
			key.createKeys();
			
			//Send public key to client
			String publicKey = Base64.getEncoder().encodeToString(key.getPublicByte());
			output.println(publicKey);
			
			
			
			//Received keyAES
			String enKey = input.readLine();
			//System.out.println("enKey: "+enKey);
			
			//Decrypt keyAES by privatekey
			keyAES = RSA.decrypt(enKey, key.getPrivateByte());
			//System.out.println(keyAES);
			
			
//			inifite loop for server
			while(true) {
				String userInput = input.readLine();
				userInput = AES.decrypt(userInput, keyAES);
				
//				Decoding userInput
//				byte[] decodedBytes = Base64.getDecoder().decode(userInput);
//				userInput = new String(decodedBytes);
				//System.out.println(userInput);
//				Check command
				StringTokenizer st = new StringTokenizer(userInput, " ");
				String command = st.nextToken();
				String value = "";
				String response = "";
				
				while(st.hasMoreTokens()) {
					value += st.nextToken() + " ";
				}
				
				//userInput = command + value
				
				switch(command) {
				case "/ip":
					Ip ip = new Ip(value);
					response = ip.getResponse();
					break;
				case "/whois":
					Whois whois = new Whois(value);
					response =  whois.getResponse();
					break;
				case "/weather":
					Weather weather = new Weather(value);
					response = weather.getResponse();
					break;
				case "/scan":
					ScanPort scan = new ScanPort(value);
					response = scan.getResponse();
					break;
				case "/swap":
					Currency cur = new Currency(value);
					response = cur.getResponse();
					break;
				case "/help":
					response = "Danh s??ch c??u l???nh \n"
							+ "/ip [S??? Ip] -- Tra c???u ?????a ??i???m ip \n"
							+ "/whois [T??n mi???n] -- Tr??? v??? th??ng tin t??n mi???n \n"
							+ "/swap [M?? ti???n t??? A]:[M?? ti???n t??? B]:[S??? ti???n] -- Quy ?????i ti???n A sang B :\n"
							+ "/weather [?????a ??i???m] -- Tr??? v??? th???i ti???t ??? ?????a ??i???m ???? v?? 7 ng??y ti???p theo\n"
							+ "/scan [S??? Ip]:[start port]:[end port] -- Tr??? v??? port ??ang m??? trong gi??? h???n t??? start ?????n end x:y\n";
					break;
				default :
					Simsimi simsimi = new Simsimi(userInput);
					response = simsimi.getResponse();
					break;
					
				}
				
				
				response = AES.encrypt(response, keyAES);
				
				// Send response to client
				output.println(response);
			}
		} catch (Exception e) {
			System.err.println("Client closed");
			try {
				socket.close();
			} catch (IOException e1) {
				System.err.println(e1);;
			
			}
		}
	}
	
//	private void printToAllClient(String outputString) {
//		for(ServerThread sT: threadList) {
//			sT.output.println(outputString);
//		}
//	}
	
	
}
