package deviceUtils;


public interface DeviceUtilsInterface {
	
	//boolean launchSMSApp();
	//HashMap<String,String> getDeviceProperties();
	String getDeviceProperty(String Property);
	void sendSMS(String message, String destinationPhoneNumber);
	boolean placeCall(String destinationPhoneNumber);
	boolean endCall();
	void clearSafariCache();
	void toggleWiFi(boolean state);
	
	void sendSMSFullNavigation(String message, String destinationPhoneNumber);
}
