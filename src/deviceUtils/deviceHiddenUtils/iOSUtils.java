private package deviceUtils.deviceHiddenUtils;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.httpclient.execution.DeviceProperty;

import testUtils.PerfectoUtils;

 public class iOSUtils {

	public static RemoteWebDriver driver;
	
	public iOSUtils(RemoteWebDriver driver) {
		this.driver = driver;
	}
	
	/**********************************************************************
	 *         This method launches the SMS application on an iOS7/iOS8 device.
	 *         it validates application is on its home screen and navigates to
	 *         it in case its not.
	 *         
	 *         @param device    the device to launch application SMS application on
	 *         @return    true/false upon success or failure
	 **********************************************************************/
	public static boolean launchSMSApp() {
		Map<String, Object> params = new HashMap<>();
	    
        //launch Messages Application
        try {
        	params.put("name", "Messages");
        	try {
        		driver.executeScript("mobile:application:close", params);
			} catch (Exception e) {}
        	
        	driver.executeScript("mobile:application:open", params);
        	//Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("Failed to launch Messages Application");
            return false;
        }
        //validate we are on the home page of app:
         try {
        	
        	PerfectoUtils.switchToContext(driver, "NATIVE");
        	WebElement element = driver.findElementByXPath("//navigationbar[1]/button[1]");
        	String title = element.getText().toLowerCase();
          	

            if (title.startsWith("edit"))
                return true;
            if (title.startsWith("cancel") || title.contains("back")){
            	driver.findElement(By.xpath("//*[@label='Cancel']")).click();
                return true;
            }
            if (title.startsWith("messages")){
                driver.findElement(By.xpath("//*[starts-with(@label,'Messages')]")).click();
                return true;
            }
            return false;
        } catch (Exception e) {
        	e.printStackTrace();
            System.out.println("Failed to launch Messages Application");
            return false;
        }    
    }
	
	/**********************************************************************
	 *         This method sends an SMS on iOS7/iOS8 device.
	 *                  
	 *         @param device    the device to send SMS from
	 *         @param message    the message to send
	 *         @param destinationPhoneNumber    the phone number to send SMS to
	 *         @return    true/false upon success or failure
	 **********************************************************************/
	public static void sendSMS(String message, String destinationPhoneNumber) {
	        
        if (!launchSMSApp())
            return;
        try {
            driver.findElement(By.xpath("//*[@label='Compose']")).click();
            driver.findElement(By.xpath("//*[@label='To:'][1]")).sendKeys(destinationPhoneNumber);
            try {
                driver.findElement(By.xpath("//*[@label='Recent']")).click();
            } catch (Exception e) {
                //proceed with script
            }
            driver.findElement(By.xpath("//*[@label='Message'][1]")).sendKeys(message);        
            driver.findElement(By.xpath("//*[@label='Send'][1]")).click();
        } catch (Exception e) {
            System.out.println(e.toString());
            
        }
    }
	
	/**********************************************************************
	 *         This method places a call on iOS7/iOS8 device.
	 *                  
	 *         @param device    the device to place call from
	 *        @param destinationPhoneNumber    the phone number to place call to
	 *         @return    true/false upon success or failure
	 **********************************************************************/
	public static boolean placeCall(String destinationPhoneNumber){
	    char digit;    //will be used to get a specific digit from the destination phone number
	    String xpath;    //will be used to create a xpath string
	    WebElement element; //will be used for elements on Page
	    Map<String, Object> params = new HashMap<>();
	    
	    //First lets validate the launch of the phone application
        try {
        	params.put("name", "Phone");
        	try {
        		driver.executeScript("mobile:application:close", params);
			} catch (Exception e) {}
        	
        	driver.executeScript("mobile:application:open", params);
        	
        } catch (Exception e) {
            System.out.println("Failed to launch Phone Application");
            return false;
        }
	    try{
	    	PerfectoUtils.switchToContext(driver, "NATIVE");
	        driver.findElement(By.xpath("//*[text()='Keypad']")).click();
	        //The next loop validates we delete any previous digits in Keypad in case they exist
	        for(int i=0;i<20;i++){
	            try {
	                element = driver.findElement(By.xpath("//*[text()='Delete' and @enabled='true']"));
	                element.click();
	                continue;
	            } catch (Exception e) {
	                //finished deleting all previous digit in case they existed
	                break;
	            }
	        }
	        //Enter destination phone number digit by digit:
	        for (int i = 0; i < destinationPhoneNumber.length(); i++){
	            digit = destinationPhoneNumber.charAt(i);
	            xpath = "//*[@name='" + digit+ "']";
	            //ignore '+' sign in case exists at the beginning of the phone number:
	            if (digit == '+') {
	                continue;
	            }
	            //Enter rest of the digits
	            driver.findElement(By.xpath(xpath)).click();                        
	        }
	        //click call button
	        driver.findElement(By.xpath("//*[@label='Call']")).click();
	        return true;    
	    } catch (Exception e) {
	        System.out.println("failed to place call");
	        return false;
	    }
	}
	
	
	/******************************
	 * End Call
	 *******************************/
	public boolean endCall(){
		Map<String, Object> params = new HashMap<>();
		//home:
		driver.executeScript("mobile:handset:ready", params);
		//open menu
		params.put("location", "50%,1%");
		driver.executeScript("mobile:touch:tap", params);

		//check if there's a call at all
		try{
			
			PerfectoUtils.switchToContext(driver, "NATIVE_APP");
			driver.findElementByXPath("//*[@label='End call']").click();
			System.out.println("Ended Call");
			
		}catch (Exception e){
			System.out.println("There is no call on it");
			//return false;
		}
		
		return true;
	}
	
	//Clear safari cache
	public static void clearSafariCache(String osVer){
        //defining variables
        HashMap<String, Object> params = new HashMap();
        
        PerfectoUtils.switchToContext(driver,"NATIVE_APP");
        //get os version:
        //params.put("property", "osVersion");
        //osVer = (String) driver.executeScript("mobile:handset:info", params);
        //Launch Settings Application on it's main page
        params.clear();
        params.put("name", "Settings");
        try {
            driver.executeScript("mobile:application:close", params);
        } catch (Exception e) {}
        driver.executeScript("mobile:application:open", params);
        
        //Scroll to Top
        params.clear();
        params.put("location", "20%,1%");
        driver.executeScript("mobile:touch:tap", params);
        //click Safari:
        params.clear();
        params.put("content", "Safari");
        params.put("scrolling", "scroll");
        params.put("levels.high", "100");
        params.put("next", "SWIPE=(20%,70%),(20%,30%);WAIT=2000");
        params.put("screen.width", "50%");
        params.put("screen.top", "0%");
        params.put("screen.left", "0%");
        params.put("screen.height", "100%");
        driver.executeScript("mobile:text:select", params);
        //swipe to bottom:
        params.clear();
        params.put("start", "50%,90%");
        params.put("end", "50%,10%");
        for (int i=0;i<3;i++){
            driver.executeScript("mobile:touch:swipe", params);
        }
        //clear Cache
        params.clear();
        params.put("value", "//*[starts-with(text(),'Clear History')]");
        params.put("framework", "perfectoMobile");
        driver.executeScript("mobile:application.element:click", params);
        params.put("value", "//*[(@class='UIAButton' or @class='UIATableCell') and starts-with(@label,'Clear') and @isvisible='true']");
        driver.executeScript("mobile:application.element:click", params);
        //below version 8 need to clear also data:
        if (!osVer.startsWith("8.")){
            params.put("value", "//*[starts-with(text(),'Clear Cookies')]");
            driver.executeScript("mobile:application.element:click", params);
            params.put("value", "//*[(@class='UIAButton' or @class='UIATableCell') and starts-with(@label,'Clear') and @isvisible='true']");
            driver.executeScript("mobile:application.element:click", params);
        }
    }
	
}






