package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.MalformedURLException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.nio.file.Paths;

public class WebBrowser {
	//public static WebDriver driver;
	private static WebDriver driver;
	private static String path = System.getProperty("user.dir");
	static String parentWindowHandle;
	private static boolean isBrowserOpen = false;
	private static boolean launchNewBrowser = false;
	public static boolean boolHighLightElement = false;
	public static boolean boolEachstepScreenshot = false;
	public static boolean boolEachSoftAssersion = false;
	static List<WebDriver> webdriverList = new ArrayList<WebDriver>();
	public static String browserType = "";
	public static String PageLoadTimeout = "";
	public static String DirectoryPAth = "";
	public static String HighLightElement = "";
	public static String EachstepScreenshot = "";
	public static String softassertion = "";
	public static String profilePath = "";
	public static String lambdaTestBuild = "";
	public static String lambdaTestURL = "";
	public static String NetworkLog = "";
	public static String type = "";
	public static String hubURL = "";

	public static WebDriver getBrowser(boolean launchBrowser) {
		if ((driver == null || launchBrowser) && !isBrowserOpen) {

			if (CommonUtil.browserName != null) {
				browserType = CommonUtil.browserName;
				System.out.println("browserName-----------------" + browserType);
			} else {
				browserType = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"BrowserType");
			}
			PageLoadTimeout = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"MaximumTimeInSecondsToWaitForControl");
			DirectoryPAth = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"DownloadInCurrentDirectory");
			HighLightElement = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"HighLightWebElement");
			EachstepScreenshot = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"EnableEachStepScreenshot");
			softassertion = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"EnableSoftassertion");

			profilePath = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"profilePath");	

			if(HighLightElement.toUpperCase().equals("TRUE")) {
				boolHighLightElement=true;
			}
			if(EachstepScreenshot.toUpperCase().equals("TRUE")) {
				boolEachstepScreenshot=true;
			}
			if(softassertion.toUpperCase().equals("TRUE")) {
				boolEachSoftAssersion=true;
			}
			if (browserType.equals("Firefox")) {
				System.out.print("Launching Firefox");
				//System.setProperty("webdriver.gecko.driver",
				//		Paths.get(path.toString(), "src", "test", "resources", "geckodriver.exe").toString());
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
				driver.manage().window().maximize();
			} else if (browserType.equals("Edge")) {

				System.out.print("Launching Edge");
				//System.setProperty("webdriver.edge.driver",
				//		Paths.get(path.toString(), "src", "test", "resources", "msedgedriver.exe").toString());
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
				driver.manage().window().maximize();
			} else if (browserType.toUpperCase().equals("Lambda".toUpperCase())) {
				System.out.print("Attempting connection to LambdaTest");
				lambdaTestBuild = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"LambdaTestBuild");
				lambdaTestURL = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"LambdaTestURL");
				NetworkLog = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"NetworkLog");

				RemoteWebDriver remotedriver = null;
				DesiredCapabilities caps = new DesiredCapabilities();
				caps.setCapability("build", lambdaTestBuild);

				//String testRailId=CommonUtil.getTestRailID();

				caps.setCapability("name", "");
				caps.setCapability("platform", "Windows 10");
				caps.setCapability("browserName", "Chrome");
				caps.setCapability("version", "97.0");
				caps.setCapability("network",NetworkLog);
				System.out.println("Desired caps made successfully");

				try {

					remotedriver = new RemoteWebDriver(new URL(lambdaTestURL),
							caps);
					SessionId sessionid = remotedriver.getSessionId();
					System.out.print("Driver session id is :"+sessionid.toString());

				} catch (MalformedURLException e) {

					System.out.println("Invalid grid URL");

				} catch (Exception e) {

					System.out.println(e.getMessage());

				}

				driver = remotedriver;
			} else {
				System.out.print("Launching Chrome");
				//				if (isWindows()) {
				//					System.setProperty("webdriver.chrome.driver",
				//							Paths.get(path.toString(), "src", "test", "resources", "chromedriver.exe").toString());
				//				} else {
				//					System.setProperty("webdriver.chrome.driver",
				//						Paths.get(path.toString(), "src", "test", "resources", "chromedriver").toString());
				//				}
				WebDriverManager.chromedriver().setup();
				//Create a map to store  preferences
				Map<String, Object> prefs = new HashMap<String, Object>();

				// add key and value to map as follow to switch off browser notification
				// Pass the argument 1 to allow and 2 to block
				prefs.put("profile.default_content_setting_values.notifications", 2);
				//DesiredCapabilities caps = DesiredCapabilities.chrome();
				DesiredCapabilities caps = new DesiredCapabilities();
				LoggingPreferences logPrefs = new LoggingPreferences();
				logPrefs.enable(LogType.BROWSER, Level.ALL);
				caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
				logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
				caps.setCapability("goog:loggingPrefs", logPrefs);

				// Create an instance of ChromeOptions
				if (DirectoryPAth.toUpperCase().equals("TRUE")) {
					String downloadFilepath = System.getProperty("user.dir");
					prefs.put("download.default_directory", downloadFilepath);
				}
				ChromeOptions options = new ChromeOptions();
				if (browserType.toUpperCase().equals("HEADLESS CHROME")) {
					options.addArguments("--no-sandbox"); // Bypass OS security model
					options.addArguments("--headless"); // headless -> no browser window. needed for jenkins
					options.addArguments("disable-infobars"); // disabling infobars
					options.addArguments("--disable-extensions"); // disabling extensions
					options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
					options.addArguments("window-size=1920,1080");
				}

				if (profilePath != null && !profilePath.isEmpty()) {
					// Here you set the path of the profile ending with User Data not the profile folder
					options.addArguments("user-data-dir="+profilePath);
				}

				options.addArguments("--ignore-ssl-errors=yes");
				options.addArguments("--ignore-certificate-errors");
				// set ExperimentalOption - prefs
				options.setExperimentalOption("prefs", prefs);
				options.addArguments("use-fake-ui-for-media-stream");
				options.addArguments("use-fake-device-for-media-stream");
				options.addArguments("--remote-allow-origins=*");
				if (browserType.equals("Kiosk Chrome")) {
					options.addArguments("--kiosk");
				}

				type = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"Type");

				if (type.toUpperCase().contains("GRID")) {
					hubURL = CommonUtil.GetXMLData(
							Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
							"HubURL");
					try {
						driver = new RemoteWebDriver(new URL(hubURL), options);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					// Now Pass ChromeOptions instance to ChromeDriver Constructor to initialize
					// chrome driver which will switch off this browser notification on the chrome
					// browser
					options.merge(caps);
					driver = new ChromeDriver(options);
					driver.manage().window().maximize();
				}
			}
			webdriverList.add(driver);
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(PageLoadTimeout), TimeUnit.SECONDS);
			//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(PageLoadTimeout), TimeUnit.SECONDS);

			parentWindowHandle = driver.getWindowHandle();
			isBrowserOpen = true;
		} else if (launchBrowser) {
			WebDriver driver2 = multipleBrowserInstance();
			webdriverList.add(driver2);
			driver2.manage().window().maximize();
		}
		return driver;

	}

	public static WebDriver multipleBrowserInstance() {

		if (CommonUtil.browserName != null) {
			browserType = CommonUtil.browserName;
			System.out.println("browserName-----------------" + browserType);
		} else {
			browserType = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"BrowserType");
		}
		PageLoadTimeout = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"MaximumTimeInSecondsToWaitForControl");
		DirectoryPAth = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"DownloadInCurrentDirectory");
		HighLightElement = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"HighLightWebElement");
		EachstepScreenshot = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"EnableEachStepScreenshot");
		softassertion = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"EnableSoftassertion");

		profilePath = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"profilePath");	

		if(HighLightElement.toUpperCase().equals("TRUE")) {
			boolHighLightElement=true;
		}
		if(EachstepScreenshot.toUpperCase().equals("TRUE")) {
			boolEachstepScreenshot=true;
		}
		if(softassertion.toUpperCase().equals("TRUE")) {
			boolEachSoftAssersion=true;
		}
		if (browserType.equals("Firefox")) {
			System.out.print("Launching Firefox");
			//System.setProperty("webdriver.gecko.driver",
			//		Paths.get(path.toString(), "src", "test", "resources", "geckodriver.exe").toString());
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		} else if (browserType.equals("Edge")) {

			System.out.print("Launching Edge");
			//System.setProperty("webdriver.edge.driver",
			//		Paths.get(path.toString(), "src", "test", "resources", "msedgedriver.exe").toString());
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			driver.manage().window().maximize();
		} else if (browserType.toUpperCase().equals("Lambda".toUpperCase())) {
			System.out.print("Attempting connection to LambdaTest");
			lambdaTestBuild = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"LambdaTestBuild");
			lambdaTestURL = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"LambdaTestURL");
			NetworkLog = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"NetworkLog");

			RemoteWebDriver remotedriver = null;
			DesiredCapabilities caps = new DesiredCapabilities();
			caps.setCapability("build", lambdaTestBuild);

			//String testRailId=CommonUtil.getTestRailID();

			caps.setCapability("name", "");
			caps.setCapability("platform", "Windows 10");
			caps.setCapability("browserName", "Chrome");
			caps.setCapability("version", "97.0");
			caps.setCapability("network",NetworkLog);
			System.out.println("Desired caps made successfully");

			try {

				remotedriver = new RemoteWebDriver(new URL(lambdaTestURL),
						caps);
				SessionId sessionid = remotedriver.getSessionId();
				System.out.print("Driver session id is :"+sessionid.toString());

			} catch (MalformedURLException e) {

				System.out.println("Invalid grid URL");

			} catch (Exception e) {

				System.out.println(e.getMessage());

			}

			driver = remotedriver;
		} else {
			System.out.print("Launching Chrome");
			//				if (isWindows()) {
			//				System.setProperty("webdriver.chrome.driver",
			//						Paths.get(path.toString(), "src", "test", "resources", "chromedriver.exe").toString());
			//			} else {
			//				System.setProperty("webdriver.chrome.driver",
			//					Paths.get(path.toString(), "src", "test", "resources", "chromedriver").toString());
			//			}
			WebDriverManager.chromedriver().setup();
			//Create a map to store  preferences
			Map<String, Object> prefs = new HashMap<String, Object>();

			//add key and value to map as follow to switch off browser notification
			//Pass the argument 1 to allow and 2 to block
			prefs.put("profile.default_content_setting_values.notifications", 2);
			//DesiredCapabilities caps = DesiredCapabilities.chrome();
			DesiredCapabilities caps = new DesiredCapabilities();
			LoggingPreferences logPrefs = new LoggingPreferences();
			logPrefs.enable(LogType.BROWSER, Level.ALL);
			caps.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
			logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
			caps.setCapability("goog:loggingPrefs", logPrefs);

			//Create an instance of ChromeOptions
			if (DirectoryPAth.toUpperCase().equals("TRUE")) {
				String downloadFilepath = System.getProperty("user.dir");
				prefs.put("download.default_directory", downloadFilepath);
			}
			ChromeOptions options = new ChromeOptions();
			if (browserType.toUpperCase().equals("HEADLESS CHROME")) {
				options.addArguments("--no-sandbox"); // Bypass OS security model
				options.addArguments("--headless"); // headless -> no browser window. needed for jenkins
				options.addArguments("disable-infobars"); // disabling infobars
				options.addArguments("--disable-extensions"); // disabling extensions
				options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
				options.addArguments("window-size=1920,1080");
			}

			if (profilePath != null && !profilePath.isEmpty()) {
				// Here you set the path of the profile ending with User Data not the profile folder
				options.addArguments("user-data-dir="+profilePath);
			}

			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			//set ExperimentalOption - prefs
			options.setExperimentalOption("prefs", prefs);
			options.addArguments("use-fake-ui-for-media-stream");
			options.addArguments("use-fake-device-for-media-stream");
			options.addArguments("--remote-allow-origins=*");
			if (browserType.equals("Kiosk Chrome")) {
				options.addArguments("--kiosk");
			}

			type = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
					"Type");

			if (type.toUpperCase().contains("GRID")) {
				hubURL = CommonUtil.GetXMLData(
						Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
						"HubURL");
				try {
					driver = new RemoteWebDriver(new URL(hubURL), options);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				// Now Pass ChromeOptions instance to ChromeDriver Constructor to initialize
				// chrome driver which will switch off this browser notification on the chrome
				// browser
				options.merge(caps);
				driver = new ChromeDriver(options);
				driver.manage().window().maximize();
			}
		}
		//webdriverList.add(driver);
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(PageLoadTimeout), TimeUnit.SECONDS);
		//driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(PageLoadTimeout), TimeUnit.SECONDS);

		parentWindowHandle = driver.getWindowHandle();
		isBrowserOpen = true;

		return driver;
	}
	public static WebDriver getBrowser() {
		return getBrowser(launchNewBrowser);
	}

	public void setBrowser(WebDriver webDriver) {

		this.driver = webDriver;
	}

	public static void closetab(int tab) {
		try {
			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(tab));
			driver.close();
		} catch (Exception e) {

		}

	}

	public static void LaunchApplication(boolean openBrowser) {
		String autUrl = "";
		if (CommonUtil.appUrl != null) {
			autUrl = CommonUtil.appUrl;
			System.out.println("appurl-----------" + autUrl);
		} else {
			autUrl = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(), "URL");
		}
		getBrowser(openBrowser);
		driver.get(autUrl);
	}

	public static void LaunchApplication(boolean openBrowser, String autUrl) {

		getBrowser(openBrowser);
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		for (int i = 0; i < tabs.size(); i++) {
			driver.switchTo().window(tabs.get(i));
		}
		WebDriver newDriver = webdriverList.get(webdriverList.size() - 1);
		newDriver.get(autUrl);
	}

	public static void LaunchNewInstance(boolean openBrowser, String autUrl) {
		getBrowser(openBrowser);
		WebDriver newDriver = webdriverList.get(webdriverList.size() - 1);
		newDriver.get(autUrl);
	}

	public static void LaunchApplication1(String autUrl) {

		driver.navigate().to(autUrl);
	}

	public static void openNewTab(boolean openBrowser, String autUrl) {		
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		for (int i = 0; i < tabs.size(); i++) {
			driver.switchTo().window(tabs.get(i));
		}
		WebDriver newDriver = webdriverList.get(webdriverList.size() - 1);
		newDriver.get(autUrl);
	}

	public static void LaunchAPIApplication() {
		String autUrl = "";
		if (RestAssuredUtil.apiCmdUrl != null) {
			autUrl = RestAssuredUtil.apiCmdUrl;
			System.out.println("api url-----------" + autUrl);
		} else {
			autUrl = CommonUtil.GetXMLData(
					Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(), "APIURL");
		}
		// getBrowser();
		RestAssuredUtil.setAPIURL(autUrl);
		// driver.get(autUrl);

	}

	public static String getParentWindowHandle() {
		return parentWindowHandle;
	}

	public static void setCurrentBrowser(int index) {
		if (webdriverList.size() > index) {
			driver = webdriverList.get(index);
			isBrowserOpen = true;
		}
	}

	public static void closeBrowserInstance() {
		for (int counter = 0; counter < webdriverList.size(); counter++) {
			if (webdriverList.get(counter) != null) {
				webdriverList.get(counter).quit();
			}
		}

		driver = null;
		webdriverList = new ArrayList<WebDriver>();
		isBrowserOpen = false;
	}

	public static boolean isBrowserOpened() {
		return isBrowserOpen;
	}

	public static boolean isWindows() {
		String OS = System.getProperty("os.name").toLowerCase();
		return OS.contains("win");
	}

}
