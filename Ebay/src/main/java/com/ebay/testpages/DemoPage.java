package com.ebay.testpages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.ebay.comlib.CommonLibrary;
import com.ebay.comlib.CommonReusableMethods;
import com.relevantcodes.extentreports.ExtentTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSFindBy;

public class DemoPage {

	CommonLibrary comlib= new CommonLibrary();
	CommonReusableMethods commonReusableMethods= new CommonReusableMethods();
	ExtentTest extest;
	
	
	/* Constructor is created so that webelemwnts can be initialized wheneven an instance of this 
	 * class is created like i have done in DemoTest Class
	 */
	public DemoPage(WebDriver driver, ExtentTest test) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
		extest=test;
	}

	/* For using common code base i am using common webelement variable */
	
	@iOSFindBy(xpath = "home")
	@AndroidFindBy(accessibility = "Main navigation, open")
	public WebElement hamburgerMenu;

	@iOSFindBy(id = "menuitem_settings")
	@AndroidFindBy(id = "menuitem_settings")
	public WebElement hamBurgerMenuSettingsOption;

	@iOSFindBy(id = "Country/region button")
	@AndroidFindBy(accessibility = "Country/region button")
	public WebElement countryRegionButton;

	@iOSFindBy(id = "switchWidget")
	@AndroidFindBy(id = "android:id/switchWidget")
	public WebElement autoDetectCheckbox;

	@iOSFindBy(id = "title")
	@AndroidFindBy(xpath = "//android.widget.ListView/android.widget.LinearLayout[3]/android.widget.RelativeLayout")
	public WebElement countryRegionButtonCountryScreen;

	@iOSFindBy(id = "filter")
	@AndroidFindBy(id = "filter")
	public WebElement searchCountryEditbox;

	@iOSFindBy(id = "title")
	@AndroidFindBy(xpath = "//android.widget.ListView/android.widget.CheckedTextView")
	public List<WebElement> countryList;

	@iOSFindBy(id = "title")
	@AndroidFindBy(xpath = "//android.widget.LinearLayout[3]/android.widget.RelativeLayout/android.widget.TextView[2]")
	public WebElement countryName;


	/* Passing platform name so that if there is some difference in flow of iOS and android then this can be used to 
	 * differentiate
	 */
	@SuppressWarnings("rawtypes")
	public void selectCountryAndRegion(AppiumDriver driver,String platform,String expectedCountry) throws Exception
	{
		boolean check;


		try {
			
			/*This Reusable method is called from common library which check for availability of an element and I have pass and fail condition reporting
			 * inside this method only
			 */
			comlib.validateForElementDisplayed(hamburgerMenu, driver, extest, "verify if Hamburger menu is displayed in homescreen", "",
					"", "Hamburger menu is not displayed in homescreen");
			hamburgerMenu.click();

			/* Below line method will scroll up as long as Settings button not displayed in screen and return boolean value 
			 * Through which further steps of code can be decided
			 * */
			check=commonReusableMethods.swipeToElement(hamBurgerMenuSettingsOption, driver, extest);
			if(check)
			{
				hamBurgerMenuSettingsOption.click();

				comlib.validateForElementDisplayed(countryRegionButton, driver, extest, "verify if Select country/Region displayed in settings", "",
						"", "Select country/Region not displayed in settings");
				countryRegionButton.click();

				comlib.validateForElementDisplayed(autoDetectCheckbox, driver, extest, "verify if Auto detect checkbox displayed", "",
						"", "Auto detect checkbox not displayed");
				check=autoDetectCheckbox.isSelected();

				if(check)
				{
					autoDetectCheckbox.click();
				}

				countryRegionButtonCountryScreen.click();
				comlib.validateForElementDisplayed(searchCountryEditbox, driver, extest, "verify if country search box displayed", "",
						"", "country search box not displayed");
/* 2 ways are there to select Australia 
 * 1. First way is written below where i search for Australia and get list of countries and verify the list value contains 
 *    Australia and select if matches.
 * 2. Without passing in search box we can take entire visible list and verify if australia is present by comparing string and keep 
 *    scrolling up until autralia is displayed
 */
				searchCountryEditbox.sendKeys(expectedCountry);

				for(WebElement st: countryList)
				{
					if(st.getText().equals(expectedCountry))
					{
						st.click();		
						break;
					}
				}
				
				comlib.validateForElementDisplayed(countryName, driver, extest, "verify if user redirected to selected country screen", "",
						"", "user not redirected to selected country screen");
				/*Below i am verifying if Australia text displayed under Country/Region */
				check=countryName.getText().equals(expectedCountry);
				
				comlib.verifyForCondition(driver, extest, check, "Verify if selected country is reflected as expected", "", 
						"Expected value was "+expectedCountry+" and forund is "+countryName.getText());
				
			}
		}catch (Exception e) {
			
			/*Here we can put a code which takes the control to home screen from where next test case will start executing*/
			comlib.fail(driver,"Check Execution Status", "", "Test case got failed", extest);		
		}

	}

}
