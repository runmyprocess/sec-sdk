package org.runmyprocess.sec;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
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
public class Config
{
   Properties configFile;
   /**
    * Loads the a config file and adds the lines to a "properties" object.
    * @param protocolConfigFile The path to the file
    * @param relativePath is it a relative path or an absolute path
    * @throws Exception
    */
   public Config(String protocolConfigFile, boolean relativePath) throws Exception
   {
	configFile = new java.util.Properties();
	try {
		if (relativePath){
		    //to load application's properties
		    FileInputStream file;
		    String path = protocolConfigFile;
		    file = new FileInputStream(path);
		    //load all the properties from this file
		    configFile.load(file);
		    //we have loaded the properties, so close the file handle
		    file.close();
		}else{
			//just load the file to a configfile
			File file = new File(protocolConfigFile);
			FileInputStream fis = new FileInputStream(file);
			configFile.load(fis);	
		}

	}catch(Exception e){
		throw e;
	}
   }
/**
 * Returns the configuration property
 * @param key the property required
 * @return
 */
   public String getProperty(String key)
   {
	String value = this.configFile.getProperty(key);
	return value;
   }

}
