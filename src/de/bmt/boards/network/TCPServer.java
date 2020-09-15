package de.bmt.boards.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class TCPServer implements Callable<String> {
	
	String ip = "";
	int port = 3125;
	String message = "";
	Socket socket;
		
	
	public TCPServer(String ip, int port) {
	  this.port = port;
	  this.ip = ip;
	}
	
	public String call() throws Exception {
	  return requestServer(message);
	}
	
	/*
	 * @param message the message sending over TCP/IP
	 * 
	 * open socket
	 * send message
	 * receive result
	 * close socket
	 * Socket öffnen, Anforderung senden, Ergebnis empfangen, Socket schliessen
	 */
	private String requestServer(String message) throws IOException {
		
		String receivedMessage;
		
		socket = new Socket(ip, port);  
		sendMessage(socket, message);
		
		receivedMessage = receiveMessage(socket);

		socket.close();
		return receivedMessage;
	}
	
	private void sendMessage(Socket socket, String nachricht) throws IOException {
	  PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	  printWriter.print(nachricht);
	  printWriter.flush();
	  printWriter.close();
	}
	
	private String receiveMessage(Socket socket) throws IOException {
	  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	  char[] buffer = new char[100];
	  int anzahlZeichen = bufferedReader.read(buffer, 0, 100);
	  String nachricht = new String(buffer, 0, anzahlZeichen);
	  return nachricht;
	}

	/*
	 * @param message the message sending over TCP/IP
	 */
	public void sendData(String message) {
		try {
			this.message = message;
			FutureTask<String> ft = new FutureTask<String>(this);
			Thread tft = new Thread(ft);
			tft.start();
			tft.join(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}