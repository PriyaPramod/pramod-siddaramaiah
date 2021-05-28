package com.monefy.steps;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.monefy.context.TestContext;
import com.monefy.managers.FileReaderManager;
import com.monefy.utils.Helper;
import com.monefy.utils.IConstants;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class BaseStep {

	TestContext testContext;

	public BaseStep(TestContext testContext) {
		this.testContext = testContext;
	}

	@Before
	public void setUp(Scenario scenario) {
		ExtentCucumberAdapter.addTestStepLog("***********************************************");
		ExtentCucumberAdapter.addTestStepLog("Test execution started for Scenario --> " + scenario.getName());
		ExtentCucumberAdapter.addTestStepLog("***********************************************");
	}

	@After
	public void tearDown(Scenario scenario) {
		ExtentCucumberAdapter.addTestStepLog("***********************************************");
		ExtentCucumberAdapter.addTestStepLog("Test execution completed for Scenario --> " + scenario.getName());
		ExtentCucumberAdapter.addTestStepLog("***********************************************");
		
		if (FileReaderManager.getInstance().getConfigReader(IConstants.EnvPath).getProperty("Environment")
				.equalsIgnoreCase("mobile")) {
			if (scenario.isFailed()) {
				scenario.attach(Helper.getByteScreenshot(testContext.driverManager().getAndroidDriver()), "image/png",
						scenario.getName());
			}
			testContext.driverManager().closeAndroidDriver();
		}
	}
}
