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
public class SocketListenerThread extends Thread {
   
    private Socket socket = null;
    private JSONSocketListener listener;
    
    /**
     *Contructor that create a socket listener thread and sets the socket and the listener 
     * @param socket
     * @param listener
     */
    public SocketListenerThread(Socket socket, JSONSocketListener listener) {
        super("SocketListenerThread");
        this.socket = socket;
        this.listener = listener;
    }
    /**
     * Sends the information to the listener in JSONObject format
     */
    public void run() {
        try {
            JSON result = JSON.parse(new StreamParser(socket.getInputStream(), "UTF-8"));//gets the body and parses it to JSON
            JSONObject jsonRequest = result.toJSONObject(); //Converts JSON to JSONOBJECT
            this.listener.acceptJson(jsonRequest);
            socket.close();
        } catch (IOException ex) {
        	 Logger.getLogger(SocketListenerThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}