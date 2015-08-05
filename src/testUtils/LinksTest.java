package testUtils;


import java.io.IOException;
import java.util.ArrayList;

import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;


public final class LinksTest extends TestUtils{

	private String url;
	private static String pageErrorsXpath;
	private long pageLoadTimeout;
	private ArrayList<ArrayList<String>> pageLinks;
	
	
	public LinksTest(RemoteWebDriver driver) {
		this(driver,"");
		
	}
	
	public LinksTest(RemoteWebDriver driver, String url) {
		super(driver);
		
		pageLinks = new ArrayList<ArrayList<String>>();
		//set timeouts:
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		setUrl(url);
		setPageLoadTimeout(Constants.pageLoadTimeout);
				
		//initialize page errors
		buildPageErrorXpath();
		Log= new Log();
		//initialize logs
		
				
	}
	
	

	public void setPageLoadTimeout(long pageLoadTimeout) {
		this.pageLoadTimeout = pageLoadTimeout;
		this.driver.manage().timeouts().pageLoadTimeout(this.pageLoadTimeout, TimeUnit.SECONDS);
	}
	
	public void setUrl(String url) {
		
		this.pageLinks.clear();
		this.url = url;
		
	}

	
	public ArrayList<ArrayList<String>> getListOfPageLinks(){
		return pageLinks;
	}
	
	private ArrayList<ArrayList<String>> buildPageLinks(){
		
		
		//load url:
		try {
			
			this.driver.get(this.url);
			
		} catch (Exception e) {
			Log.fatal("Failed to open URL: " + this.url);
			Log.fatal("Exception: " + e.getMessage());
			return null;
		}
		//parse page and get all links:
		try {
			//get all links on the page:
			 Document doc = Jsoup.parse(this.driver.getPageSource());
			 Elements links = doc.select("a[href]");
			 String href,text;
			 
			 for (Element link : links) {
				 href = link.attr("abs:href");
				 if (href.isEmpty())
					 href = this.url+"/"+link.attr("href");
				 text = link.text();
				
				 ArrayList<String> singlePageLink = new ArrayList<String>();
				 singlePageLink.add(href);
				 singlePageLink.add(text);
											 
	        	if (text.isEmpty()){
	        		Element img = link.select("img[src]").first();
	        		if(img!=null){
	        			singlePageLink.set(1,img.attr("src"));
	        		}
	        		
	        	}
	        	
	        	pageLinks.add(singlePageLink);
	        	
			 }
					
					
		} catch (Exception e) {
			Log.fatal("Failed to get links from Page: " + this.url);
			Log.fatal("Exception: " + e.getMessage());
			return null;
			
		}
		
		return pageLinks;
	}
	
	public ArrayList<ArrayList<String>> linksTest(String url) throws IOException{
		return linksTest(url, this.pageLoadTimeout);
	}
	
