package deviceUtils;


public interface DeviceUtilsInterface {
	
	//boolean launchSMSApp();
	//void sendSMSFullNavigation(String message, String destinationPhoneNumber);
	
	String getDeviceProperty(String Property);
	void sendSMS(String message, String destinationPhoneNumber);
	boolean placeCall(String destinationPhoneNumber);
	boolean endCall();
	void clearSafariCache();
	void toggleWiFi(boolean state);
	//void toggleData(boolean state);
	
	
}
