package com.money.screens;

import org.openqa.selenium.By;

import com.monefy.utils.Helper;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class DashboardScreen extends BaseScreen {

	public DashboardScreen(AndroidDriver<AndroidElement> androidDriver) {
		super(androidDriver);
	}

	@AndroidFindBy(id = "com.monefy.app.lite:id/income_button_title")
	private AndroidElement incomeButton;

	@AndroidFindBy(id = "com.monefy.app.lite:id/expense_button")
	private AndroidElement expenseButton;

	@AndroidFindBy(id = "com.monefy.app.lite:id/balance_amount")
	private AndroidElement balanceAmountText;

	@AndroidFindBy(id = "com.monefy.app.lite:id/income_amount_text")
	private AndroidElement incomeAmountText;

	@AndroidFindBy(id = "com.monefy.app.lite:id/expense_amount_text")
	private AndroidElement expenseAmountText;

	@AndroidFindBy(id = "com.monefy.app.lite:id/amount_text")
	private AndroidElement amountTextBox;

	@AndroidFindBy(id = "com.monefy.app.lite:id/textViewNote")
	private AndroidElement noteTextbox;

	@AndroidFindBy(id = "com.monefy.app.lite:id/keyboard_action_button")
	private AndroidElement chooseCategoryButton;

	@AndroidFindBy(xpath = "//android.widget.TextView[@text='Salary']/preceding-sibling::*")
	private AndroidElement categoryOption;

	@AndroidFindBy(accessibility = "Open navigation")
	private AndroidElement openNavigation;

	@AndroidFindBy(id = "com.monefy.app.lite:id/custom_period_button")
	private AndroidElement intervalButton;

	@AndroidFindBy(accessibility = "Save")
	private AndroidElement saveButton;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@resource-id=\"com.monefy.app.lite:id/pts_main\"]/child::android.widget.TextView")
	private AndroidElement selectedRangeOnDashboard;

	public boolean userSelectedRangeDisplayedOnDashboard() {
		String rangeOnDashboard = selectedRangeOnDashboard.getText();

		if (Helper.getTodaysDate().contains(rangeOnDashboard.split("-")[1].trim())
				&& Helper.getPreviousDate().contains(rangeOnDashboard.split("-")[0].trim())) {
			flag = true;
			print("User selected range is displyed on the dashboard. ");
			print("Interval range displayed on the dashboard : " + rangeOnDashboard);
			print("End date selected by user: " + Helper.getTodaysDate());
			print("Start date selected by user: " + Helper.getPreviousDate());
		} else {
			flag = false;
		}
		return flag;
	}

	public void openNavigationPane() {
		click(openNavigation);
	}

	public void clickOnSaveButton() {
		click(saveButton, "Clicked on SAVE button. ");
	}

	public void selectInterval() {
		click(intervalButton);
	}

	public void selectTheRangeFromInterval() {
		selectInterval();
		String previoustDateXPath = "//android.widget.TextView[@content-desc='" + Helper.getPreviousDate() + "']";
		AndroidElement previousDate = androidDriver.findElement(By.xpath(previoustDateXPath));
		click(previousDate, "Clicked on the previous date as a start Date. ");

		String currentDateXPath = "//android.widget.TextView[@content-desc='" + Helper.getTodaysDate() + "']";
		AndroidElement currentDate = androidDriver.findElement(By.xpath(currentDateXPath));
		click(currentDate, "Clicked on the current date as a end Date. ");

		clickOnSaveButton();
	}

	private AndroidElement getCategoryFromChooseCategory(String categoryName) {
		String categoryXPath = "//android.widget.TextView[@text='" + categoryName + "']/preceding-sibling::*";
		return androidDriver.findElement(By.xpath(categoryXPath));
	}

	public int returnBalance() {
		return Helper.returnDigits(balanceAmountText.getText());
	}

	public void addIncome(String incomeAmount, String category) {
		click(incomeButton, "Clicked on the income button in Dashboard screen. ");
		enterAmount(incomeAmount);
		sendKeys(noteTextbox, Helper.generateRandomString(10));
		click(chooseCategoryButton, "Clicked on the choose category button in New Income Screen. ");
		click(getCategoryFromChooseCategory(category), "Selected " + category + " Category to add new income. ");
	}

	public void addExpense(String expenseAmount, String category) {
		click(expenseButton, "Clicked on the expense button in Dashboard screen. ");
		enterAmount(expenseAmount);
		sendKeys(noteTextbox, Helper.generateRandomString(10));
		click(chooseCategoryButton, "Clicked on the choose category button in New Expense Screen. ");
		click(getCategoryFromChooseCategory(category), "Selected " + category + " Category to add new expense. ");
	}

	public boolean dashboardDisplayed() {
		if (elementDisplayed(balanceAmountText)) {
			flag = true;
			print("Dashboard screen is displayed. ");
		} else {
			flag = false;
		}
		return flag;
	}

	public void enterAmount(String amount) {
		char[] ch = amount.toCharArray();
		String key = "";
		String keyboardID = "com.monefy.app.lite:id/buttonKeyboard";

		for (char c : ch) {
			key = keyboardID + c;
			click(androidDriver.findElement(By.id(key)), "Clicked " + c + " number button on the keyboard. ");
			key = "";
		}
	}
}
