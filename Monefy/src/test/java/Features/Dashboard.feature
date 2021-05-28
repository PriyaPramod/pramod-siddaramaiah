#Author: kspramod13@gmail.com
@Monefy @Mobile
Feature: Dashboard Screen Validation

  @Balance
  Scenario Outline: Validate Balance is calculated correctly
    Given Dashboard screen is displayed
    And Add Income "<Income>" by selecting the category "<incomeCategory>"
    And Add Expense "<Expense>" by selecting the category "<expenseCategory>"
    Then I validate balance is calculated correctly

    Examples: 
      | incomeCategory | expenseCategory | Income | Expense |
      | Salary         | Bills           |     50 |      25 |

  @SelectInterval
  Scenario Outline: Validate user is able to select the range to see the financial status between the intervals
    Given Dashboard screen is displayed
    And Add Income "<Income>" by selecting the category "<incomeCategory>"
    And Add Expense "<Expense>" by selecting the category "<expenseCategory>"
    When Select Range from calenader
    Then Validate I am able to select the Interval

    Examples: 
      | incomeCategory | expenseCategory | Income | Expense |
      | Salary         | Bills           |    507 |     265 |
