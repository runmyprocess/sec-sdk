package org.runmyprocess.sec;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
public class HandlerListenerThread extends Thread {

    private InputHandlerInterface listener;
    private int port;
    private String protocolClass;

    /**
     * Sets the listener's parameters
     * @param listener
     * @param port
     * @param protocolClass
     */
    public HandlerListenerThread(InputHandlerInterface listener, int port, String protocolClass) {
        this.listener = listener;
        this.port = port;
        this.protocolClass = protocolClass;
    }

    /**
     * Starts listening to the configured socket
     */
    public void run() {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException ex) {
            System.err.println("Could not listen on port: " + this.port + ":" + ex.getMessage());
            Logger.getLogger(HandlerListenerThread.class.getName()).log(Level.WARNING, null, ex);
            System.exit(-1);
        }
        try {
            while (listening)
                new HandlerSocketListenerThread(serverSocket.accept(), this.protocolClass, this.listener).start();

            serverSocket.close();
        } catch (IOException ex) {
    		Logger.getLogger(HandlerListenerThread.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.getMessage());
        }
    }
}