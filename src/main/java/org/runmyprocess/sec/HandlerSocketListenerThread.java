package org.runmyprocess.sec;

import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;

import org.runmyprocess.json.JSON;
import org.runmyprocess.json.JSONObject;
import org.runmyprocess.json.parser.StreamParser;

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
public class HandlerSocketListenerThread extends Thread {
    private Socket socket = null;
    private InputHandlerInterface listener;
    private String protocolClass;

    /**
     * Sets the socket parameters to listen for the manager
     * @param socket
     * @param protocolClass
     * @param listener
     */
    public HandlerSocketListenerThread(Socket socket,String protocolClass, InputHandlerInterface listener) {
        super("SocketListenerThread");
        this.socket = socket;
        this.listener = listener;
        this.protocolClass = protocolClass;
    }

    /**
     * Start listening to the configured socket for information sent from the manager
     */
    public void run() {
    	Response response=null;
        try {
            JSON result = JSON.parse(new StreamParser(socket.getInputStream(), "UTF-8"));//gets the body and parses it to JSON
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("data",result.toJSONObject());//Converts JSON to JSONOBJECT and adds to data
            jsonRequest.put("protocolClass", this.protocolClass);
            this.listener.acceptJson(jsonRequest);
            response = listener.getResponse();
            if (response.getStatus() == 0){//if Status is not set in the handler it is assumed that everything is OK 
            	response.setStatus(200);
            }
            
        } catch (Exception ex) {
        	SECErrorManager exceptionError = new SECErrorManager();
        	exceptionError.setMessage(Http_SEC_Errors.HANDLER_ERROR.getMessage()+": "+ex.getMessage());
        	response = new Response();
        	response.setStatus(Http_SEC_Errors.HANDLER_ERROR.getId());
        	response.setData(exceptionError.getErrorObject());
            Logger.getLogger(HandlerSocketListenerThread.class.getName()).log(Level.WARNING, null, ex);

        } finally {
        	try {
				new DataOutputStream(socket.getOutputStream()).write(response.getObjectResponse().toString().getBytes("UTF-8"));//returns the json reply and concatenates the status 
				socket.close();
			} catch (Exception ex) {
				SECErrorManager exceptionError = new SECErrorManager();
	        	exceptionError.setMessage(Http_SEC_Errors.HANDLER_ERROR.getMessage()+": "+ex.getMessage());
	        	response = new Response();
	        	response.setStatus(Http_SEC_Errors.HANDLER_ERROR.getId());
	        	response.setData(exceptionError.getErrorObject());
	            Logger.getLogger(HandlerSocketListenerThread.class.getName()).log(Level.SEVERE, null, ex);

			}
        	
        }

    }
}










