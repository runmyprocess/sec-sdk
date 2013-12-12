package org.runmyprocess.sec;
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
public enum Http_SEC_Errors {
	  MANAGER_ERROR(101, "An error in the SEC Manager occured."),
	  HANDLER_ERROR(102, "An error in the SEC Handler occured."),
	  HANDLER_NOTFOUND(103, "The handler was not found."),
	  HANDLER_IDLE(103, "The handler stopped working.");
	  private final int id;
	  private final String message;
	/**
	 * Set the error message and id
	 * @param id
	 * @param message
	 */
	  Http_SEC_Errors(int id, String message) {
	     this.id = id;
	     this.message = message;
	  }
	  /**
	   * returns the error id previously set
	   * @return id
	   */
	  public int getId() { return id; }
	  /**
	   * returns the error message previously set
	   * @return message
	   */
	  public String getMessage() { return message; }
}
