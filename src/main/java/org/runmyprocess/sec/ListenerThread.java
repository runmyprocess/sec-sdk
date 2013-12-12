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
public class ListenerThread extends Thread {

    private JSONSocketListener listener;
    private int port;

    /**
     * Constructor that sets the port to listen and the listener
     * @param listener
     * @param port
     */
    public ListenerThread(JSONSocketListener listener, int port) {
        this.listener = listener;
        this.port = port;
    }

    /**
     * Starts listening to socket
     */
    public void run() {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException ex) {
            System.err.println("Could not listen on port: " + this.port + ":" + ex.getMessage());
            Logger.getLogger(ListenerThread.class.getName()).log(Level.WARNING, null, ex);
            System.exit(-1);
        }
        try {
            while (listening)
                new SocketListenerThread(serverSocket.accept(), this.listener).start();

            serverSocket.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            Logger.getLogger(ListenerThread.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}