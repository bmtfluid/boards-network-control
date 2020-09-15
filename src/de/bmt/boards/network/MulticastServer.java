package de.bmt.boards.network;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class MulticastServer {
						
	private int port = 5104;
	private String group = "225.1.2.3";
		
	DatagramSocket senderSocket = null;
	MulticastSocket receiverSocket = null;
	
	String ownIp = "";
	
	public MulticastServer(String group, int port) {
		this(group, port, "");
	}
	
	public MulticastServer(String group, int port, String ownIp) {
		this.group = group;
		this.port = port;
		this.ownIp = ownIp;
		startNetwork();
	}
	
	private void startNetwork() {
		try {
			if (ownIp.equals("")) setOwnIp();
			
			senderSocket = new DatagramSocket();
			receiverSocket = new MulticastSocket(port);

			receiverSocket.setReuseAddress(true);
			receiverSocket.setSoTimeout(0);
			receiverSocket.joinGroup(InetAddress.getByName(group));		
						
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private void setOwnIp() {
		try {
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements()) {
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    Enumeration<InetAddress> ee = n.getInetAddresses();
			    while (ee.hasMoreElements()) {
			        InetAddress i = (InetAddress) ee.nextElement();
			        if (!i.getHostAddress().startsWith("127.") && !i.getHostAddress().contains(":")) ownIp = i.getHostAddress();
			    }
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
	}
	
	public void sendData(final String text) {
		  Thread thread = new Thread() {
		    public void run(){
				try {
					byte[] b = text.getBytes("UTF-8");
					DatagramPacket dgram = new DatagramPacket(b, b.length, InetAddress.getByName(group), port);
	
					senderSocket.send(dgram);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		  };
		  thread.start();
	}
}
