package org.runmyprocess.sec;

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
public class Request {
	private String protocol;
	private JSONObject data;
	
	/**
	 * Constructor that creates a empty instance
	 */
	public Request() {		
	}
	/**
	 * Constructor creates an instance and sets the protocol name and the information to send
	 * @param protocol
	 * @param data
	 */
	public Request(String protocol,JSONObject data) {
		this.protocol = protocol;
		this.data = data;
	}
	/**
	 * 	Gets the protocol name
	 * @return protocol
	 */
	public String getProtocol() {
		return protocol;
	}
	/**
	 * Sets the protocol name
	 * @param protocol name of the protocol
	 */
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	/**
	 * 	Gets the data to send to the protocol  
	 * @return data
	 */
	public JSONObject getData() {
		return data;
	}
	/**
	 * Sets the data to send to the protocol
	 * @param data
	 */
	public void setData(JSONObject data) {
		this.data = data;
	}
	
}