	public ArrayList<ArrayList<String>> linksTest() throws IOException{
		return linksTest(this.url, this.pageLoadTimeout);
	}
	
	
	public ArrayList<ArrayList<String>> linksTest(String url, long pageLoadTimeout) throws IOException{
		//initialize logs
		Log.setTestLog("testLinks");
		
		Log.debug("*********************************************************************************************");
		setUrl(url);
		setPageLoadTimeout(pageLoadTimeout);
		
		if (this.url.isEmpty()){
			Log.error("URL is empty - Cannot run testLinks ");
			Log.error("******End test due to an error");
			Log.debug("*********************************************************************************************");
			
			return null;
		}
		
		
		if (buildPageLinks() == null){
			Log.error("******End test due to an error");
			Log.debug("*********************************************************************************************");
			return null;
		}
		
		//check for broken links:
		Log.debug("******Begin test on "+ this.url);
		for (ArrayList<String> singlePageLink: pageLinks){
			try {
				this.driver.get(singlePageLink.get(0));
			} catch (Exception e) {
				singlePageLink.add(Constants.LinkLoadErrorMsg);
				Log.error(singlePageLink.get(1) + " : " +singlePageLink.get(0) + " ==> " +Constants.LinkLoadErrorMsg + " ==> Link to screenshot: "+PerfectoUtils.takeScreenshot(this.driver));
				continue;
			}
			
			try {
				this.driver.findElement(By.xpath(pageErrorsXpath));
				singlePageLink.add(Constants.LinkBrokenMsg);
				Log.error(singlePageLink.get(1) + " : " +singlePageLink.get(0) + " ==> " +Constants.LinkBrokenMsg + " ==> Link to screenshot: "+PerfectoUtils.takeScreenshot(this.driver));
				
				
			} catch (Exception e) {
				if (this.driver.getTitle().isEmpty()){
					singlePageLink.add(Constants.LinkBrokenMsg);
					Log.error(singlePageLink.get(1) + " : " +singlePageLink.get(0) + " ==> " +Constants.LinkBrokenMsg+" ==> Link to screenshot: "+PerfectoUtils.takeScreenshot(this.driver));
				}
				else {
					singlePageLink.add(Constants.LinkSuccessMsg);
					Log.debug(singlePageLink.get(1) + " : " +singlePageLink.get(0) + " ==> " +Constants.LinkSuccessMsg);
				}
			}
			
		}
		
		Log.debug("*************End Of TestLinks on: " + this.url+ "***************");
		Log.debug("*********************************************************************************************");
		return this.pageLinks;
	}
		
	public ArrayList<ArrayList<String>> getBrokenLinks(){
		
		if (pageLinks.isEmpty() || pageLinks.size()==2){
			return null;
		} 
		
		ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>();
		
		for (ArrayList<String> singleLink : pageLinks){
			if (singleLink.get(2).equals(Constants.LinkBrokenMsg)){
				newList.add(singleLink);
			}
				
		}
		
		return newList;
	}
	
	public ArrayList<ArrayList<String>> getLoadFailedLinks(){
		
		if (pageLinks.isEmpty() || pageLinks.size()==2){
			return null;
		} 
		
		ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>();
		
		for (ArrayList<String> singleLink : pageLinks){
			if (singleLink.get(2).equals(Constants.LinkLoadErrorMsg)){
				newList.add(singleLink);
			}
				
		}
		
		return newList;
	}
	
	public ArrayList<ArrayList<String>> getSucceededLinks(){
		
		if (pageLinks.isEmpty() || pageLinks.size()==2){
			return null;
		} 
		
		ArrayList<ArrayList<String>> newList = new ArrayList<ArrayList<String>>();
		
		for (ArrayList<String> singleLink : pageLinks){
			if (singleLink.get(2).equals(Constants.LinkSuccessMsg)){
				newList.add(singleLink);
			}
				
		}
		
		return newList;
	}
	
	/**
	 * Print pageLinks and their status
	 */
	public void printPageLinksStatus(){
		if (pageLinks.isEmpty() || pageLinks.size()==2){
			return;
		} 
		
	
		for (ArrayList<String> singleAddress: this.pageLinks){
			if (singleAddress.size()>2) {
				System.out.println("***** " + singleAddress.get(1) + " ***** " + singleAddress.get(0)
					+ " ; " + singleAddress.get(2));
				
			}else{
				System.out.println("***** " + singleAddress.get(1) + " ***** " + singleAddress.get(0));
				
			}
			
		}
	}
	
	/*
	 * Build the xpath for all relevant broken links errors.
	 * to add an error, add it to Constant file
	 */
	private void buildPageErrorXpath(){
		String xpath = "//title[";
		int counter = 0;
		
		for (String msg: Constants.loadPageErrors){
			++counter;
			if (counter!=1){
				xpath = xpath+" or ";
			}
			xpath = xpath+ "contains(text(),'" + msg + "')";
		}
		xpath = xpath + "]";
		pageErrorsXpath = xpath;	
	}
	
	
}
