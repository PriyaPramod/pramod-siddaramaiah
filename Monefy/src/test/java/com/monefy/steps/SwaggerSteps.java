package com.monefy.steps;

import org.testng.Assert;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.monefy.builders.CategoryBuilder;
import com.monefy.builders.CreatePetRequestBuilder;
import com.monefy.builders.TagsBuilder;
import com.monefy.managers.FileReaderManager;
import com.monefy.requests.Category;
import com.monefy.requests.CreatePetRequest;
import com.monefy.requests.Tags;
import com.monefy.responses.CreatePetResponse;
import com.monefy.utils.Helper;
import com.monefy.utils.IConstants;
import com.monefy.utils.RequestHelper;
import com.monefy.utils.ResourceHelper;
import com.monefy.utils.ResponseHelper;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class SwaggerSteps {

	RequestSpecification request;
	Response response;
	int actualStatusCode;
	String contentType;
	Category category;
	Tags tags;
	Tags[] tagsList;
	CreatePetRequest createPetRequest;
	CreatePetResponse createPetResponse;
	Response findSpecificPetResponse;
	String url;
	String json;

	@Given("Create {string} with {string}")
	public void create_with(String categoryValue, String tagValue) {
		category = new CategoryBuilder().withId(1).withName(categoryValue).build();
		tags = new TagsBuilder().withId(1).withName(tagValue).build();
	}

	@Given("Create Pet request with {string} and {string} using {string}")
	public void create_pet_request_with_and_and_using(String name, String status, String endPoint) {
		tagsList = new Tags[1];
		tagsList[0] = tags;

		String[] photoUrls = { "test" };
		createPetRequest = new CreatePetRequestBuilder().withId(Helper.getRandomInt())
				.withName(name + Helper.getRandomInt()).withCategory(category).withPhotoUrls(photoUrls)
				.withTags(tagsList).withStatus(status).build();

		url = FileReaderManager.getInstance().getConfigReader(IConstants.COMMON_PROPERTIES).getProperty("baseURI")
				+ endPoint;
		json = RequestHelper.getJsonString(createPetRequest);
		response = ResourceHelper.create(url, json);
	}

	@Given("Create Pet request {string}")
	public void create_pet_request(String endPoint) {

		createPetRequest = new CreatePetRequestBuilder().build();

		url = FileReaderManager.getInstance().getConfigReader(IConstants.COMMON_PROPERTIES).getProperty("baseURI")
				+ endPoint;
		json = RequestHelper.getJsonString(createPetRequest);

		response = ResourceHelper.create(url, json);
	}

	@Then("I Validate the {string}")
	public void i_validate_the(String statusCode) {
		createPetResponse = (CreatePetResponse) ResponseHelper.getResponseAsObject(response.asString(),
				CreatePetResponse.class);
		Assert.assertEquals(response.getStatusCode(), Integer.parseInt(statusCode));
	}

	@Then("I Validate Request body and Response Body")
	public void i_validate_request_body_and_response_body() {
		Assert.assertEquals(createPetRequest.getName(), createPetResponse.getName(),
				"Failure: Request body & Response body NAME are not same. ");
		ExtentCucumberAdapter.addTestStepLog("info -> Request body & Response body NAME are same.  ");
		Assert.assertEquals(createPetRequest.getStatus(), createPetResponse.getStatus(),
				"Failure: Request body & Response body STATUS are not same.");
		ExtentCucumberAdapter.addTestStepLog("info -> Request body & Response body STATUS are same.");
		Assert.assertEquals(createPetRequest.getTags()[0].getName(), createPetResponse.getTags()[0].getName(),
				"Failure: Request body & Response body TAGS are not same.");
		ExtentCucumberAdapter.addTestStepLog("info -> Request body & Response body TAGS are same.");
	}

	@Then("I Verify created Pet using {string}")
	public void i_verify_created_pet(String petEndPoint) {
		String url1 = FileReaderManager.getInstance().getConfigReader(IConstants.COMMON_PROPERTIES)
				.getProperty("baseURI") + petEndPoint + createPetRequest.getId();

		findSpecificPetResponse = ResourceHelper.get(url1);

		CreatePetResponse getPetResponseBasedOnId = (CreatePetResponse) ResponseHelper
				.getResponseAsObject(findSpecificPetResponse.asString(), CreatePetResponse.class);

		Assert.assertEquals(findSpecificPetResponse.getStatusCode(), 200);
		Assert.assertEquals(createPetRequest.getId(), getPetResponseBasedOnId.getId());
		Assert.assertEquals(createPetRequest.getName(), getPetResponseBasedOnId.getName());
	}

	@Then("I Delete the pet using pet id {string}")
	public void i_Delete_the_pet_using_pet_id(String endPoint) {
		String deleteUrl = FileReaderManager.getInstance().getConfigReader(IConstants.COMMON_PROPERTIES)
				.getProperty("baseURI") + endPoint + createPetRequest.getId();
		System.out.println(deleteUrl);
		findSpecificPetResponse = ResourceHelper.delete(deleteUrl);
	}

	@Then("I Verify pet deleted successfully")
	public void i_Verify_pet_deleted_successfully() {
		Assert.assertEquals(findSpecificPetResponse.getStatusCode(), 200,
				"Failure: Unable to delete the Pet using the PetID. ");
		ExtentCucumberAdapter.addTestStepLog("info -> Succesfully deleted the pet using the Pet ID. ");
		ExtentCucumberAdapter.addTestStepLog("info -> " + findSpecificPetResponse.asPrettyString());
	}

}
