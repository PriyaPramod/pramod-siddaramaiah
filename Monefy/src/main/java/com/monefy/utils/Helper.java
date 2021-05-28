package com.monefy.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.service.ExtentService;
import com.monefy.managers.FileReaderManager;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Helper {

	private static int value;
	private final static Random RANDOM = new SecureRandom();
	private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;

	/*
	 * If the string is empty return false Find match between given string and
	 * regular expression using Pattern.matcher() Return if the string has digits
	 */
	public static boolean hasDigits(String value) {
		boolean flag = false;
		if (value.matches(".*\\d.*")) {
			flag = true;
		}
		return flag;
	}

	public static int getRandomInt() {
		return RandomUtils.nextInt(100, 999);
	}

	public static String getTodaysDate() {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("E, d MMM");
		return sf.format(date.getTime());
	}

	public static String getPreviousDate() {
		Date today = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("E, d MMM");
		return sf.format(findPrevDay(today));
	}

	public static void setProperty(String environmentValue) {
		Properties property = new Properties();
		FileOutputStream output;
		try {
			output = new FileOutputStream(IConstants.EnvPath);
			property.setProperty("Environment", environmentValue);
			try {
				property.store(output, "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static Date findPrevDay(Date date) {
		return new Date(date.getTime() - MILLIS_IN_A_DAY);
	}

	public static int parseStringToInt(String valueToParse) {

		if (hasDigits(valueToParse)) {
			value = Integer.parseInt(valueToParse);
		} else {
			throw new RuntimeException("Unable to parse " + valueToParse);
		}
		return value;
	}

	public static int returnDigits(String strValue) {
		String value = strValue.replaceAll("[^0-9.]", "");
		return (int) Double.parseDouble(value);
	}

	public static byte[] getByteScreenshot(AndroidDriver<AndroidElement> androidDriver) {
		File src = ((TakesScreenshot) androidDriver).getScreenshotAs(OutputType.FILE);
		byte[] fileContent = new byte[0];
		try {
			fileContent = FileUtils.readFileToByteArray(src);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}

	public static String getCurrentDate() {
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("dd_MMM_yyyy_hh_mm_ss");
		return sf.format(date.getTime());
	}

	public static String generateRandomString(int length) {
		StringBuilder returnValue = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		return new String(returnValue);
	}

	public static String getAbsolutePathOfFile(String currentRelativePath) {
		String absolutePath = "";
		try {
			String apkPath = System.getProperty("user.dir") + currentRelativePath;
			absolutePath = new File(apkPath).getAbsolutePath();
		} catch (Exception exp) {
			System.out.println("Exception: While trying to get the Absolute path of a file: " + currentRelativePath
					+ " Exception is: " + exp.toString());
		}
		return absolutePath;
	}

	public static ByteArrayInputStream convertPngToByteInputStream(String imageFilePath) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(2000);
		BufferedImage img = null;
		ByteArrayInputStream imageByteArray = null;
		try {
			img = ImageIO.read(new File(imageFilePath));
			ImageIO.write(img, "jpg", baos);
			baos.flush();
			String base64String = new String(Base64.encodeBase64(baos.toByteArray()), "UTF-8");
			baos.close();
			byte[] bytearray = Base64.decodeBase64(base64String);
			imageByteArray = new ByteArrayInputStream(bytearray);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imageByteArray;
	}

	public static void createPlatform() {
		ExtentService.getInstance().setSystemInfo("Author", System.getProperty("user.name"));
		ExtentService.getInstance().setSystemInfo("Platform", FileReaderManager.getInstance()
				.getConfigReader(IConstants.ANDROID_CAPABILITIES).getProperty("platformName"));
		ExtentService.getInstance().setSystemInfo("App", FileReaderManager.getInstance()
				.getConfigReader(IConstants.ANDROID_CAPABILITIES).getProperty("applicationName"));
	}
}
