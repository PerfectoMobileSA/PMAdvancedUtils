package testUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;

import ch.qos.logback.core.FileAppender;

import com.google.common.base.Function;

public class PerfectoUtils {
	private static Map<String, String> deviceProperties = new HashMap<String, String>();
	
	public static void downloadReport(RemoteWebDriver driver, String type, String fileName) throws IOException {
		try { 
			String command = "mobile:report:download"; 
			Map<String, Object> params = new HashMap<>(); 
			params.put("type", type); 
			String report = (String)driver.executeScript(command, params); 
			File reportFile = new File(fileName + "." + type); 
			BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(reportFile)); 
			byte[] reportBytes = OutputType.BYTES.convertFromBase64Png(report); 
			output.write(reportBytes); output.close(); 
		} catch (Exception ex) { 
			System.out.println("Got exception " + ex); }
	}
	
	public static void downloadAttachment(RemoteWebDriver driver, String type, String fileName, String suffix) throws IOException {
		try {
			String command = "mobile:report:attachment";
			boolean done = false;
			int index = 0;

			while (!done) {
				Map<String, Object> params = new HashMap<>();	

				params.put("type", type);
				params.put("index", Integer.toString(index));

				String attachment = (String)driver.executeScript(command, params);
				
				if (attachment == null) { 
					done = true; 
				}
				else { 
					File file = new File(fileName + index + "." + suffix); 
					BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file)); 
					byte[] bytes = OutputType.BYTES.convertFromBase64Png(attachment);	
					output.write(bytes); 
					output.close(); 
					index++; }
			}
		} catch (Exception ex) { 
			System.out.println("Got exception " + ex); 
		}
	}


	public static void switchToContext(RemoteWebDriver driver, String context) {
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		Map<String,String> params = new HashMap<String,String>();
		params.put("name", context);
		executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
	}

	public static String getCurrentContextHandle(RemoteWebDriver driver) {		  
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		String context =  (String) executeMethod.execute(DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null);
		return context;
	}

	public static List<String> getContextHandles(RemoteWebDriver driver) {		  
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
		List<String> contexts =  (List<String>) executeMethod.execute(DriverCommand.GET_CONTEXT_HANDLES, null);
		return contexts;
	}
	public static boolean checkVisual(RemoteWebDriver driver, String needle){
		String previousContext = getCurrentContextHandle(driver);
		// Switch to visual driver, to perform text checkpoint
		switchToContext(driver, "VISUAL");
		
		// Perform the checkpoint
		try{
			driver.findElement(By.linkText(needle));
		}
		catch(Exception e){
			switchToContext(driver, previousContext);
			return false;
		}
		
		// Switch back to webview context
		switchToContext(driver, previousContext);
		return true;
		
	}
	/* Wait until the objects loads until the timeout */
	  public static WebElement fluentWait(final By locator, RemoteWebDriver driver, long timeout) {
		 
		try {
			 FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				        .withTimeout(timeout, TimeUnit.SECONDS)
				        .pollingEvery(250, TimeUnit.MILLISECONDS)
				        .ignoring(Exception.class);
				        //.ignoring(NoSuchElementException.class);
				       
					  WebElement webelement = wait.until(new Function<WebDriver, WebElement>() {
						  public WebElement apply(WebDriver driver) {
				            return driver.findElement(locator);
						  }
					  });
					    return  webelement;
		} catch (Exception e) {
			return null;
		}
		 
		
	  };
	  public static String getDateAndTime(int offset){
		  Calendar c = Calendar.getInstance();
		  c.setTime(new Date());
		  c.add(Calendar.DATE, offset);
		  DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH-mm-ss z");
		  return dateFormat.format(c.getTime());
	  }
	  public static String takeScreenshot(RemoteWebDriver driver) throws IOException{
		  String filePath = new File("").getAbsolutePath();
		  filePath += "\\test-output\\screenshots";
		  File theDir = new File(filePath);

		  // if the directory does not exist, create it
		  if (!theDir.exists()) {
			  //System.out.println("creating directory: " + directoryName);

			  try{
				  theDir.mkdir();
			  } 
			  catch(SecurityException se){
				  return null;
			  }        
		  }
		  filePath+= "//";
		  File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		  String filename = filePath + getDateAndTime(0) + ".png";
		  FileUtils.copyFile(scrFile, new File(filename));
		  return filename;
	  }
	  public static RemoteWebDriver getPerfectoDriver(String user, String password, String host,
			  DesiredCapabilities capabilities, int retries, int retryIntervalSeconds) throws MalformedURLException, UnsupportedEncodingException, InterruptedException{
		  
		  user = URLEncoder.encode(user, "UTF-8");
		  password = URLEncoder.encode(password, "UTF-8");
		  host = URLEncoder.encode(host, "UTF-8");
		  RemoteWebDriver driver = null;
		  int i = 0;
			while (i < retries){
				try{
					driver = new RemoteWebDriver(new URL("https://" + user + ':' + password + '@' + host + "/nexperience/wd/hub"), capabilities);
					return driver;
				}
				catch(Exception e){
					if(i == retries - 1){
						/*
						Reporter.log("Couldn't find a device /n" + e);
						this.driver = null;
						Assert.fail("Driver not found");
						*/
						return null;
					}
					Thread.sleep(retryIntervalSeconds*1000);
					i++;
				}
			}
			return null;
	  }
	  
	  public static boolean isDevice(RemoteWebDriver driver){
		//first check if driver is a mobile device:
		  Capabilities capabilities = driver.getCapabilities();
		  String type = (String) capabilities.getCapability("browserName");
		  if (type.toLowerCase().equals("chrome") || type.toLowerCase().equals("firefox")){
			  return false;
		  }
		  return true;
	  }
	  
	  /*****
		 * PRIAVTE METHOD SECTION
		 */
		
		/********************************************************************************
		 * This method builds the dictionary of all device properties in a key=value
		 * format.
		 * for example:
		 * model=iPhone6
		 * deviceId=ABCD89765
		 *******************************************************************************/
		public static HashMap<String,String> getDeviceProperties(RemoteWebDriver driver) {
		    //hashmap to contain device properties
		    deviceProperties = new HashMap<String, String>();
	        
	        Map<String, Object> params = new HashMap<>();
	        params.put("property", "ALL");
	        String properties = (String) driver.executeScript("mobile:handset:info", params);

	        List<String> items = Arrays.asList(properties.split(","));
	        String key,value;
	       //build hashmap for all device properties:
	        for (int i = 0; i < items.size(); i=i+2) {
	    	    key=items.get(i);
	    	    if (key.startsWith("[")||key.startsWith(" ")){
	    		    key=key.substring(1);
	    	    }
		        value=items.get(i+1);
		        if(value.startsWith(" ")){
		    	    value=value.substring(1);
		        }
		        if (value.startsWith("[")){
		    	    for (int j = i+2; j < items.size(); j++) {
		    		    value=value+","+items.get(j);
		                if (value.endsWith("]")){
		            	    value=value.substring(1,value.length()-1);
		                    i=j-1;
		                    break;
		                }
		            }
		        }
		        if (value.endsWith("]")){
		        	  value=value.substring(0,value.length()-1);
		        }
		        deviceProperties.put(key, value);
	        }
			return (HashMap<String, String>) deviceProperties;
	       
		 }
		
	    /*****************************************************************************
	    * gets a specific device property out of the Dictionary.
	    * returns the value of the property
	    * for example:
	    * getDeviceProperty("Model") will return the model of the device(ie iPhone-6)
	    ******************************************************************************/
	    public static String getDeviceProperty(String Property){
	    	return (deviceProperties.get(Property));
	    }

	  
	 
	 
	  
}
