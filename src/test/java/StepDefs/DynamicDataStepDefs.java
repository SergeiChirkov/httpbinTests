package StepDefs;

import java.util.List;
import java.util.Random;

import org.junit.Assert;

import Logging.Logger;
import Sources.ExpectedResponse;
import Sources.HttpMethod;
import Sources.Method;
import Sources.RequestType;
import Sources.Status;
import Utils.UrlBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;

public class DynamicDataStepDefs {
	private int expectedByteLength;
	private long expectedDelay;
	private long expectedDuration;
	private int expectedResponseCode;
	private long actualDelay;
	private Long actualDuration;
	private int numberOfLinks;
	private int offset;
	private int expectedStreamNumber;

	@Given("^I generate bytes length for (BYTES|RANGE|STREAM_BYTES) method")
	public void givenIGenerateBytesLength(Method method) {
		expectedByteLength = new Random().nextInt(10000);
		method.setValidPath("/" + expectedByteLength);
		method.setInvalidPath("/" + -expectedByteLength);

		Logger.info.accept(String.format("Expected length of random byte array is [%d]", expectedByteLength));
	}

	@Given("^I generate stream number for (STREAM) method$")
	public void givenIGenerateStreamNumberForMethod(Method method) {
		expectedStreamNumber = 10 + new Random().nextInt(10);
		method.setValidPath("/" + expectedStreamNumber);
		method.setInvalidPath("/" + -expectedStreamNumber);

		Logger.info.accept(String.format("Expected number of streams is [%d]", expectedStreamNumber));
	}

	@Given("^I generate delay in seconds$")
	public void givenIGenerateDelay() {
		expectedDelay = new Random().nextInt(10);
		Method.DELAY.setValidPath("/" + expectedDelay);

		Logger.info.accept(String.format("Expected delay is [%d] sec", expectedDelay));
	}

	@Given("^I generate links$")
	public void givenIGenerateLinks() {
		numberOfLinks = new Random().nextInt(15);
		offset = new Random().nextInt(numberOfLinks);

		Method.LINKS.setValidPath(String.format("/%d/%d", numberOfLinks, offset));
		Method.LINKS.setInvalidPath(String.format("/%d/%d", -numberOfLinks, offset));

		Logger.info.accept(String.format("Generate [%d] links and offset [%d]", numberOfLinks, offset));
	}

	private Status getRandomStatus() {
		Status[] statuses = Status.values();
		int randomIndex = new Random().nextInt(statuses.length - 1);
		return statuses[randomIndex];
	}

	private void setDripData(long expectedDuration, int expectedByteLength, int expectedResponseCode, long expectedDelay) {
		Method.DRIP.setValidPath("?"
				+ "duration=" + expectedDuration
				+ "&numbytes=" + expectedByteLength
				+ "&code=" + expectedResponseCode
				+ "&delay=" + expectedDelay);

		Logger.info.accept(String.format("Following data hs been generated"
						+ "%n" + "duration = [%d]"
						+ "%n" + "length of array = [%d]"
						+ "%n" + "status code = [%d]"
						+ "%n" + "delay = [%d]",
				expectedDuration,
				expectedByteLength,
				expectedResponseCode,
				expectedDelay));
	}

	@Given("^I generate (.*) data for drip request$")
	public void givenIGenerateDripData(RequestType requestType) {
		expectedDuration = new Random().nextInt(5);
		expectedByteLength = new Random().nextInt(50);
		expectedResponseCode = getRandomStatus().getStatuscode();
		expectedDelay = new Random().nextInt(5);

		switch (requestType) {
			case VALID:
				setDripData(expectedDuration, expectedByteLength, expectedResponseCode, expectedDelay);
				break;
			case INVALID:
				setDripData(-expectedDuration, -expectedByteLength, -expectedResponseCode, -expectedDelay);
				break;
			default:
				throw new RuntimeException("Unexpected Request type is used: " + requestType.name());
		}
	}

	@Given("^I generate invalid path and set maximum expected delay$")
	public void givenISetMaxDelay() {
		Method.DELAY.setInvalidPath("/" + 10 + Math.abs(new Random().nextInt()));

		expectedDelay = 10;

		Logger.info.accept(String.format("Expected delay is [%d] sec", expectedDelay));
	}

