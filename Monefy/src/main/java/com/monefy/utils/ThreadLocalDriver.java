package com.monefy.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;

public class ThreadLocalDriver {

	private static ThreadLocal<AndroidDriver<AndroidElement>> tlDriver = new ThreadLocal<>();
	
	private static ThreadLocal<IOSDriver<IOSElement>> tlIosDriver = new ThreadLocal<>();
	 
    public synchronized static void setTLDriver(AndroidDriver<AndroidElement> driver) {
        tlDriver.set(driver);
    }
 
	public synchronized static AndroidDriver<AndroidElement> getTLDriver() {
        return tlDriver.get();
    }
	
    public synchronized static void setIosDriver(IOSDriver<IOSElement> driver) {
    	tlIosDriver.set(driver);
    }
 
	public synchronized static IOSDriver<IOSElement> getIosDriver() {
        return tlIosDriver.get();
    }

}
