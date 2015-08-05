package testUtils;

import java.util.concurrent.TimeUnit;


public class Constants {
	
	//farFingures:
	public static int defaultFatFingers = 45;
	public static enum directions {
		UP,
		DOWN,
		LEFT,
		RIGHT;
		
		public static directions getRandom() {
	        return values()[(int) (Math.random() * values().length)];
	    }

	}
	
	//broken links Page Variables:
	public static String LinkBrokenMsg = "Broken Link";
	public static String LinkLoadErrorMsg = "Page Load Error";
	public static String LinkSuccessMsg = "Page Load Success";
	
	public static String [] loadPageErrors = {
		"is not available",
		"safari could not open",
		"404",
		"400",
		"401",
		"403",
		"405",
		"406",
		"407",
		"408",
		"409",
		"410",
		"411",
		"412",
		"413",
		"414",
		"415",
		"416",
		"417",
		"418",
		"419",
		"420",
		"421",
		"422",
		"423",
		"424",
		"426",
		"428",
		"429",
		"431",
		"440",
		"444",
		"449",
		"450",
		"451",
		"494",
		"495",
		"496",
		"497",
		"498",
		"499",
		"499",
		"500",
		"501",
		"502",
		"503",
		"504",
		"505",
		"506",
		"507",
		"508",
		"509",
		"510",
		"511",
		"520",
		"598",
		"599"};
	/*public static String [] loadPageErrors = {
		"is not available",
		"safari could not open",
		"404 Not Found",
		"400 Bad Request",
		"401 Unauthorized",
		"403 Forbidden",
		"405 Method Not Allowed",
		"406 Not Acceptable",
		"407 Proxy Authentication Required",
		"408 Request Timeout",
		"409 Conflict",
		"410 Gone",
		"411 Length Required",
		"412 Precondition Failed",
		"413 Request Entity Too Large",
		"414 Request-URI Too Long",
		"415 Unsupported Media Type",
		"416 Requested Range Not Satisfiable",
		"417 Expectation Failed",
		"418 I",
		"419 Authentication Timeout",
		"420 Method Failure",
		"421 Misdirected Request",
		"422 Unprocessable Entity",
		"423 Locked",
		"424 Failed Dependency",
		"426 Upgrade Required",
		"428 Precondition Required",
		"429 Too Many Requests",
		"431 Request Header Fields Too Large",
		"440 Login Timeout",
		"444 No Response",
		"449 Retry With",
		"450 Blocked by Windows Parental Controls",
		"451 Redirect",
		"494 Request Header Too Large",
		"495 Cert Error",
		"496 No Cert",
		"497 HTTP to HTTPS",
		"498 Token expired/invalid",
		"499 Client Closed Request",
		"499 Token required",
		"500 Internal Server ErrorA",
		"501 Not Implemented",
		"502 Bad Gateway",
		"503 Service Unavailable",
		"504 Gateway Timeout",
		"505 HTTP Version Not Supported",
		"506 Variant Also Negotiates",
		"507 Insufficient Storage",
		"508 Loop Detected",
		"509 Bandwidth Limit Exceeded",
		"510 Not Extended",
		"511 Network Authentication Required",
		"520 Unknown Error",
		"598 Network read timeout error",
		"599 Network connect timeout error"};
	*/
	
	public static long pageLoadTimeout = 25;						
	
	
	//Log Constants:
	public enum logFileTypes {
		DummyTest,BrokenLinks,FatFingers;
		
		
	}
	
	//DummyTestConstants
	public static int dummyActionsNumber = 100;
	public static long dummyTimeout = 300;
	public static TimeUnit dummyTimeoutUnit = TimeUnit.SECONDS;
	public static double dummyDeviceBounderies = 0.05;
	public static int dummyMaxSeed = 5000;

	
}
