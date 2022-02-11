/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package guichatclient;

import java.awt.Font;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Base64;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.text.DefaultCaret;

import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author DELL
 */
public class ClientAIChatBox extends JFrame {
	// Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btn_send;
    private JScrollPane jScrollPane1;
    private JLabel lb_title;
    private JTextField txt_chat;
    private JTextPane txt_pkhungchat;
    // End of variables declaration//GEN-END:variables
    
//    public Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String userInput;
	private String response;
	private String keyAES;
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * Creates new form ClientAIChatBox
     */
    public ClientAIChatBox() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new JScrollPane();
        txt_pkhungchat = new JTextPane(); 
        lb_title = new JLabel();
        btn_send = new JButton();
        txt_chat = new JTextField();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        

        txt_pkhungchat.setEnabled(true);
        txt_pkhungchat.setAutoscrolls(true);
        txt_pkhungchat.setEditable(false);
        txt_pkhungchat.setFont(new Font("Times New Roman", 1, 18));
        jScrollPane1.setViewportView(txt_pkhungchat);
        DefaultCaret caret = (DefaultCaret)txt_pkhungchat.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        lb_title.setFont(new Font("Times New Roman", 1, 36)); // NOI18N
        lb_title.setText("CHAT BOT 7");
        lb_title.setVerticalAlignment(SwingConstants.CENTER);

        btn_send.setText("Send");
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });

        txt_chat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_chatActionPerformed(evt);
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1))
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(lb_title)
                        .addGap(0, 96, Short.MAX_VALUE))
                    .addGroup(GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txt_chat)
                        .addGap(18, 18, 18)
                        .addComponent(btn_send, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(lb_title)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_chat, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_send, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
    	try {
//        	get text user
        	userInput = txt_chat.getText();
        	System.out.println("Client: "+userInput);
        	
        	txt_pkhungchat.setText(txt_pkhungchat.getText()+ "\n" +"Client: "+ userInput);
        	txt_chat.setText("");
        	
        	//encrypt userInput
        	userInput = AES.encrypt(userInput, keyAES);
        	
//    		send to server
    		output.println(userInput);
            
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    }//GEN-LAST:event_btn_sendActionPerformed

    private void txt_chatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_chatActionPerformed
//    	get text user
    	userInput = txt_chat.getText();
    	System.out.println("Client: "+userInput);
    	
    	txt_pkhungchat.setText(txt_pkhungchat.getText()+ "\n" +"Client: "+ userInput);
    	txt_chat.setText("");
    	
    	//encrypt userInput
    	userInput = AES.encrypt(userInput, keyAES);
    	
//		send to server
		output.println(userInput);
    }//GEN-LAST:event_txt_chatActionPerformed

    private void connectSocket(String address, int port) {
    	try(Socket socket = new Socket(address, port)) {
    		
//			reading input from server
			input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			

//			returning the output to the server : true statement is to flush the buffer otherwise we have to do it manually
			output = new PrintWriter(socket.getOutputStream(), true);
			

			
			//Create key AES
			keyAES = RandomStringUtils.random(16);
			System.out.println("key AES: " + keyAES);
			
			//Received public key
			String publicKey = input.readLine();
			byte[] publicKeyByte = Base64.getDecoder().decode(publicKey);
			
			
			//Encrypt key AES by public key
			String enKey = RSA.encrypt(keyAES, publicKeyByte);
			System.out.println("enKey: "+enKey);
			
    		//Send key AES to server
			output.println(enKey);
    		
			
			//connect success
			txt_pkhungchat.setText("Bot đã sẵn sàng.");
			
//			taking the user input
			while(true) {
				
//				get response from server
				response = input.readLine();
				
				// decrypt response
				response = AES.decrypt(response, keyAES);
				
//				Show response
				System.out.println("Server: "+response);
				txt_pkhungchat.setText(txt_pkhungchat.getText() + "\n"+ "BOT:  " + response);
//				
			}
        } catch (Exception e) {
        	System.out.println(e.getStackTrace());
        	txt_pkhungchat.setText("Không thể kết nối với server");
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        ClientAIChatBox a = new ClientAIChatBox();
		a.setVisible(true);
        a.setLocation(300,100);
		a.setSize(800,600);
		
		a.connectSocket("localhost", 5000);
        
    }

    
}
