private package deviceUtils.deviceInnerUtils.pmCommands;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.selenium.api.IMobileDevice;

/**
 * @author shirk
 *
 */
public final class Application extends Command{
	
	private String appName;
	private String appIdentifier;
	private String repositoryKey;
	private boolean instrumentApp;
	private Certificate certificate;
	
	public Application(RemoteWebDriver driver) {
		super(driver);
		this.appName=null;
	}
	public Application(RemoteWebDriver driver, String appName) {
		super(driver);
		this.appName = appName;
		this.instrumentApp = false;
		this.certificate = null;
	}
		
	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public void setCertificate(String certificateKey, String certificationUser,
				String certificationPassword, String certificationParams){
		
		this.certificate = new Certificate(certificateKey, certificationUser, certificationPassword, certificationParams);
		
	}

	/*****************************************************************************
	 * Applications Commands
	 ****************************************************************************/
	/**********************************************************
	 * closes an application with the "appName" name.
	 * returns true/false Upon success
	 ***********************************************************/
	 public boolean close() {
		Map<String, Object> params = new HashMap<>();
		if (appName==null)
		{
			System.out.println("no application is set. use setApp command first");
			return false;
		}
		
		params.put("name", this.appName);
		try {
			driver.executeScript("mobile:application:close", params);
			return true;
		} catch (Exception e) {
			System.out.println("failed to close application " + appName);
			return false;
		}
	
	 }
	 
	 /**********************************************************
	 * Launches an application with the "appIdentifier" name.
	 * returns true/false Upon success
	 **********************************************************/
	 public boolean start() {
		
		 Map<String, Object> params = new HashMap<>();
			if (appName==null)
			{
				System.out.println("no application is set. use setApp command first");
				return false;
			}
			
			params.put("name", this.appName);
			try {
				driver.executeScript("mobile:application:open", params);
				return true;
			} catch (Exception e) {
				System.out.println("failed to open application " + appName);
				return false;
			}
			    
	}
	 
	 public boolean install(String repositoryKey, boolean instrumentation, Certificate certificate ){
		Map<String, Object> params = new HashMap<>();
		
		try {
			params.put("instrument", "noinstrument");
			params.put("file", repositoryKey);
			if (instrumentation)
				params.put("instrument", "instrument");
			
			if (certificate!=null)
			{
				params.put("certificate.file",  certificate.certificateKey);
				params.put("certificate.user", certificate.certificationUser);
				params.put("certificate.password", certificate.certificationPassword);
				params.put("certificate.params", certificate.certificationParams);
			}
			driver.executeScript("mobile:application:install", params);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	 }
	 public boolean install(String repositoryKey){
		 
		 return install(repositoryKey,false,null);
	 }
	 public boolean install(String repositoryKey, boolean instrumentation){
		 return install(repositoryKey,instrumentation,null);
		
		
	 }
	 public boolean uninstall() {
		 Map<String, Object> params = new HashMap<>();
			if (appName==null)
			{
				System.out.println("no application is set. use setApp command first");
				return false;
			}
			
			params.put("name", this.appName);
			try {
				driver.executeScript("mobile:application:uninstall", params);
				return true;
			} catch (Exception e) {
				System.out.println("failed to uninstall application " + appName);
				return false;
			}


		
		 
	 }
	 
	/* public boolean getDeviceApplication(){
		  try {
			  Map<String, String> params = new HashMap<>();
			  //params.put("command", "application find ");
			  params.put("handsetId", device.getDeviceId());
			  String listOfApps = (String) device.executeGenericCommand("application", "find", params);
			  return true;
		} catch (Exception e) {
			return false;
		}
	 }*/
	 
	 private class Certificate{
		 
		 public String certificateKey;
		 public String certificationUser;
		 public String certificationPassword;
		 public String certificationParams;
		
		 public Certificate(String certificateKey, String certificationUser,
				String certificationPassword, String certificationParams) {
			
			this.certificateKey = certificateKey;
			this.certificationUser = certificationUser;
			this.certificationPassword = certificationPassword;
			this.certificationParams = certificationParams;
		}
		 
		 
		 
		 
	 }
}
