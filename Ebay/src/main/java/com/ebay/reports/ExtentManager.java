/**
 * 
 */
package com.ebay.reports;

import java.io.File;

import com.ebay.comlib.ConfigurationLibrary;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentManager {
	static ExtentReports extent;
    
	public synchronized static ExtentReports getReporter() {
    	File classRootPath = new File(System.getProperty("user.dir"));
		File appDir = new File(classRootPath, "");
		File path = new File(appDir, ConfigurationLibrary.extentConfigFile);
        if (extent == null) {
            extent = new ExtentReports(ConfigurationLibrary.TestResultPath, true);
            extent.loadConfig(new File(path.toString()));
        }
        return extent;
    }
}