package com.ebay.comlib;

import java.time.Duration;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


public class CommonReusableMethods {
	CommonLibrary comlib = new CommonLibrary();
	ExtentTest exTest;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void waitForElementToDisappear(AppiumDriver driver,WebElement element) throws InterruptedException      
	{

		boolean status=false;
		while(status==false)
		{
			try{
				Wait  fw= new FluentWait(driver).withTimeout(Duration.ofMillis(5000)).pollingEvery(Duration.ofMillis(2000)).ignoring(Exception.class);
				fw.until(ExpectedConditions.visibilityOf(element));

			}catch(Exception e)
			{
				status=true;

			}
		}
	}

	public boolean swipeToElement(WebElement element, AppiumDriver driver,ExtentTest exTent) throws Exception
	{

		boolean check= true;
		boolean flag= false;
		int count=0;
		TouchAction action= new TouchAction(driver);
		Dimension dimension = driver.manage().window().getSize();

		int width = dimension.getWidth()/2;

		int height = dimension.getHeight();


		while(check)
		{
			try {

				WebDriverWait wait = new WebDriverWait(driver, 3);
				wait.until(ExpectedConditions.visibilityOf(element));


				check=false;
				flag=true;
			}catch (Exception e) {
				if(count<5)
				{
					action.press(PointOption.point(width,  height-55)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(width, (int) (height*0.6))).release().perform();
					count++;
					check=true;
				}else {
					check=false;

				}
			}
		}
		return flag;


	}

}
















