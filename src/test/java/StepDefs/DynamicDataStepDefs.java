package StepDefs;

import org.junit.Assert;

import Logging.Logger;
import Sources.ExpectedResponse;
import Sources.Method;
import Sources.RequestType;
import Utils.UrlBuilder;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

public class DynamicDataStepDefs {
	private String actualResponseText = "Not set";

	private void tearDown() {
		actualResponseText = "Not set";

		Logger.info.accept(String.format("Actual response text has been reset to [%s]", actualResponseText));
	}

	@When("^I make (.*) base64 request$")
	public void whenIMakeBase64RequestWithData(RequestType requestType) {
		String url = new UrlBuilder(Method.BASE64).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		actualResponseText = RestAssured.given().get(url).print();

		Logger.info.accept(String.format("Response [%s] has been saved", actualResponseText));
	}

	@Then("^I see (.*) response$")
	public void thenICompareResponses(ExpectedResponse expectedResponse) {
		Assert.assertEquals("Responses should be equal", expectedResponse.getResponseText(), actualResponseText);

		tearDown();
	}
}
