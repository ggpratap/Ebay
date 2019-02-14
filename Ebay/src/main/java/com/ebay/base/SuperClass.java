package com.ebay.base;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.ebay.reports.ExtentManager;
import com.ebay.reports.ExtentTestManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class SuperClass extends TestListenerAdapter
{
	@SuppressWarnings("rawtypes")
	String deviceName;
	protected AppiumDriver driver;
	protected String osType;
	public Process process;	
	public ExtentReports extent;
	ExtentTest parent;
	ExtentTest child;
	private Map<Long, ExtentTest> parentContext = new HashMap<Long, ExtentTest>();
	File app;
	
	@Parameters({ "deviceName_","udid","platformVersion_","platformName", "apppackage" ,"appWaitActivity","URL_", "bundleId"})

	@BeforeTest
	public void beforeClass(ITestContext caller,String deviceName_, String udid, String platformVersion_, String platformName, String appPackage, String appWaitActivity, String URL_,String bundleId) throws MalformedURLException, InterruptedException
	{
		deviceName=deviceName_;
		File classRootPath = new File(System.getProperty("user.dir"));
		File appDir = new File(classRootPath, "/Test_App");

		switch(platformName)
		{
		case "android" : app = new File(appDir, "eBay.apk");
		osType="android";
		break;

		case "iOS" : app = new File(appDir, "eBay.ipa");
		osType="iOS";

		break;
		}


		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("noReset", true);
		capabilities.setCapability("fullReset", false);


		capabilities.setCapability("deviceName", deviceName_);
		capabilities.setCapability("udid", udid);
		capabilities.setCapability("platformVersion", platformVersion_);
		capabilities.setCapability("platformName", platformName);
		capabilities.setCapability("app", app.getAbsolutePath());

		switch(platformName)
		{

		case "android" : 
			capabilities.setCapability("apppackage", appPackage);
			capabilities.setCapability("automationName", "uiautomator2");

			capabilities.setCapability("appWaitActivity", appWaitActivity);


			driver = new AndroidDriver(new URL("http://"+URL_), capabilities);

			break;

		case "iOS" :  
			capabilities.setCapability("bundleId", bundleId);
			capabilities.setCapability("automationName", "XCUITest");
			capabilities.setCapability("startIWDP", true);

			driver = new IOSDriver(new URL("http://"+URL_), capabilities);
			break;
		}
		parent = ExtentTestManager
				.startTest(deviceName_+"_"+platformVersion_, "")
				.assignCategory(Thread.currentThread().getName());
		parentContext.put(Thread.currentThread().getId(), parent);

	}
	

		@BeforeMethod
	public void beforeMethod(Method method)
	{
		child = ExtentTestManager.startTest(method.getName()).assignCategory(Thread.currentThread().getName());
	}



	@AfterMethod
	public void afterMethod(ITestResult result)
	{
		if(result.getStatus()==ITestResult.FAILURE)
		{
			ExtentTestManager.getTest().log(LogStatus.FAIL,result.getThrowable());
		}
		else if (result.getStatus() == ITestResult.SKIP) {
			ExtentTestManager.getTest().log(LogStatus.SKIP, "Test skipped " + result.getThrowable());
		} else {
			ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed");
		}

		parentContext.get(Thread.currentThread().getId()).appendChild(child);
		ExtentManager.getReporter().flush();
	}
	

	@AfterTest
	public void tearDown() throws InterruptedException 
	{
		driver.closeApp();
	}

	



	
}
