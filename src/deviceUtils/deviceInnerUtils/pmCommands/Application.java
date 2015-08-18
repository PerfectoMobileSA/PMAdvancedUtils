private package deviceUtils.deviceInnerUtils.pmCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.selenium.api.IMobileDevice;

/**
 * @author shirk
 *
 */
public class Application extends Command{
	
	//private String appName;
	//private String appIdentifier;
	private String repositoryKey;
	private boolean instrumentApp;
	private Certificate certificate;
	
	public Application(RemoteWebDriver driver, String os) {
		super(driver, os);
		//this.appName=null;
		//this.appIdentifier = null;
		this.instrumentApp = false;
		this.certificate = null;
		
	}
	/*
	public Application(RemoteWebDriver driver, String os, String appName) {
		super(driver, os);
		this.appName = appName;
		this.instrumentApp = false;
		this.certificate = null;
		this.appIdentifier = null;
	}
	*/	
	/*public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	public String getAppIdentifier() {
		return this.appIdentifier;
	}

	public void setAppIdentifier(String appIdentifier) {
		this.appIdentifier = appIdentifier;
	}
	
	public void setApp(String appName, String appIdentifier) {
		this.appName = appName;
		this.appIdentifier = appIdentifier;
	}
	
	public void setCertificate(String certificateKey, String certificationUser,
				String certificationPassword, String certificationParams){
		
		this.certificate = new Certificate(certificateKey, certificationUser, certificationPassword, certificationParams);
		
	}*/

	/*****************************************************************************
	 * Applications Commands
	 ****************************************************************************/
	
	/**********************************************************
	 * closes an application with the "appName" name or appIdentifier set before.
	 * returns true/false Upon success
	 ***********************************************************/
	/* public boolean close() {
		boolean result=false;		
		Map<String, Object> params = new HashMap<>();
		if (this.appName==null && this.appIdentifier == null)
		{
			System.out.println("no applicationName or applicationIdentifier is set. use setApp command first");
			return false;
		}
		if (this.appName!=null)
			result=closeAppByName(this.appName);
		
		if (result)
			return true;
		
		return closeAppByIdentifier(this.appIdentifier);
			
	 }*/
	 
	 public boolean closeAppByName(String appName){
		 Map<String, Object> params = new HashMap<>();
		 params.put("name", appName);
		
		try {
			driver.executeScript("mobile:application:close", params);
			return true;
		} catch (Exception e) {
			System.out.println("failed to close application " + appName);
			return false;
		}
	 }
	 
	 public boolean closeAppByIdentifier(String appIdentifier){
		 Map<String, Object> params = new HashMap<>();
		 params.put("identifier", appIdentifier);
		
		try {
			driver.executeScript("mobile:application:close", params);
			return true;
		} catch (Exception e) {
			System.out.println("failed to close application " + appIdentifier);
			return false;
		}
	 }
	 
	 
	 /**********************************************************
	 * Launches an application with the "appIdentifier" name.
	 * returns true/false Upon success. timeout=seconds
	 **********************************************************/
	 public boolean startAppByName(String appName, int timeout) {
		
		 Map<String, Object> params = new HashMap<>();
			
		params.put("name", appName);
		try {
			driver.executeScript("mobile:application:open", params);
			return true;
		} catch (Exception e) {
			System.out.println("failed to open application " + appName);
			return false;
		}
			    
	}
	 
	 public boolean startAppByIdentifier(String appIdentifier, int timeout) {
			
		 Map<String, Object> params = new HashMap<>();
			
		params.put("identifier", appIdentifier);
		try {
			driver.executeScript("mobile:application:open", params);
			return true;
		} catch (Exception e) {
			System.out.println("failed to open application " + appIdentifier);
			return false;
		}
			    
	}
	 
	 public boolean installApp(String repositoryKey, boolean instrumentation, Certificate certificate ){
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
	 
	 public boolean installApp(String repositoryKey, boolean instrumentation){
		 return installApp(repositoryKey,instrumentation,null);
		
		
	 }
	 public boolean uninstallAppByName(String appName) {
		Map<String, Object> params = new HashMap<>();
		params.put("name", appName);
		try {
			driver.executeScript("mobile:application:uninstall", params);
			return true;
		} catch (Exception e) {
			System.out.println("failed to uninstall application " + appName);
			return false;
		}
	 
	 }
	 public boolean uninstallAppByIdentifier(String appIdentifier) {
			Map<String, Object> params = new HashMap<>();
			params.put("identifier", appIdentifier);
			try {
				driver.executeScript("mobile:application:uninstall", params);
				return true;
			} catch (Exception e) {
				System.out.println("failed to uninstall application " + appIdentifier);
				return false;
			}
		 
		 }
	 
	 public String getApplicationsNames(){
		  try {
			  if (!isAndroid()){
					 System.out.println("This method is unsupported on this os version");
					 return null;
				 }
			  Map<String, Object> params = new HashMap<>();
			  params.put("format", "name");
			  Object res = driver.executeScript("mobile:application:find", params);
			 return res.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	 }
	 
	 public String getApplicationsIdentifiers(){
		  try {
			 if (!isAndroid()){
				 System.out.println("This method is unsupported on this os version");
				 return null;
			 }
				 
			 Map<String, Object> params = new HashMap<>();
			 params.put("format", "identifier");
			 Object result = driver.executeScript("mobile:application:find", params);
			 return result.toString();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	 }
	
	 public boolean cleanAppByName(String appName){
		 try {
			if (!isAndroid()){
				 System.out.println("This method is unsupported on this os version");
				 return false;
			 }
			Map<String, Object> params = new HashMap<>();
			params.put("name", appName);
			
			Object result = driver.executeScript("mobile:application:clean", params);
			if (result.toString().equals("OK"))
				return true;
			
			return false;
		} catch (Exception e) {
			return false;
		} 
	 }
	 public boolean cleanAppByIdentifier(String appIdentifier){
		 try {
			if (!isAndroid()){
				 System.out.println("This method is unsupported on this os version");
				 return false;
			 }
			Map<String, Object> params = new HashMap<>();
			params.put("identifier", appIdentifier);
			
			Object result = driver.executeScript("mobile:application:clean", params);
			if (result.toString().equals("OK"))
				return true;
			
			return false;
		 } catch (Exception e) {
			return false;
		} 
	 }
	 
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
