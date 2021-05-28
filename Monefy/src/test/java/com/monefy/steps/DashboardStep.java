package com.monefy.steps;

import org.testng.Assert;

import com.monefy.context.TestContext;
import com.monefy.utils.Helper;
import com.money.screens.DashboardScreen;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DashboardStep {

	DashboardScreen dashboardScreen;
	int incomeAmount;
	int expenseAmount;

	public DashboardStep(TestContext testContext) {
		dashboardScreen = testContext.pageManager().getDashboardScreen();
	}

	@When("Select Range from calenader")
	public void select_range_from_calenader() {
		dashboardScreen.openNavigationPane();
		dashboardScreen.selectTheRangeFromInterval();
	}

	@Then("Validate I am able to select the Interval")
	public void validate_i_am_able_to_select_the_interval() {
		Assert.assertTrue(dashboardScreen.userSelectedRangeDisplayedOnDashboard(),
				"Failure: User selected interval range is not displaying on the Dashboard. ");
	}

	@Given("Dashboard screen is displayed")
	public void dashboard_screen_is_displayed() {
		Assert.assertTrue(dashboardScreen.dashboardDisplayed(), "Failure: Dashboard screen is not displayed. ");
	}

	@Given("Add Income {string} by selecting the category {string}")
	public void add_income_by_selecting_the_category(String income, String category) {
		incomeAmount = Helper.parseStringToInt(income);
		dashboardScreen.addIncome(income, category);
	}

	@Given("Add Expense {string} by selecting the category {string}")
	public void add_expense_by_selecting_the_category(String expense, String category) {
		expenseAmount = Helper.parseStringToInt(expense);
		dashboardScreen.addExpense(expense, category);
	}

	@Then("I validate balance is calculated correctly")
	public void i_validate_balance_is_calculated_correctly() {
		int balance = dashboardScreen.returnBalance();
		Assert.assertEquals(balance, (incomeAmount - expenseAmount),
				"Failure: Balance is not matching with income and expenses. ");
	}
}
