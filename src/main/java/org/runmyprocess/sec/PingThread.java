package org.runmyprocess.sec;

import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

import org.runmyprocess.json.JSONObject;

/**
 *
 * @author Malcolm Haslam <mhaslam@runmyprocess.com>
 *
 * Copyright (C) 2013 Fujitsu RunMyProcess
 *
 * This file is part of RunMyProcess SEC.
 *
 * RunMyProcess SEC is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License Version 2.0 (the "License");
 *
 *   You may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
public class PingThread extends Thread {
	
	private JSONObject configInfo;
	private static final byte[] CRLF = {'\r','\n'};
	
	/**
	 * Constructor that sets the configuration Object 
	 * @param configInfo
	 */
	public PingThread(JSONObject configInfo) {
    	this.configInfo = configInfo;
	}

	/**
	 * Pings the host on the configured port with all the connection information
	 */
	public void run() {
		while(true){
			OutputStream os = null;
			try {
				// formulate the request ...
				Thread.sleep(this.configInfo.getInteger("pingFrequency"));
				ByteArrayOutputStream baos = new ByteArrayOutputStream ();
				JSONObject pingData = new JSONObject();
				pingData.put("protocol",this.configInfo.getString("protocolName"));
				pingData.put("host",this.configInfo.getString("handlerHost"));
				pingData.put("port",this.configInfo.getInteger("connectionPort"));
				baos.write(pingData.toString().getBytes("UTF-8"));
				baos.write(CRLF);
				baos.flush();
				Socket socket;
			    try {
			    	socket = new Socket(this.configInfo.getString("managerHost"), this.configInfo.getInteger("managerPort"));
					os = socket.getOutputStream();
					os.write(baos.toByteArray());
					os.flush();
					baos.close();
					socket.close();
			    } catch (ConnectException ex) {
			        Logger.getLogger(PingThread.class.getName()).log(Level.WARNING, null, ex);
			        //the manger is not responding...insert an action to take here!
			    } catch (IOException ex) {
			        Logger.getLogger(PingThread.class.getName()).log(Level.SEVERE, null, ex); 
			    }

			} catch (Exception ex) {
				Logger.getLogger(PingThread.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
    }
}