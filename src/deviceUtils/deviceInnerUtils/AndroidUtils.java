private package deviceUtils.deviceInnerUtils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.httpclient.execution.DeviceProperty;


import testUtils.PerfectoUtils;

 public class AndroidUtils{

	 	public static RemoteWebDriver driver;
		private static Map<String, String> deviceProperties;
		private String os;
		
	
	public AndroidUtils(RemoteWebDriver driver) {
		this.driver = driver;
		PerfectoUtils.getDeviceProperties(driver);
		os = PerfectoUtils.getDeviceProperty("os");
		
	}
	
	
	/**
	 * SMS UTILS
	 * 
	 */
	private static boolean launchSMSApp() {
		Map<String, Object> params = new HashMap<>();
		String model = PerfectoUtils.getDeviceProperty("model");
		String appName = "Messages";
		if (model.contains("SM-G900A")) {
			appName = "Messaging";
		}
		 //First lets validate the launch of the Messaging application
        try {
        	params.put("name", appName);
        	try {
        		driver.executeScript("mobile:application:close", params);
			} catch (Exception e) {}
        	
        	driver.executeScript("mobile:application:open", params);
        	
        } catch (Exception e) {
            System.out.println("Failed to launch Mesaages Application");
            return false;
        }
        PerfectoUtils.switchToContext(driver, "NATIVE");
				
		 try {
			
			 for (int i = 0; i < 2; i++) {
				 
				WebElement element = driver.findElementByXPath("(//group[1]/text[1])[1]");
				String title = element.getText().toLowerCase();
							 
				if (title.contains(appName.toLowerCase()))
					return true;
				else {
					element.click();
					continue;
				}
				
			}
			 return false;
			

		} catch (Exception e) {
			return false;
		}	
	}
	/******************************
	 * Send SMS
	 * @throws Exception 
	 *******************************/
	private void sendSMSFullNavigation(String message, String destinationPhoneNumber){
		
		if (!launchSMSApp())
			return;
		
		PerfectoUtils.switchToContext(driver, "NATIVE");
		//compose new message
		try {
			WebElement element = driver.findElementByXPath("//*[@contentDesc='Compose Button']");
			element.click();
			
			//enter phone number
			element = driver.findElementByXPath("//*[contains(text(),'Enter recipients')]");
			element.clear();
			element.sendKeys(destinationPhoneNumber);
						
			//enter message:
			//theres a bug in andriod that edit set the first time gives just the focus.
			//so this is a workaround to first click then edit set.
			
			element = driver.findElementByXPath("//*[contains(text(),'Enter message')]");
			element.click();
			element.clear();
			element.sendKeys(message);
			
			//send message:
			driver.findElementByXPath("//*[@contentDesc='Send']").click();
			
			

			//closeApplication("Messages");			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		
	

	}
	public void sendSMS(String message, String destinationPhoneNumber) {
		
		try {
			String cmd = "am start -a android.intent.action.SENDTO -d sms:"+destinationPhoneNumber+" --es sms_body "+message+" --ez exit_on_sent true";
			shellComand(cmd);
			//send message:
			PerfectoUtils.switchToContext(driver, "NATIVE");
			driver.findElement(By.xpath("//*[@contentDesc='Send']")).click();
			
			
			
		} catch (Exception e) {	//retry mechanism using full navigation
			sendSMSFullNavigation(message,destinationPhoneNumber);
		}
	}
	
	
	
	
	/**
	 *  PHONE UTILS
	 */
	/**********************************************************************
	 *         This method places a call on iOS7/iOS8 device.
	 *                  
	 *         @param device    the device to place call from
	 *        @param destinationPhoneNumber    the phone number to place call to
	 *         @return    true/false upon success or failure
	 **********************************************************************/
	public boolean placeCall(String destinationPhoneNumber){
		
		try {
			//String cmd = "am start tel:" + destinationPhoneNumber;
			String cmd = "am start -a android.intent.action.CALL -d tel:"+destinationPhoneNumber;
			shellComand(cmd);
					
			return true;
			
		} catch (Exception e) {
			return placeCallFullNavigation(destinationPhoneNumber);
		}
		
		
	}
	/******************************
	 * Place Call
	 * @throws Exception 
	 *******************************/
	private boolean placeCallFullNavigation(String destinationPhoneNumber){
		
		try {
			if (!launchPhoneApp())
				return false;
			
			PerfectoUtils.switchToContext(driver, "NATIVE");
	        driver.findElement(By.xpath("//*[text()='Keypad']")).click();
	        WebElement element = driver.findElement(By.xpath("//*[contains(text(),'Editing.')]"));
	        element.clear();element.sendKeys(destinationPhoneNumber);
	        
	        driver.findElementByXPath("//*[@contentDesc='Call']").click();
						
			
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	private boolean launchPhoneApp(){
		
		Map<String, Object> params = new HashMap<>();
		//First lets validate the launch of the phone application
        try {
        	params.put("name", "Phone");
        	try {
        		driver.executeScript("mobile:application:close", params);
			} catch (Exception e) {}
        	try {
        		driver.executeScript("mobile:application:open", params);
			} catch (Exception e) {
				//Phone app is not recognized then manually open it
				try {
					params.clear();
					driver.executeScript("mobile:handset:ready", params);
					params.clear();
					params.put("label", "Phone");
					driver.executeScript("mobile:button-text:click", params);


				} catch (Exception e2) {
					 System.out.println("Failed to launch Phone Application");
					return false;
				}
			}
        	
        	return true;
        	
        } catch (Exception e) {
            System.out.println("Failed to launch Phone Application");
            return false;
        }
				
	}
	
	/******************************
	 * End Call
	 *******************************/
	public boolean endCall(){
		Map<String, Object> params = new HashMap<>();
		
		//check if there's a call at all
		try{
			driver.executeScript("mobile:handset:ready", params);
			params.put("start", "50%,1%");
			params.put("end", "50%,70%");
			params.put("duration", "1");
			driver.executeScript("mobile:touch:swipe", params);
			
			PerfectoUtils.switchToContext(driver, "NATIVE");
			driver.findElementByXPath("//*[text()='End']").click();
						
			return true;
		}catch (Exception e){
			System.out.println("There has no call on it");
			return false;
		}
			
	}
	
	//Clear safari cache
	public void clearSafariCache(){
        System.out.println("No Safari is supported on Android devices");
    }
	
	
	/**********************************************************************
	 *         This method Toggles  Wi-Fi On and Off.
	 *                  
	 *         @param device    the device to toggle Wi-Fi on
	 *         @param state    ON/OFF
	 **********************************************************************/
	public void toggleWiFi(boolean state) {
	    
		Map<String, Object> params = new HashMap<>();
	   
	    try {
	    	if (state)
	    		params.put("wifi", "enabled");
	    	else
	    		params.put("wifi", "disabled");
	    	
	    	driver.executeScript("mobile:network.settings:set", params);


		} catch (Exception e) {
			System.out.println("failed to toggle wifi");
		}
	    
	}
	
public void toggleData(boolean state) {
	    
		Map<String, Object> params = new HashMap<>();
	   
	    try {
	    	if (state)
	    		params.put("wifi", "enabled");
	    	else
	    		params.put("wifi", "disabled");
	    	
	    	driver.executeScript("mobile:network.settings:set", params);


		} catch (Exception e) {
			System.out.println("failed to toggle wifi");
		}
	    
	}
	private boolean shellComand(String cmdShell){
		  try {
			  Map<String, String> params = new HashMap<>();
			  params.put("command", cmdShell);
			  params.put("handsetId", PerfectoUtils.getDeviceProperty("deviceId"));
			  driver.executeScript("mobile:handset:shell", params);
			 
			  return true;
		} catch (Exception e) {
			return false;
		}
		 
		  		  
	  }

	
}






