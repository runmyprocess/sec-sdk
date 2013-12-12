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
public class Response {

	private JSONObject data;
	private int status; 
	/**
	 * Gets all the response information in a single JSONObject structure
	 * @return 
	 */
	public JSONObject getObjectResponse() {
		JSONObject reply = new JSONObject();
		reply=data.copy();
		reply.put("SECStatus", this.status);
		return reply;	
	}
	/**
	 * Gets the data from the response
	 * @return
	 */
	public JSONObject getData() {
		return data;
	}
	/**
	 * Sets the data object
	 * @param data
	 */
	public void setData(JSONObject data) {
		this.data = data;
	}
	/**
	 * gets the status of the response (http and custom codes)
	 * @return
	 */
	public int getStatus() {
		return status;
	}
	/**
	 * Sets the status code of the response
	 * @param status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
