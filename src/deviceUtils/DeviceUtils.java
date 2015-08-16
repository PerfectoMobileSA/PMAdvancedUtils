package deviceUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.RemoteWebDriver;

import deviceUtils.*;
import deviceUtils.deviceHiddenUtils.*;
public class DeviceUtils implements DeviceUtilsInterface{
	
	public RemoteWebDriver driver;
	private static Map<String, String> deviceProperties;
	private String os;
	private iOSUtils iosDriver;
	
	 
	public DeviceUtils(RemoteWebDriver driver) {
		
		this.driver = driver;
		getDeviceProperties();
		os = getDeviceProperty("os");
		iosDriver = new iOSUtils(driver);
	}
	
	private boolean launchSMSApp(){
		
		switch(os){
			case "Android":
				//FFU
			break;
			case "iOS":
				return iosDriver.launchSMSApp();
			
			default:
				System.out.println("Not supported");
		}
		
		return false;
		
	}
	
	public void sendSMS(String message, String destinationPhoneNumber){
		
		switch(os){
			case "Android":
				//FFU
			break;
			case "iOS":
				iosDriver.sendSMS(message, destinationPhoneNumber);
			
			default:
				System.out.println("Not supported");
		}
		
		
		
	}
public boolean placeCall(String destinationPhoneNumber){
		
		switch(os){
			case "Android":
				//FFU
			break;
			case "iOS":
				return iosDriver.placeCall(destinationPhoneNumber);
			
			default:
				System.out.println("Not supported");
		}
		return false;
		
		
	}

	public boolean endCall(){
		
		switch(os){
			case "Android":
				//FFU
			break;
			case "iOS":
				return iosDriver.endCall();
			
			default:
				System.out.println("Not supported");
		}
		return false;
		
		
	}
	
	public void clearSafariCache() {
		
		switch(os){
			case "Android":
				//FFU
			break;
			case "iOS":
				iosDriver.clearSafariCache(getDeviceProperty("osVersion"));
			
			default:
				System.out.println("Not supported");
		}
		
		
		
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
	public void getDeviceProperties() {
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
       
	 }
	
    /*****************************************************************************
    * gets a specific device property out of the Dictionary.
    * returns the value of the property
    * for example:
    * getDeviceProperty("Model") will return the model of the device(ie iPhone-6)
    ******************************************************************************/
    public String getDeviceProperty(String Property){
    	return (deviceProperties.get(Property));
    }

	
}
