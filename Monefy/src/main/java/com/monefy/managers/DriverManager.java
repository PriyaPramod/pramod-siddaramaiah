package com.monefy.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

import com.monefy.utils.Helper;
import com.monefy.utils.IConstants;
import com.monefy.utils.ThreadLocalDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverManager implements IConstants {

	private AndroidDriver<AndroidElement> androidDriver;
	private DesiredCapabilities caps;
	Process process;
	BufferedReader is;
	String line;
	public String UDID;
	public String deviceName;
	public String androidVersion;

	public AndroidDriver<AndroidElement> getAndroidDriver() {
		if (androidDriver == null)
			androidDriver = createAppiumDriver();
		return androidDriver;
	}

	private AndroidDriver<AndroidElement> createAppiumDriver() {
		UDID = getUDID();
		deviceName = getDeviceName();
		androidVersion = getAndroidVersion();

		caps = new DesiredCapabilities();
		caps.setCapability(MobileCapabilityType.APPLICATION_NAME,
				FileReaderManager.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("applicationName"));
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME,
				FileReaderManager.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("platformName"));
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, androidVersion);
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		caps.setCapability(MobileCapabilityType.UDID, UDID);
		caps.setCapability(MobileCapabilityType.CLEAR_SYSTEM_FILES, true);
		caps.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
		caps.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
		caps.setCapability(AndroidMobileCapabilityType.APP_WAIT_DURATION, 10000);
		try {
			String apkPath = System.getProperty("user.dir") + "/src/test/resources/app/"
					+ FileReaderManager.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("app");

			caps.setCapability(MobileCapabilityType.APP, new File(apkPath).getAbsolutePath());

			ThreadLocalDriver.setTLDriver(new AndroidDriver<AndroidElement>(new URL(
					FileReaderManager.getInstance().getConfigReader(ANDROID_CAPABILITIES).getProperty("appium_url")),
					caps));
			ThreadLocalDriver.getTLDriver().manage().timeouts().implicitlyWait(
					FileReaderManager.getInstance().getConfigReader(COMMON_PROPERTIES).getImplicitlyWait(),
					TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
		}
		return ThreadLocalDriver.getTLDriver();
	}

	public void closeAndroidDriver() {
		ThreadLocalDriver.getTLDriver().quit();
	}

	public String getUDID() {
		try {
			process = Runtime.getRuntime().exec("adb devices");
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader is = new BufferedReader(new InputStreamReader(process.getInputStream()));
		String line;

		try {
			while ((line = is.readLine()) != null) {
				if (Helper.hasDigits(line)) {
					UDID = line.split("\t")[0];
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return UDID;
	}

	public String getDeviceName() {
		try {
			process = Runtime.getRuntime().exec("adb devices -l");
		} catch (IOException e) {
			e.printStackTrace();
		}
		is = new BufferedReader(new InputStreamReader(process.getInputStream()));

		try {
			while ((line = is.readLine()) != null) {
				if (line.contains(UDID)) {
					if (line.contains("product")) {
						deviceName = line.substring(line.lastIndexOf("device:") + 7);
						deviceName = deviceName.split(" ")[0];
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return deviceName;
	}

	public String getAndroidVersion() {
		try {
			process = Runtime.getRuntime().exec("adb -s " + UDID + " shell getprop ro.build.version.release");
		} catch (IOException e) {
			e.printStackTrace();
		}
		is = new BufferedReader(new InputStreamReader(process.getInputStream()));

		try {
			while ((line = is.readLine()) != null) {
				if (line != null) {
					androidVersion = line;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return androidVersion;
	}
}