	@When("^I execute (\\S+) (\\S+) (\\S+) request")
	public void whenIExecuteSpecificHttpMethod(RequestType requestType, HttpMethod httpMethod, Method method) {
		String url = new UrlBuilder(method).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		long startTime = System.currentTimeMillis();
		Response response = httpMethod.factory().apply(url);
		long endTime = System.currentTimeMillis();

		method.saveResponse(response);
		actualDelay = (endTime - startTime) / 1000;

		Logger.info.accept(String.format("Execution time is [%d] sec", actualDelay));

		startTime = System.currentTimeMillis();
		Logger.info.accept(String.format("Response has been saved and has length [%d]", response.asByteArray().length));
		endTime = System.currentTimeMillis();

		actualDuration = (endTime - startTime) / 1000;
		Logger.info.accept(String.format("Duration time is [%d] sec", actualDuration));
		Logger.info.accept(String.format("Status code is [%d]", response.statusCode()));
	}

	@Then("^I see that (.*) has (.*) response$")
	public void thenICompareResponses(Method method, ExpectedResponse expectedResponse) {
		Assert.assertEquals("Responses should be equal", expectedResponse.getExpectedResponse(), method.getSavedResponse().print());
	}

	@Then("^I see that random bytes array for (.*) has right length$")
	public void thenISeeThatRandomBytesArrayHasRightLength(Method method) {
		Assert.assertEquals("Actual length of random bytes array should match with expected",
				expectedByteLength, method.getSavedResponse().asByteArray().length);
	}

	@Then("^I check that response delay is correct$")
	public void thenICheckThatResponseDelayIsCorrect() {
		// delta contains code execution time;
		Assert.assertEquals("Actual response delay and expected delay should match with delta [1]",
				expectedDelay, actualDelay, 1);
	}

	@Then("^I check that response duration is correct$")
	public void thenICheckThatResponseDurationIsCorrect() {
		// delta contains code execution time;
		Assert.assertEquals("Actual response duration and expected duration should match with delta [1]",
				expectedDuration, actualDuration, 1);
	}

	@Then("^I see that (.*) executed with correct status$")
	public void thenICompareResults(Method method) {
		Assert.assertEquals("Status codes should match", expectedResponseCode, method.getSavedResponse().statusCode());
	}

	@Then("^I see correct numbers of links$")
	public void thenISeeCorrectNumberOfLinks() {
		List<Node> links = Method.LINKS.getSavedResponse().body().htmlPath().get().children().getNode("body").children().list();

		Assert.assertEquals("Number of links should be as [generated - 1]", links.size(), numberOfLinks - 1);

		Assert.assertEquals("Opened link doesn't have link", Integer.parseInt(links.get(offset).value()), offset + 1);

		if (offset > 0) {
			Assert.assertEquals("Opened link doesn't have link", Integer.parseInt(links.get(offset - 1).value()), offset - 1);
		}
	}

	@Then("^I see correct numbers of streams$")
	public void thenISeeCorrectNumbersOfStreams() {
		//приложение генерирует невалидный multiple json написал свой костыль для правки
		//дааа я знаю что костыль, но иначе вообще стремно было бы
		String response = "[" + Method.STREAM.getSavedResponse().body().print().replaceAll("\\}\\n\\{", "},{") + "]";

		int actualStreamNumber = JsonPath.from(response).getList("$4").size();
		Assert.assertEquals("Number of streams should match.", expectedStreamNumber, actualStreamNumber);
	}

	@Then("^I see content range$")
	public void thenISeeContentRange() {
		String actualContentRange = Method.RANGE.getSavedResponse().header("Content-range");
		String expectedContentRange = "bytes 0-" + (expectedByteLength - 1) + "/" + expectedByteLength;

		Assert.assertEquals("Content-range should be correct", expectedContentRange, actualContentRange);
	}

	@Then("^I see content stream-bytes$")
	public void thenISeeContentStramBytes() {
		String actualContentType = Method.STREAM_BYTES.getSavedResponse().header("content-type");
		String expectedContentType = "application/octet-stream";

		Assert.assertEquals("Content-type should be octet-stream", expectedContentType, actualContentType);
	}
}
