package de.bmt.boards;

import org.json.JSONObject;

import de.bmt.boards.network.MulticastServer;
import de.bmt.boards.network.TCPServer;

public class BMTBoardNetworkStarter {

	public final static String BOARD_PROGRAM_RUN = "run-board-program";

	private static int MESSAGE_ID = 1;
		
	/*
	 * @param arg[0] using udp or tcp protocol
	 * @param arg[1] network address (UDP Default: 225.1.2.3)
	 * @param arg[2] port (UDP Default: 5104; TCP default: 3125)
	 * @param arg[3] if defined the program name to start
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 4) {
				if (args[0].toLowerCase().equals("udp")) sendUdpNetworkCommand(args[1], Integer.valueOf(args[2]), getStartBoardProgram(args[3]));
				else if (args[0].toLowerCase().equals("tcp")) sendTcpNetworkCommand(args[1], Integer.valueOf(args[2]), getStartBoardProgram(args[3]));
			}
			else if (args.length == 3) {
				if (args[0].toLowerCase().equals("udp")) sendUdpNetworkCommand(args[1], Integer.valueOf(args[2]), getStopBoardProgramBroadcast());
				else if (args[0].toLowerCase().equals("tcp")) sendTcpNetworkCommand(args[1], Integer.valueOf(args[2]), getStopBoardProgramBroadcast());
			}
			else {
				System.out.println("3 or 4 arguments needed. Please see readme.txt...");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void sendTcpNetworkCommand(String ipAddress, int port, JSONObject message) {
		try {
			TCPServer tcpServer = new TCPServer(ipAddress, port);
			tcpServer.sendData(message.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void sendUdpNetworkCommand(String group, int port, JSONObject message) {
		try {
			MulticastServer mms = new MulticastServer(group, port);
			mms.sendData(message.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static JSONObject getStartBoardProgram(String programName) {
		JSONObject dataObj = new JSONObject();
		dataObj.put("messege-id", MESSAGE_ID);
		dataObj.put("message-origin-type", "APP");		
		dataObj.put("message-type", BOARD_PROGRAM_RUN);		
		dataObj.put("program-name", programName);
		dataObj.put("run-program", true);

		MESSAGE_ID++;
		
		return dataObj; 
	}
	
	private static JSONObject getStopBoardProgramBroadcast() {
		JSONObject dataObj = new JSONObject();
		dataObj.put("messege-id", MESSAGE_ID);
		dataObj.put("message-origin-type", "APP");		
		dataObj.put("message-type", BOARD_PROGRAM_RUN);		
		dataObj.put("program-name", "");
		dataObj.put("run-program", false);

		MESSAGE_ID++;
		return dataObj; 
	}
}
