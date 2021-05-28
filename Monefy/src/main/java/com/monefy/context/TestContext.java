package com.monefy.context;

import com.monefy.managers.DriverManager;
import com.monefy.managers.FileReaderManager;
import com.monefy.managers.PageManager;
import com.monefy.utils.IConstants;

public class TestContext {

	private PageManager pageManager;
	private DriverManager driverManager;

	public TestContext() {
		driverManager = new DriverManager();
		if (FileReaderManager.getInstance().getConfigReader(IConstants.EnvPath).getProperty("Environment")
				.equalsIgnoreCase("mobile")) {
			pageManager = new PageManager(driverManager.getAndroidDriver());
		}
	}

	public DriverManager driverManager() {
		return driverManager;
	}

	public PageManager pageManager() {
		return pageManager;
	}

}
