package testUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;



public final class FatFingerTest extends TestUtils {
	
	private Random rand;
	private Random randHelper;
	private Point startCoord;
	private Point endCoord;
	
	int minValue;
	int sizeOfRange;
	
	long seed;
	boolean isSeedRandom;
	
	
	Point centerOfElement;
	
	Dimension elementDimension;
	
	int minX, minY,maxX,maxY;
	
	Point newPoint;
	int pixelsToAdd;
	int actualPixelsToAdd;
	
	
	
	public FatFingerTest(RemoteWebDriver driver) {
		super(driver);
		
		
		randHelper = new Random();
		setRandomSeed(seed);
		
		Log= new Log();
		

		HashMap<String, Object> params = new HashMap<String,Object>();
	   
	   //get all Properties:
	   params.put("property", "resolutionWidth");
	   resolutionWidth = Integer.parseInt((String) driver.executeScript("mobile:handset:info", params));
	   params.put("property", "resolutionHeight");
	   resolutionHeight = Integer.parseInt((String) driver.executeScript("mobile:handset:info", params));
	   
	   //sets limits of the screen
	   //minValue = this.resolutionHeight
	   //sizeOfRange = this.resolutionHeight - minValue + 1;
		
		
	
	}
	
	public void click(WebElement element, int pixels){
		Map<String, Object> params = new HashMap<>();	
		try {
			
			this.pixelsToAdd = pixels;
			this.newPoint = null;
			centerOfElement = element.getLocation();
			elementDimension = element.getSize();
			minX = centerOfElement.x - elementDimension.width/2;
			maxX = minX + elementDimension.width;
			minY = centerOfElement.y - elementDimension.height/2;
			maxY = minY + elementDimension.height;
			
			setNewPoint();
			
			
			System.out.println("center=" + centerOfElement.toString()
					+ " min=[" + minX + "," + minY + "], max=[" + maxX + ","
					+ maxY + "]");
			
			
			setNewPoint();
			System.out.println("newPoint=" + newPoint.toString());
			//set rand:
			rand = new Random();
			if (isSeedRandom)
				this.seed = randHelper.nextLong();
			
			rand.setSeed(this.seed);
			
			
			
			
			params.put("location", this.newPoint.x + "," + this.newPoint.y);
			driver.executeScript("mobile:touch:tap", params);


		} catch (Exception e) {
			System.out.println("failed to click new point");
		}  
		
		
		  
		   
		   
		
		
	}
	
	public long getRandomSeed(){
		//Log.debug("Get value of Seed is: "+ this.seed);
		return this.seed;
	}
	
	
	public void setRandomSeed(long seed){
		
		if (seed == -1){
			this.isSeedRandom = true;
			this.seed = randHelper.nextLong();
		} else {
			this.isSeedRandom = false;
			this.seed = seed;
		}
		
	}
	

		private void setNewPoint(){
			Constants.directions side = Constants.directions.getRandom();
			int newX=0;
			int newY=0;
			
			switch (side){
				
				case UP:
					newX = rand.nextInt(this.maxX - this.minX + 1) + this.minX;
					newY = Math.max(minY - pixelsToAdd , 1);
				break;
				case DOWN:
					newX = rand.nextInt(this.maxX - this.minX + 1) + this.minX;
					newY = Math.min(maxY + pixelsToAdd , this.resolutionHeight-1);
				break;
				case LEFT:
					newY = rand.nextInt(this.maxY - this.minY + 1) + this.minY;
					newX = Math.max(minX - pixelsToAdd , 1);
				break;
				case RIGHT:
					newY = rand.nextInt(this.maxY - this.minY + 1) + this.minY;
					newX = Math.min(maxX + pixelsToAdd , this.resolutionWidth-1);
				break;
				
			}
			
			newPoint = new Point(newX,newY);
		}

		
		void swipe(Constants.directions side){
			
			switch (side) {
			
			case UP:
			
			break;
			
			}
		}
	
}
