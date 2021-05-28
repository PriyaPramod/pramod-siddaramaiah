package com.monefy.managers;

import com.money.screens.DashboardScreen;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class PageManager {

	AndroidDriver<AndroidElement> androidDriver;
	DashboardScreen dashboardScreen;

	public PageManager(AndroidDriver<AndroidElement> androidDriver) {
		this.androidDriver = androidDriver;
	}

	public DashboardScreen getDashboardScreen() {
		return (dashboardScreen == null) ? dashboardScreen = new DashboardScreen(this.androidDriver) : dashboardScreen;
	}

}
