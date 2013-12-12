package org.runmyprocess.sec;

import java.util.logging.Level;
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
public class GenericHandler {

	/**
	 * Sets up manager connections and runs the handler class
	 * @param config handler's config files
	 * @throws java.io.IOException
	 */
    public void run(Config config)throws java.io.IOException{

    	InputHandler inputHandler = new InputHandler();
    	JSONObject configInfo = new JSONObject();
		try{
			
    		configInfo.put("protocolClass",config.getProperty("protocolClass"));
    		
    		configInfo.put("connectionPort", Integer.parseInt(config.getProperty("connectionPort")));
    		configInfo.put("protocolName",config.getProperty("protocol"));
    		configInfo.put("handlerHost",config.getProperty("handlerHost"));
  
    		configInfo.put("managerHost",config.getProperty("managerHost"));
    		configInfo.put("managerPort",Integer.parseInt(config.getProperty("managerPort")));
    		configInfo.put("pingFrequency",Integer.parseInt(config.getProperty("pingFrequency")));
    		
		}catch (NullPointerException e) {
        	SECErrorManager errorManager = new SECErrorManager();
        	errorManager.logError(e.getMessage(), Level.SEVERE);
		}
    	//LISTEN TO PORT
    	try{
            new HandlerListenerThread(inputHandler,configInfo.getInteger("connectionPort"), configInfo.getString("protocolClass")).start();//Listen to input port
        	} catch( Exception e ) {
            	SECErrorManager errorManager = new SECErrorManager();
            	errorManager.logError(e.getMessage(), Level.SEVERE);
        	}
    	
    	//PING PROTOCOL MANAGER  
		try{
			new PingThread(configInfo).start();
    	}catch( Exception e ){
        	SECErrorManager errorManager = new SECErrorManager();
        	errorManager.logError(e.getMessage(), Level.SEVERE);
    		}
		
        }

}
