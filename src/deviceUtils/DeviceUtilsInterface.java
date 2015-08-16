package deviceUtils;


public interface DeviceUtilsInterface {
	
	//boolean launchSMSApp();
	void getDeviceProperties();
	String getDeviceProperty(String Property);
	void sendSMS(String message, String destinationPhoneNumber);
	boolean placeCall(String destinationPhoneNumber);
	boolean endCall();
	void clearSafariCache();
}
