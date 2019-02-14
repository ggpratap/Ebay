package com.ebay.testscripts;

import org.testng.annotations.Test;

import com.ebay.base.SuperClass;
import com.ebay.comlib.CommonLibrary;
import com.ebay.comlib.CommonReusableMethods;
import com.ebay.reports.ExtentTestManager;
import com.ebay.testpages.DemoPage;




public class DemoTest extends SuperClass{

	/* This Class is inheriting superclass so driver control will be available and also Platform name*/
	CommonLibrary comlib = new CommonLibrary();
	CommonReusableMethods commonReusableMethods= new CommonReusableMethods();
	String countryToSelect="Australia";


	@Test(priority=1)
	public void verifyAppInstalled() throws Exception {

		/*Creating Instance of DemoPage to initialize all the webelement variables*/
		
		DemoPage demopage= new DemoPage(driver, ExtentTestManager.getTest());
		
		/*calling method to select region or country*/
		demopage.selectCountryAndRegion(driver, osType, countryToSelect);
	}







}
