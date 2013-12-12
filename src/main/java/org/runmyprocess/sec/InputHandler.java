
package org.runmyprocess.sec;

import java.util.logging.Level;
import java.util.logging.Logger;

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
public class InputHandler implements InputHandlerInterface{

	private Response response  = new Response();
	
	@Override
	/**
	 * Overrides acceptJson function of the input handler to
	 * create a new instance of a configured Adapter and manage
	 * data to/from it.
	 * @param jsonObject JSONObject with the Adapters information and data
	 */
	public void acceptJson(JSONObject jsonObject) throws Exception {
			try {
				String className = jsonObject.getString("protocolClass");// passed in from config
				String configPath = jsonObject.getString("protocolConfigPath");
				ProtocolInterface protocolInterface;
				protocolInterface = (ProtocolInterface)Class.forName(className).newInstance();
				protocolInterface.accept(jsonObject.getJSONObject("data"),configPath);
				response = protocolInterface.getResponse();
			} catch (InstantiationException ex) {
				// TODO Auto-generated catch block
				Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
				throw new Exception(ex); 
			} catch (IllegalAccessException ex) {
				// TODO Auto-generated catch block
				Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
				throw new Exception(ex); 
			} catch (ClassNotFoundException ex) {
				// TODO Auto-generated catch block
				Logger.getLogger(InputHandler.class.getName()).log(Level.SEVERE, null, ex);
				throw new Exception(ex); 
			}

	}
	/**
	 * gets the response object previously set
	 */
	public Response getResponse(){
		return response;
	}
	/**
	 * unused overridden function that returns null
	 */
	@Override
	public JSONObject getProtocolList() {
		// TODO Auto-generated method stub
		return null;
	}


}