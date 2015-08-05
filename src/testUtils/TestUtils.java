package testUtils;

import testUtils.*;
import org.openqa.selenium.remote.RemoteWebDriver;


	public abstract class TestUtils {
		protected RemoteWebDriver driver;
		protected static Log Log;
		protected int resolutionWidth;
		protected int resolutionHeight;
			
		public TestUtils(RemoteWebDriver driver) {
			
			this.driver = driver;
			this.resolutionHeight = 0;
			this.resolutionWidth = 0;
			//Log= new Log(DeviceUtils.class.getName()).getLog();
			//Log.setLevel(Level.TRACE);
			/*
			//initialize Log:
			DOMConfigurator.configure("log4j.xml");
			Log= Logger.getLogger(DeviceUtils.class.getName());
			//BasicConfigurator.configure();
			Log.setLevel(Level.TRACE);*/
	
			//GetDeviceProperties();
			//fatFingers = new FatFingers(this.driver, this);
			
		}
		
		
		
		
}
