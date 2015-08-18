private package deviceUtils.deviceInnerUtils.pmCommands;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.selenium.api.IMobileDevice;

public abstract class Command {
	
	protected static RemoteWebDriver driver;
	protected static String os;
	
	public Command(RemoteWebDriver driver, String os) {
		this.driver = driver;
		this.os = os;
	}
	
	protected boolean isAndroid(){
		if (this.os.equals("Android"))
			return true;
		
		return false;
	}

}
