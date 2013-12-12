RunMyProcess-SEC-SDK
====================

SDK classes for the Secure Enterprise Connector (SEC).
The SEC-SDK contains classes required by the "SEC - Protocol Manager". It also contains the generic Adapter that can be imported while creating an adapter to simplify the coding.

Generating the jar file
-----------------------
To generate the jar file simply download the source code and run :

	mvn clean install

You can also add the project as a maven dependency on your project. To do this, simply add the project mvn-repo branch as a repository on your maven pom.mxl file:

	<repositories>
	        <repository>
	            <id>json-mvn-repo</id>
	            <url>https://raw.github.com/runmyprocess/sec-sdk/mvn-repo/</url>
	            <snapshots>
	                <enabled>true</enabled>
	                <updatePolicy>always</updatePolicy>
	            </snapshots>
	        </repository>
	</repositories>
	
You can then add your project's dependency:

	<dependency>
		<groupId>org.runmyprocess</groupId>
		<artifactId>rmp-sec-sdk</artifactId>
		<version>1.0</version>
	</dependency>

The generic Handler
--------------------
The SDK also contains the generic Handler. This class can be helpful for the development of Java Adapters for the SEC. 



Creating a Custom Adapter for the SEC Using the Generic Handler
----------------------------------------------------------------

We will create a simple SEC Adapter. Our objective is to create a "HelloWorld" adapter that will return a message with a certain structure when posting the resource. 



For this guide the following is assumed :

* The SEC manager is running on the server and a tunnel is open and configured.  
* The server has Java installed.
* The ping port on the manager is 4444 (this can be configured in the adapter and the manager).
* The manager is running on the same server as the adapter (127.0.0.1).

The Adapter will be coded in **JAVA** and we will use the "**Generic Protocol Handler**". This means that the classes we create must include the **SEC-SDK project**, which contains the **GPH classes**. 

##Adapter Classes
We will create 2 classes : the Handler and the Adapter.

###Handler
The Handler will create a new instance of the GPH with the configuration information. For this example, we will create a class called **HelloWorldHandler**.
The code for the Handler looks as following :

	package com.runmyprocess.sec;
	
	import java.io.File;
	import java.util.logging.Level;

	public class HelloWorldHandler {
		public static void main(String [] args)throws java.io.IOException{ 	
			try{
				GenericHandler genericHandler = new GenericHandler();//Creates a new instance of generic handler
				System.out.println("Searching for config file...");
				Config conf = new Config("configFiles"+File.separator+"handler.config",true);//sets the congif info
				System.out.println("Handler config file found for manager ping port "+
									conf.getProperty("managerPort"));
				genericHandler.run(conf);//Runs the handler
			}catch( Exception e ){
					SECErrorManager errorManager = new SECErrorManager();//creates a new instance of the SDK error manager
					errorManager.logError(e.getMessage(), Level.SEVERE);//logs the error
					e.printStackTrace();//prints the error stack trace
				}
			}
	}
	
This is the starting point of our Adapter. We are importing :

* com.runmyprocess.sec - To have access to the SDK classes and the GPH. To simplify things, the Adapter is packaged in the same package as the SDK. 
* java.io.File - To get the OS's file separator so that the adapter can be run on different OSs.
* java.util.logging.Level - To log any error.

The main method is run when starting the adapter. It will first create a new instance of the GPH and then load the config files. For this example we are loading the handlers config file from a folder named "configFiles". The handler config file should look like this :
	
	#Generic Protocol Configuration
	protocol = HelloWorld
	protocolClass = com.runmyprocess.sec.HelloWorld
	handlerHost = 127.0.0.1
	connectionPort = 5832
	managerHost = 127.0.0.1
	managerPort = 4444
	pingFrequency = 300
	
Where :

* **protocol** is the name to identify our Adapter.
* **protocolClass** is the class of the Adapter.
* **handlerHost** is where the Adapter is running.
* **connectionPort** is the port of the adapter where data will be received and returned.
* **managerHost** is where the SEC is running. 
* **managerPort** is the port where the SEC is listening for ping registrations.
* **pingFrequency** is the frequency in which the manager will be pinged (at least three times shorter than what's configured in the manager).


###Adapter
If the handler is configured correctly, it will ping the manager and will be automatically registered with the first ping. 
The handler must implement the **ProtocolInterface**. This interface structures the data transfer.
It contains two methods to be overridden :

* accept - where the data is received and processed.
* getResponse - gets the response object.

The Adapter code should look as following :

	package com.runmyprocess.sec;

	import java.util.logging.Level;
	import org.runmyprocess.json.JSONObject;

	public class HelloWorld implements ProtocolInterface{
	   
		private Response response = new Response();

		public HelloWorld() {
			// TODO Auto-generated constructor stub
		}
		
		private JSONObject HelloWorldError(String error){
			response.setStatus(500);//sets the return status to internal server error
			JSONObject errorObject = new JSONObject();
			errorObject.put("error", error.toString());
			return errorObject;
		}
		
		  @Override
		public void accept(JSONObject jsonObject,String configPath) {
			try {
				String message = jsonObject.getString("message");//Gets the message sent 
				response.setStatus(200);//sets the return status to 200
				JSONObject resp = new JSONObject();
				resp.put("Message", "Hello World! "+message);//sends the info inside an object
				response.setData(resp);
		
			} catch (Exception e) {
				response.setData(this.HelloWorldError(e.getMessage()));
				SECErrorManager errorManager = new SECErrorManager();
				errorManager.logError(e.getMessage(), Level.SEVERE);
				e.printStackTrace();
			}
		}
		
		@Override
		public Response getResponse() {
			return response;
		}	
	}

By looking at the code we can see that in the *accept method*, the received JSON object expects a message parameter which will be parsed as a String and concatenated to our hardcoded "Hello World! " string.
We will then create a JSONObject and add this string as a property and finally run the *setData method* of the response object.
The GPH will take care of calling the *getReponse method* and sending back the information to the manager on the configured port.

It is important to mention that the *accept method* receives another parameter (configPath). This string is the configuration path.

##Testing the Adapter

Testing the adapter is quite simple. Simply run the **HelloWorldHandler** or package everything in a runnable jar file and run it.
When started, the Adapter will look into the "**configFiles**" folder and get the config file for the handler. 
To obtain a JSONObject response with a message property containing "Hello World! we are online!" the body of the POST should look like this :

	{
	"protocol":"HelloWorld",
	"data":{
			"message":"we are online!"
		} 
	}

The response object should look like this :

	{"SECStatus":200,"Message":"Hello World! we are online!"}
