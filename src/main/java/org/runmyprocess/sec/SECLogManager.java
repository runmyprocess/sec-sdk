package org.runmyprocess.sec;


import org.runmyprocess.json.JSONObject;

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
public class SECLogManager {

	private String message;
	private static Logger logger = Logger.getLogger(SECLogManager.class.getName());

    public SECLogManager(){

    }
    public SECLogManager(String className){
        logger =   Logger.getLogger(className);
    }
	/**
	 * Creates a response object for the error message
	 * @param message the message to send back
	 * @param id the error code
	 * @param level the severity level
	 * @return
	 */
	public JSONObject generateReponseObject(String message, int id, Level level) {
		logger.log(level, message) ;
		this.message = message;
		Response resp = new Response();
		resp.setStatus(id);
		resp.setData(this.getErrorObject());
		return resp.getData();
	}
	/**
	 * Sets the message and logs the error
	 * @param message
	 * @param level
	 */
	public void logError(String message, Level level){
		logger.log(level, message) ;
		this.message = message;
	}
    /**
     * Sets the message and logs the error
     * @param message
     * @param level
     */
    public void log(String message, Level level){
        logger.log(level, message) ;
        this.message = message;
    }

    public void log(String message, Exception e, Level level){
        logger.log(level,message, e) ;
        this.message = message;
    }
	/**
	 * generate and return the error object
	 * @return
	 */
	public JSONObject getErrorObject() {
		JSONObject error = new JSONObject();
		error.put("message", this.message);
		return error;
	}
	/**
	 * Sets the message of the error manager
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}


}
