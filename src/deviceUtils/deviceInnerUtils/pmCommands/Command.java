private package deviceUtils.deviceInnerUtils.pmCommands;

import org.openqa.selenium.remote.RemoteWebDriver;

import com.perfectomobile.selenium.api.IMobileDevice;

public abstract class Command {
	
	protected static RemoteWebDriver driver;
	
	public Command(RemoteWebDriver driver) {
		this.driver = driver;
	}
	
	//Abstract methods

}
