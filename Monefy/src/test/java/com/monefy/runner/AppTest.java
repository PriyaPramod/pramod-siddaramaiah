package com.monefy.runner;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.monefy.utils.AppiumServer;
import com.monefy.utils.Helper;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * @author - Pramod K S
 */
@CucumberOptions(features = { "src/test/java/Features/" }, tags = "@Monefy", glue = "com.monefy.steps", plugin = {
		"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
		"pretty" }, monochrome = true, publish = true)
public class AppTest extends AbstractTestNGCucumberTests {

	AppiumServer appiumServer;

	@BeforeClass
	@Parameters({ "Environment" })
	public void beforeClass(@Optional("api") String Environment) {
		Helper.setProperty(Environment);
		if (Environment.equalsIgnoreCase("Mobile")) {
			appiumServer = new AppiumServer();
			appiumServer.startAppiumServer();
			System.out.println("Started Appium server. ");
		}
	}

	@AfterClass
	@Parameters({ "Environment" })
	public void afterClass(@Optional("api") String Environment) {
		if (Environment.equalsIgnoreCase("Mobile")) {
			appiumServer.stopAppiumServer();
			System.out.println("Stopped Appium server. ");
			Helper.createPlatform();
		}
	}

}