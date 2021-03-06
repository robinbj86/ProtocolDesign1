package com.aalto.protocol.design.iotps.start;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.concurrent.ConcurrentHashMap;

import com.aalto.protocol.design.iotps.db.engine.SQLiteDBEngine;
import com.aalto.protocol.design.iotps.udp.engine.ServerToClientUDPEngine;
import com.aalto.protocol.design.iotps.udp.engine.ServerToSensorUDPEngine;


public class IoTPSServerStarter {

	public static int publishPort;   // 5060
	
	public static int subscribePort; // 5061
	
	public static int updatePort; // 5062
	
	public static int version = 1; 

	public static boolean isCongestionControlSupported = false;
	
	public static ConcurrentHashMap<String,Long> subscriptionIdSeqNumMap = new ConcurrentHashMap<String,Long>();
	
	public static String getSelfIP() {
		try {
			return(Inet4Address.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
			return "127.0.0.1"; // To be changed later
		}
	}

	/* 			Arguments to the program
	 * 			java IoTPSServerStarter publishPort subscribePort updatePort version
	 */
	
	public static void main(String[] args) {
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() { 
				System.out.println("Received ctrl+c: shutting down"); 
			}
		});

		if (args.length < 4) {
			System.err.println("Incorrect number of arguments! - Exiting program");
			System.exit(-1);
		}
		
		
		publishPort = Integer.parseInt(args[0]);
		subscribePort = Integer.parseInt(args[1]);
		updatePort = Integer.parseInt(args[2]);
		version = Integer.parseInt(args[3]);
		
		String dbUrl = args[4];
		
		if(null != dbUrl && !"".equals(dbUrl.trim())) {
			SQLiteDBEngine.DB_URL = dbUrl;
		}
		
		if(version == 1) {
			isCongestionControlSupported = false;
		} else if (version > 1) {
			isCongestionControlSupported = true;
		}
		
		
		Thread clientListenThread = new Thread(new Runnable() 
		{ 
			public void run() {
				try {
					ServerToClientUDPEngine.listenForClientMessages(subscribePort);
					System.out.println("Started listening to client Messages in subscribe port:: "+ subscribePort);
				} catch (Exception e) {
					System.err.println("Failed to listen to client Messages in subscribe port:: "+ subscribePort+" - Exiting program");
					e.printStackTrace();
					System.exit(-1);
				}
			} 
		});
		clientListenThread.start();

		Thread sensorListenThread = new Thread(new Runnable() 
		{ 
			public void run() 
			{
				try {
					ServerToSensorUDPEngine.listenForSensorMessages(publishPort);
					System.out.println("Started listening to sensor Messages in publish port:: "+ publishPort);
				} catch (Exception e) {
					System.err.println("Failed to listen to sensor Messages in publish port:: "+ publishPort+" - Exiting program");
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		sensorListenThread.start();
		System.out.println("Starting server successfull");
	}
	
	
}
