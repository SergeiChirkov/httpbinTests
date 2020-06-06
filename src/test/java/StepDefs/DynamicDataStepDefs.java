package StepDefs;

import java.util.Random;

import org.junit.Assert;

import Logging.Logger;
import Sources.ExpectedResponse;
import Sources.Method;
import Sources.RequestType;
import Utils.UrlBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

public class DynamicDataStepDefs {
	private String actualResponse = "Not set";

	private void tearDown() {
		actualResponse = "Not set";

		Logger.info.accept(String.format("Actual response has been reset to [%s]", actualResponse));
	}

	@Given("^I generate bytes length")
	public void givenIGenerateBytesLength() {
		int expectedLenth = new Random().nextInt(10000);
		Method.BYTES.setValidPath("/" + expectedLenth);
		ExpectedResponse.BYTES_LENGTH.setExpectedResponse(String.valueOf(expectedLenth));
	}

	@When("^I make (.*) base64 request$")
	public void whenIMakeBase64RequestWithData(RequestType requestType) {
		String url = new UrlBuilder(Method.BASE64).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		actualResponse = RestAssured.given().get(url).print();

		Logger.info.accept(String.format("Response [%s] has been saved", actualResponse));
	}

	@When("^I make (.*) bytes request$")
	public void whenIMakeBytesRequest(RequestType requestType) {
		String url = new UrlBuilder(Method.BYTES).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		actualResponse = String.valueOf(RestAssured.given().get(url).asByteArray().length);

		Logger.info.accept(String.format("Response length [%s] has been saved as actual response", actualResponse));

	}

	@Then("^I see (.*) response$")
	public void thenICompareResponses(ExpectedResponse expectedResponse) {
		Assert.assertEquals("Responses should be equal", expectedResponse.getExpectedResponse(), actualResponse);

		tearDown();
	}
}
