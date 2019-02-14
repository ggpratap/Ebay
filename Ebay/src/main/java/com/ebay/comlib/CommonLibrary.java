package com.ebay.comlib;

import java.io.File;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class CommonLibrary 
{

	
	@SuppressWarnings("rawtypes")
	public void swipe_ins(AppiumDriver driver,String direction ) throws InterruptedException

	{
		Thread.sleep(1000);
		TouchAction action = new TouchAction(driver);


		Dimension dimension = driver.manage().window().getSize();

		int width = dimension.getWidth()/2;

		int height = dimension.getHeight();
		
		
		System.out.println(width);
		System.out.println(height);

		switch(direction)

		{

		case "up":


			action.press(PointOption.point(width,  height-60)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(width, 122)).release().perform();

			break;


		case "down":


			action.press(PointOption.point(width, (int) (height*0.6))).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(width, height-50)).release().perform();

			break;


		case "left":

			action.press(PointOption.point((int) (width*(0.9)), (int) (height*(0.5)))).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point((int) (width*(0.1))-(int) (width*(0.9)), (int) (height*(0.5))-(int) (height*(0.5)))).release().perform();


			break;


		case "right":

			action.press(PointOption.point((int) (width*(0.1)), (int) (height*(0.6)))).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point((int) (width*(0.9))-(int) (width*(0.1)), (int) (height*(0.2))-(int) (height*(0.2)))).release().perform();

			break;

		default : System.out.println("Select either right/left/up/down");

		break;
		}
	}
	public String screenshotTest(WebDriver driver, String ImageName) throws Exception{

		File classRootappdir = new File(System.getProperty("user.dir"));
		File appdir = new File(classRootappdir, ConfigurationLibrary.screenCapturePath);
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String dest = appdir+"/"+ImageName+".png";
		File destFile = new File(dest);
		FileUtils.copyFile(scrFile, destFile);
		return dest;
	}

	public void pass(String stepDesc, String inputValue, String expectedValue, ExtentTest test) throws Exception
	{
		test.log(LogStatus.PASS, stepDesc);
		System.out.println(stepDesc+" Pass");
	}

	public void fail(WebDriver driver,String stepDesc, String inputValue, String actValue, ExtentTest test) throws Exception
	{
		test.log(LogStatus.FAIL, stepDesc, actValue+test.addScreenCapture(screenshotTest(driver, actValue)));
		System.out.println(actValue+" Fail");
	}

	@SuppressWarnings("rawtypes")
	public boolean verifyForCondition(AppiumDriver driver,ExtentTest ext,Boolean status,String stepDesc, String expectedValue, String actualValue) throws Exception
	{

		try
		{
			Assert.assertTrue(status);   
			pass(stepDesc, "", expectedValue, ext);
			return true;
		}
		catch(Throwable e)
		{
			fail(driver, stepDesc, "", actualValue, ext);
			System.out.println(stepDesc+" fail" + e.getMessage());
			return false;


		}
	}

	public boolean validateForElementDisplayed(WebElement element,WebDriver driver,ExtentTest test, String stepDesc, String inputValue, String expectedValue, String actValue) throws Exception
	{

		boolean check=false;
		Thread.sleep(1000);
		try{

			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.visibilityOf(element));
			pass(stepDesc, inputValue, expectedValue, test);
			check=true;


		}
		catch (Exception e) {

			check= false;
			System.out.println(stepDesc+" fail");
			fail(driver, stepDesc, inputValue, actValue, test);
		}
		return check;
	}



}
