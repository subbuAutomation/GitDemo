package common;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.junit.ClassRule;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.asserts.SoftAssert;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
//import com.cucumber.listener.Reporter;
import com.google.common.io.Files;

//import org.testng.asserts.SoftAssert;
//vish commented following 3 lines
//import cucumber.api.Scenario;
//import cucumber.api.java.After;
//import cucumber.api.java.Before;
import common.CommonUtil;
import common.TFSUtil;
import common.WebBrowser;
import io.cucumber.java.*;
import ScreenRecorder.ScreenRecorderUtil;
import org.monte.screenrecorder.ScreenRecorder;

public class Hooks {
	public WebDriver driver;
	 public static String userName;
	public static String password;
	private static List<Map<String, Object>> tstSteps = new ArrayList<>();
final static Logger log = Logger.getLogger(Hooks.class);
	public static boolean closeBrowser=true;
	public static SoftAssert softAssertions=new SoftAssert();
	public static List<String> tagsExecuted=new ArrayList<String>();
	public static int passCount=0;
    public static int failCount=0;
    public static int skipCount=0;
	public static String JiraIntegration;
	private static String path = System.getProperty("user.dir");
	
	String EnableVideoCaptureForSuccess = CommonUtil.GetXMLData(
			Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
			"EnableVideoCaptureForSuccess");
    String EnableVideoCaptureForFailure = CommonUtil.GetXMLData(
			Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
			"EnableVideoCaptureForFailure");

	@Before
	
