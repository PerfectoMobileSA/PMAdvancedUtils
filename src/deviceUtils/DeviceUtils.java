package deviceUtils;

import java.util.Map;

import org.openqa.selenium.remote.RemoteWebDriver;

import testUtils.PerfectoUtils;
import deviceUtils.deviceInnerUtils.AndroidUtils;
import deviceUtils.deviceInnerUtils.IOSUtils;
import deviceUtils.deviceInnerUtils.pmCommands.Application;


public class DeviceUtils {
	public RemoteWebDriver driver;
	private static Map<String, String> deviceProperties;
	private String os;
	private AndroidUtils androidUtils;
	private IOSUtils iosUtils;
	private Application application;
	
	public DeviceUtils(RemoteWebDriver driver) {
		this.driver = driver;
		this.androidUtils = null;
		this.iosUtils = null;
		this.application= new Application(driver,os);
		
		deviceProperties = PerfectoUtils.getDeviceProperties(driver);
		os = deviceProperties.get("os");
		
		if (os.equals("Android"))
			this.androidUtils = new AndroidUtils(driver);
		else
			this.iosUtils = new IOSUtils(driver);
		
		
	}
	//boolean launchSMSApp();
	//void sendSMSFullNavigation(String message, String destinationPhoneNumber);
	
	
	public String getDeviceProperty(String Property) {
		return PerfectoUtils.getDeviceProperty(Property);
	}

	public void sendSMS(String message, String destinationPhoneNumber){
		
		switch (this.os){
			case "Android":
				androidUtils.sendSMS(message, destinationPhoneNumber);
			break;
			case "iOS":
				iosUtils.sendSMS(message, destinationPhoneNumber);
			break;
			default:
				System.out.println("unsupported utils for this os system");
			break;
		}
	}
	public boolean placeCall(String destinationPhoneNumber){
		switch (this.os){
			case "Android":
				return androidUtils.placeCall(destinationPhoneNumber);
			
			case "iOS":
				return iosUtils.placeCall(destinationPhoneNumber);
			
			default:
				System.out.println("unsupported utils for this os system");
				return false;
		
		}
	}
	public boolean endCall(){
		switch (this.os){
			case "Android":
				return androidUtils.endCall();
			
			case "iOS":
				return iosUtils.endCall();
			
			default:
				System.out.println("unsupported utils for this os system");
				return false;
		}
	}
	public void clearSafariCache(){
		switch (this.os){
			case "Android":
				System.out.println("unsupported utils for this os system");
			break;
			case "iOS":
				iosUtils.clearSafariCache();
			break;
			default:
				System.out.println("unsupported utils for this os system");
			break;
		}
	}
	public void toggleWiFi(boolean state){
		switch (this.os){
			case "Android":
				androidUtils.toggleData(state);
			break;
			case "iOS":
				iosUtils.toggleWiFi(state);
			break;
			default:
				System.out.println("unsupported utils for this os system");
			break;
		}
	}
	
	
 
	/*@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
	  @ BEGIN: Application Commands Section	
	  @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@*/
	public Application setApplication(String appName){
		try {
			application = new Application(driver,appName);
			return application;
		} catch (Exception e) {
			return null;
		}
		
	}
	public Application getApplication(String appName){
		return application;
	}
	/**********************************************************
	 * closes an application with the "appIdentifier" name.
	 * returns true/false Upon success
	 ***********************************************************//*
	 public boolean closeApplication(String appName) {
		 application = new Application(driver,appName);
		 
		 if (application.close()){
			 return true;
		 }
		 return false;
	}
	 public boolean closeApplication() {
		 return closeApplication(this.application.getAppName());
	 }
	  
	 *//**********************************************************
	 * Launches an application with the "appIdentifier" name.
	 * returns true/false Upon success
	 **********************************************************//*
	 public boolean startApplication(String appName) {
		 application = new Application(driver,appName);
		 
			switch (appName.toLowerCase()) {
	        case "messages":
	            if (!launchSMSApp())
	            	return false;
	        break;
	        case "settings":
	        	if (!launchSettingsApp())
	        		return false;
	        break;
	        default:
	        	if (!application.start())
	        		return false;
	        	break;
	    	}
					
			
			return true;
	    
	 }
	 public boolean startApplication(){
		 return startApplication(application.getAppName());
	 }
	 public boolean installApp(String repositoryKey){
		 return (new Application(device).install(repositoryKey));
	}
	 public boolean installApp(String repositoryKey, boolean instrumentApp){
		 return (new Application(device).install(repositoryKey,instrumentApp));
	}
	 public boolean uninstallApp(String appIdentifier) {
		 return (new Application(device,appIdentifier).uninstall());
	 }
	*/
	
}
