package StepDefs;

import java.util.Random;

import org.junit.Assert;

import Logging.Logger;
import Sources.ExpectedResponse;
import Sources.HttpMethod;
import Sources.Method;
import Sources.RequestType;
import Utils.StringUtils;
import Utils.UrlBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DynamicDataStepDefs {
	private int expectedByteLength;
	private long expectedDelay;
	private long actualDelay;

	@Given("^I generate bytes length")
	public void givenIGenerateBytesLength() {
		expectedByteLength = new Random().nextInt(10000);
		Method.BYTES.setValidPath("/" + expectedByteLength);
		Method.BYTES.setInvalidPath("/" + StringUtils.randomString.get());

		Logger.info.accept(String.format("Expected length of random byte array is [%d]", expectedByteLength));
	}

	@Given("^I generate delay in seconds$")
	public void givenIGenerateDelay() {
		expectedDelay = new Random().nextInt(10);
		Method.DELAY.setValidPath("/" + expectedDelay);

		Logger.info.accept(String.format("Expected delay is [%d] sec", expectedDelay));
	}

	@Given("^I generate invalid path and set maximum expected delay$")
	public void givenISetMaxDelay() {
		Method.DELAY.setInvalidPath("/" + 10 + Math.abs(new Random().nextInt()));

		expectedDelay = 10;

		Logger.info.accept(String.format("Expected delay is [%d] sec", expectedDelay));
	}

	@When("^I execute (\\S+) (\\S+) http method$")
	public void whenIExecuteSpecificHttpMethod(RequestType requestType, HttpMethod httpMethod) {
		String url = new UrlBuilder(Method.DELAY).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		long startTime = System.currentTimeMillis();
		httpMethod.factory().apply(url);
		long endTime = System.currentTimeMillis();

		actualDelay = (endTime - startTime) / 1000;

		Logger.info.accept(String.format("Execution time is [%d] sec", actualDelay));
	}

	@When("^I make (\\S+) (\\S+) request$")
	public void whenIMakeBase64RequestWithData(RequestType requestType, Method method) {
		String url = new UrlBuilder(method).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		Response response = RestAssured.given().get(url);

		method.saveResponse(response);

		if (!method.equals(Method.BYTES)) {
			Logger.info.accept(String.format("Response [%s] has been saved", response.print()));
		} else {
			Logger.info.accept(String.format("Response status code is [%s]", response.statusCode()));
		}
	}

	@Then("^I see that (.*) has (.*) response$")
	public void thenICompareResponses(Method method, ExpectedResponse expectedResponse) {
		Assert.assertEquals("Responses should be equal", expectedResponse.getExpectedResponse(), method.getSavedResponse().print());

		method.removeResponse();

		Logger.info.accept("Actual response has been reset to [null]");
	}

	@Then("^I see that random bytes array has right length$")
	public void thenISeeThatRandomBytesArrayHasRightLength() {
		Assert.assertEquals("Actual length of random bytes array should match with expected",
				expectedByteLength, Method.BYTES.getSavedResponse().asByteArray().length);

		Method.BYTES.removeResponse();
	}

	@Then("^I check that response delay is correct$")
	public void thenICheckThatResponseDelayIsCorrect() {
		// delta contains code execution time;
		Assert.assertEquals("Actual response delay and expected delay should match with delta [1]",
				expectedDelay, actualDelay, 1);
	}
}
