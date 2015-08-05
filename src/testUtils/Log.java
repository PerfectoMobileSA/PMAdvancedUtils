package testUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.SimpleLayout;

public class Log extends Logger{
	 private static Logger specificLog;
	 private static Logger executionLog;
	 
	 private boolean rollingLog;
	
	 public Log(){
		 super("stam"); 
		 
	 }
	 public void setTestLog(String testLogName) {
			 
		 BasicConfigurator.configure();
		 executionLog = LogManager.getRootLogger();
		 System.out.println(executionLog.getName());
		 executionLog.removeAllAppenders();
		    FileAppender fileAppender = new FileAppender();
		    fileAppender.setFile("././test-output/logs/execution.log");
		    
		    PatternLayout pattern = new PatternLayout();
		    pattern.setConversionPattern("%d{dd-MM-yy HH:mm} %-5p [%c{1}] %m %n");
		    fileAppender.setLayout(pattern);
		    
		    
		    executionLog.addAppender(fileAppender);
		    fileAppender.activateOptions();
		 
		 
		 
		 
			 //BasicConfigurator.configure();
			 
			 specificLog = LogManager.getLogger(testLogName);
			// executionLog = LogManager.getLogger("executionlog");
			 
		     
		    //create a FileAppender object and 
		    //associate the file name to which you want the logs
		    //to be directed to.
		   
		    fileAppender = new FileAppender();
		    fileAppender.setFile(getLogFileName(testLogName));
		    fileAppender.setAppend(false);
		    pattern = new PatternLayout();
		    pattern.setConversionPattern("%d{dd-MM-yy HH:mm} %-5p [%c{1}] %m %n");
		    fileAppender.setLayout(pattern);
		   
		
		    //Add the appender to our Logger Object. 
		    //from now onwards all the logs will be directed
		    //to file mentioned by FileAppender
		    specificLog.addAppender(fileAppender);
		    fileAppender.activateOptions();
		    
		   
		}
	 
		
	
	 // Need to create these methods, so that they can be called  
	public void info(String message) {
		specificLog.info(message);
		//executionLog.info(message);
		
	 }
	public void warn(String message) {
		specificLog.warn(message);
		//executionLog.warn(message);
		
	    }
	public void error(String message) {
		specificLog.error(message);
		//executionLog.error(message);
		
	    }
	public void fatal(String message) {
		specificLog.fatal(message);
		//executionLog.fatal(message);
		
	    }
	public void debug(String message) {
		specificLog.debug(message);
		//executionLog.debug(message);
		
	    }
	
	 private static String getLogFileName(String testLog){
		  String logFolder = getCallingClass();
		  Calendar c = Calendar.getInstance();
		  c.setTime(new Date());
		  c.add(Calendar.DATE, 0);
		  DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy_HH.mm.ss");
		  
		  logFolder = "././test-output/logs/"+logFolder+"/"+testLog+"_"+dateFormat.format(c.getTime())+".log";  
		  
		  
		  return logFolder;
	  }
	 
	 public static String getCallingClass() {
	        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
	        StackTraceElement element = stackTrace[4];
	        return element.getMethodName();
	        /*System.out.println("I was called by a method named: " + element.getMethodName());
	        System.out.println("That method is in class: " + element.getClassName());
	         element = stackTrace[2];
	        System.out.println("I was called by a method named: " + element.getMethodName());
	        System.out.println("That method is in class: " + element.getClassName());*/
	    }

	 


}