	public void init(Scenario scenario) {
		log.info("*********************************Execution started*******************************");
		String node=System.getProperty("Node");
		if(node == null || node.isEmpty())
		{
			node="Node1";
		}
		String url=System.getProperty("Url");
		//System.out.println("Url-----------"+url);
		if(url != null && !url.isEmpty())
		{
		CommonUtil.appUrl=url;
		}
		String apiurl=System.getProperty("apiUrl");
		//System.out.println("apiurl-----------"+apiurl);
		if(apiurl != null && !apiurl.isEmpty())
		{
		RestAssuredUtil.apiCmdUrl=apiurl;
		}
		String browserName=System.getProperty("browserName");
		//System.out.println("browserName-----------"+browserName);
		if(browserName != null && !browserName.isEmpty())
		{
		CommonUtil.browserName=browserName;
		}
		YMLUtil.loadYML("src/test/java/", node);
		YMLUtil.loadObjectRepoYML("src/test/java/ObjectRepository.yml");
		YMLUtil.readObjectRepoYML("src/test/java/ObjectRepository.yml");
		YMLUtil.PayloadYML("src/test/java/Payload.yml", node);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	//	 Reporter.addScenarioLog("Start Time : "+timestamp);
	    ExtentCucumberAdapter.addTestStepLog(""+scenario.getSourceTagNames());
		ExtentCucumberAdapter.addTestStepLog("Start Time : "+timestamp);
	if(EnableVideoCaptureForSuccess.toUpperCase().equals("TRUE")||EnableVideoCaptureForFailure.toUpperCase().equals("TRUE"))
		{
			try {
				ScreenRecorderUtil.startRecord("TestCase");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}	

		JiraIntegration = CommonUtil.GetXMLData(
				Paths.get(path.toString(), "src", "test", "java", "ApplicationSettings.xml").toString(),
				"JiraIntegration");
	     
		//extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/ExtentScreenshot.html", true);
	}
	
	//Method to wait for 2 minutes before executing the test case
	//This method is used to handle LRS execution so that execution will happen without locking the user to Login 
	@Before("@waitinminutes")
	public void beforeScenario()throws Throwable {
		log.info("*********************************Scenario started*******************************");
		
		Thread.sleep(120000);
		CommonUtil.setCopiedCount(0);
	}

	@BeforeStep
	public void storeScenario(Scenario scenario) 
		{
		WebBrowserUtil.sce=scenario;
		}
	
	@AfterStep
	public void addScreenshot(Scenario scenario) 
		{
		WebBrowserUtil.takeEachStepScrenshot(scenario);
		}
	@After(order = 1)
	public void afterScenario(Scenario scenario) {
		
		if(EnableVideoCaptureForSuccess.toUpperCase().equals("TRUE")||EnableVideoCaptureForFailure.toUpperCase().equals("TRUE"))
		{
		 try {
				ScreenRecorderUtil.stopRecord();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(EnableVideoCaptureForSuccess.toUpperCase().equals("TRUE")&&EnableVideoCaptureForFailure.toUpperCase().equals("FALSE"))
			    { if(scenario.isFailed()) 			
		    	     ScreenRecorderUtil.deleteRecordedFile();
			    }
		    else if(EnableVideoCaptureForSuccess.toUpperCase().equals("FALSE")&&EnableVideoCaptureForFailure.toUpperCase().equals("TRUE"))
		         {  if(!scenario.isFailed()) 			
		        	  ScreenRecorderUtil.deleteRecordedFile();
				 }
				
		}	
		
		if(JiraIntegration.toUpperCase().equals("TRUE"))
			if (scenario.isFailed())
				{     
				//scenario.getName()=>name of the failing scenario; scenario.getLine=>the line(s) in the feature file of the Scenario; scenario.getStatus()=>scenario Status 
				JiraUtil.cmdArray[8]=CommonUtil.error;  //Summary
				JiraUtil.cmdArray[9]="Scenario " + scenario.getName() + " is failed at line(s) " + scenario.getLine() + " with status " + scenario.getStatus();
				JiraUtil.JiraIntegration();
				}
		
		log.info("*********************************Scenario ended*******************************");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ExtentCucumberAdapter.addTestStepLog("End Time: "+timestamp);
		CommonUtil.setCopiedCount(0);
		CommonUtil.setCopiedCountTextNull();
		softAssertions=new SoftAssert();
		boolean semiAuto=false;
		String scenarioStatusLowercase = new String();
		scenarioStatusLowercase = scenario.getStatus().toString().toLowerCase();
		
		if(scenarioStatusLowercase.equals("skipped")) {
			skipCount++;
		}
		
		if (scenario.isFailed()||(scenarioStatusLowercase.equals("passed"))) {
			
			if(scenarioStatusLowercase.equals("passed")) {
				passCount++;
			} else if(scenarioStatusLowercase.equals("failed")) {
				failCount++;
				AutoHealUtil.SaveConfigDeatils();
			}
						
			String screenshotName = "Image_"+new Date().getTime();
			boolean flag=true;
			for(String tag : scenario.getSourceTagNames())
			{
				tagsExecuted.add(tag);
				if(tag.toLowerCase().contains("api"))
				{
					flag=false;
					break;
				}
			}
			if(flag)
			{
				driver = WebBrowser.getBrowser(); 
				WebBrowserUtil.takeScrenshot(scenario);

			}
					}
		for(String tag : scenario.getSourceTagNames())
		{
			closeBrowser=true;
			if (tag.contains("usesamesession"))
			{
				closeBrowser=false;
				break;
			}
		}
		String testcaseId="";
		for(String tag : scenario.getSourceTagNames())
		{
			if (tag.contains("set2") || tag.contains("semiauto") || tag.contains("set3") || tag.contains("set21") || tag.contains("set22") || tag.contains("set23"))
            {
				if(closeBrowser)
				{
				WebBrowser.closeBrowserInstance();
				}
            }
            if (tag.contains("semiauto"))
            {
                semiAuto = true;
            }
            if (scenario.isFailed() && semiAuto)
            {
                semiAuto = false;
                throw new CustomException("Semi Auto test cases may fail due to OTP / Captcha.");
            }
            if(tag.contains("test"))
			{
				testcaseId=tag;
			}
            
            String SubmitTfsResult = CommonUtil.GetXMLData(Paths.get( System.getProperty("user.dir").toString(), "src", "test", "java", "ApplicationSettings.xml").toString(), "SubmitResultToTFS");
            boolean tfsResult= Boolean.parseBoolean(SubmitTfsResult);
            if(tfsResult) {
        	int testPointId=0, testPlanId=0, testRunId=0,testCaseId=0;
            TFSUtil tfsUtil = new TFSUtil();
            testPointId = tfsUtil.getTestPointId(testCaseId)[0];
            testPlanId= tfsUtil.getTestPointId(testCaseId)[1];
            testRunId = tfsUtil.getTestRunId(testCaseId, testPointId, testPlanId);
            System.out.println(tstSteps);
            tfsUtil.updateResultsToTFS(testCaseId,testPointId, testRunId, tstSteps, userName, password);
            tstSteps.clear();
            
		}
		
		}	
		softAssertions=new SoftAssert();
	}

	@After(order = 0)
	public void closeBrowser() {
		log.info("*********************************Execution ended*******************************");
		System.out.println("------------------------------");
		System.out.println(" Status - ");
		System.out.println("------------------------------");
		if (WebBrowser.isBrowserOpened() && closeBrowser)
        {           
            WebBrowser.closeBrowserInstance();
        }	
		String abc=System.getProperty("user.dir");
		 File dir = new File(abc+"//output//");
		    File[] files = dir.listFiles();
		    File lastModified = Arrays.stream(files).filter(File::isDirectory).max(Comparator.comparing(File::lastModified)).orElse(null);
		    System.out.println(lastModified);
		   
		      try {
				   
		    	  int totalCount = passCount+failCount+skipCount;
					String json = "{\"TotalTest\":"+String.valueOf(totalCount)+","+"\"passed\":"+passCount+","+"\"failed\":"+failCount+","+"\"skipped\":"+skipCount+"}";			    	
					String path= lastModified+"//Execution_status.json";
					System.out.println("PATH :"+path);
					BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
					writer.write(json);
					writer.close();		    	  
		    	  FileWriter myWriter = new FileWriter(lastModified+"//ExexutedTagDetails.txt");
		    	  Set<String> uniqueTag = new HashSet<String>(tagsExecuted);
				String test="";
				String set="";
				String otherTags="";
				for (String tag : uniqueTag) {
					if(tag.contains("@test"))
					{
						test+= test=="" ? tag: ","+tag;
						
					}
					else if(tag.contains("@set"))
					{
						set+= set=="" ? tag: ","+tag;
						
					}
					else
					{
						otherTags+= otherTags=="" ? tag: ","+tag;
						
					}
				}
				
				myWriter.write("Test Tags:\n");
				myWriter.write(test+"\n\n");
				myWriter.write("Set Tags:\n");
				myWriter.write(set+"\n\n");
				myWriter.write("Other Tags:\n");
				myWriter.write(otherTags+"\n");
				 myWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
	}
	
}
