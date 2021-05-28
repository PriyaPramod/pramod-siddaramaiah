package com.money.screens;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.monefy.managers.FileReaderManager;
import com.monefy.utils.IConstants;
import com.monefy.utils.MobileActions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class BaseScreen {
	protected AndroidDriver<AndroidElement> androidDriver;
	protected WebDriverWait wait;
	protected JavascriptExecutor jS;
	protected String value;
	protected boolean flag;
	protected MobileActions mobileActions;

	/**
	 * This constructor is used to initialize the Android Driver
	 *
	 * @param androidDriver driver
	 * @author pramod.ks
	 */
	public BaseScreen(AndroidDriver<AndroidElement> androidDriver) {
		this.androidDriver = androidDriver;
		PageFactory.initElements(new AppiumFieldDecorator(androidDriver), this);
		wait = new WebDriverWait(androidDriver,
				FileReaderManager.getInstance().getConfigReader(IConstants.COMMON_PROPERTIES).getImplicitlyWait());
		jS = androidDriver;
		mobileActions = new MobileActions(androidDriver);
	}

	protected void wait(int sec) {
		try {
			Thread.sleep(sec * 1000);
		} catch (InterruptedException exp) {
			print("Error: while waiting for " + (sec) + " seconds" + exp.toString());
		}
	}

	protected boolean verifyTextOfAnElement(AndroidElement element, String textToVerify) {
		flag = false;
		try {
			flag = wait.until(ExpectedConditions.textToBePresentInElement(element, textToVerify));
		} catch (TimeoutException exp) {
			flag = false;
			System.out.printf("Exception: Expected text '-%s-' is not present in the element", textToVerify);
		}
		return flag;
	}

	protected String getText(AndroidElement elementToGetText) {
		value = "";
		try {
			elementDisplayed(elementToGetText);
			value = elementToGetText.getText();
		} catch (Exception exp) {
			System.out.printf("Exception: %s while trying to fetch the text of an element: ", exp);
		}
		return value;
	}

	protected void sendKeys(AndroidElement elementToEnterText, String textToEnter) {
		if (elementDisplayed(elementToEnterText)) {
			elementToEnterText.sendKeys(textToEnter);
		} else {
			throw new RuntimeException(
					"Unable to type the text into Android text box as the Android element is Not Visible/Intractable. ");
		}
	}

	protected void click(AndroidElement elementToClick, String comment) {
		try {
			elementToClick.click();
			print(comment);
		} catch (Exception exp) {
			try {
				mobileActions.tap(elementToClick);
				print(comment);
			} catch (Exception ex) {
				throw new RuntimeException(
						"Unable to click on the Android Element. " + ex.toString() + "\n" + exp.toString());
			}
		}
	}

	protected void click(AndroidElement elementToClick) {
		try {
			elementToClick.click();
		} catch (Exception exp) {
			try {
				mobileActions.tap(elementToClick);
			} catch (Exception ex) {
				throw new RuntimeException(
						"Unable to click on the Android Element. " + ex.toString() + "\n" + exp.toString());
			}
		}
	}

	protected boolean elementClickable(AndroidElement eleToCheck) {
		flag = false;
		try {
			wait.until(ExpectedConditions.elementToBeClickable(eleToCheck));
			flag = true;
		} catch (TimeoutException exp) {
			print("%s is not visible\n"+exp);
		}
		return flag;
	}

	protected boolean elementDisplayed(AndroidElement eleToCheck) {
		flag = false;
		try {
			wait.until(ExpectedConditions.visibilityOf(eleToCheck));
			flag = true;
		} catch (TimeoutException exp) {
			print("%s is not visible\n"+exp);
		}
		return flag;
	}

	protected void print(String strToPrint) {
		ExtentCucumberAdapter.addTestStepLog("info -> "+strToPrint);
	}

}
