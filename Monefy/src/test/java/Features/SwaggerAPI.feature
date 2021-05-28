#Author: kspramod13@gmail.com
@Monefy @API
Feature: Swagger API Automation at http://localhost:8080/api/v3/

  @CreatePets
  Scenario Outline: Add new Pet to Strore and Validating the response
    Given Create "<Category>" with "<Tag>"
    And Create Pet request with "<Name>" and "<Status>" using "<EndPoint>"
    Then I Validate the "<StatusCode>"
    Then I Validate Request body and Response Body
    Then I Verify created Pet using "<petIDEndPoint>"

    Examples: 
      | Category | Tag   | Name    | Status    | EndPoint     | StatusCode | petIDEndPoint |
      | Dogs     | Tag 1 | Testing | available | /api/v3/pet/ |        200 | /api/v3/pet/  |

  @DeletePet
  Scenario Outline: Delete the pet using ID and validate Pet deleted.
    Given Create "<Category>" with "<Tag>"
    And Create Pet request with "<Name>" and "<Status>" using "<EndPoint>"
    Then I Validate the "<StatusCode>"
    Then I Delete the pet using pet id "<EndPoint>"
    Then I Verify pet deleted successfully

    Examples: 
      | Category | Tag   | Name    | Status    | EndPoint     | StatusCode |
      | Dogs     | Tag 1 | Testing | available | /api/v3/pet/ |        200 |
