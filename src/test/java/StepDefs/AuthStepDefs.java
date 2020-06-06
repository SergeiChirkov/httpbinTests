package StepDefs;

import java.util.function.Supplier;

import Logging.Logger;
import Sources.Account;
import Sources.Algorithm;
import Sources.Method;
import Sources.RequestType;
import Sources.StaleAfter;
import Utils.StringUtils;
import Utils.UrlBuilder;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthStepDefs {
	@Given("^I generate correct credentials$")
	public void givenIGenerateCorrectCredentials() {
		Account.CORRECT.setUsername(StringUtils.randomString.get());
		Account.CORRECT.setPassword(StringUtils.randomString.get());
		Account.CORRECT.setBearerToken(StringUtils.randomString.get());
		Account.CORRECT.setQop(StringUtils.randomString.get());

		Logger.info.accept(String.format("User with username [%s] and password [%s] has been generated",
				Account.CORRECT.getUsername(), Account.CORRECT.getPassword()));
	}

	private Supplier<String> basicValidPath = () ->
			String.format("/%s/%s", Account.CORRECT.getUsername(), Account.CORRECT.getPassword());

	private Supplier<String> basicInvalidPath = () -> "/";

	@Given("^I generate basic auth paths$")
	public void givenIGenerateBasicAuthPath() {
		Method.AUTH_BASIC.setValidPath(basicValidPath.get());
		Method.AUTH_BASIC.setInvalidPath(basicInvalidPath.get());
	}

	@Given("^I generate hidden-basic auth paths$")
	public void givenIGenerateHiddenBasicAuthPath() {
		Method.AUTH_HIDDEN_BASIC.setValidPath(basicValidPath.get());
		Method.AUTH_HIDDEN_BASIC.setInvalidPath(basicInvalidPath.get());
	}

	@Given("^I generate digest-auth paths$")
	public void givenIGenerateDigestAuthAuthPath() {
		Method.AUTH_DIGEST.setValidPath(String.format("/%s/%s/%s", Account.CORRECT.getQop(), Account.CORRECT.getUsername(), Account.CORRECT.getPassword()));
		Method.AUTH_DIGEST.setInvalidPath("/");
	}

	@When("^I make basic auth (.*) request with (.*) credentials$")
	public void whenIMakeBasicAuthRequestWithCredentials(RequestType requestType, Account account) {
		String url = new UrlBuilder(Method.AUTH_BASIC).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		Response response = RestAssured
				.given().auth().basic(account.getUsername(), account.getPassword())
				.when().get(url);

		Method.AUTH_BASIC.saveResponse(response);

		Logger.info.accept(String.format("Status code [%d] has been saved", response.statusCode()));
	}

	@When("^I make digest-auth (.*) request with (.*) credentials$")
	public void whenIMakeDigestAuthRequestWithCredentials(RequestType requestType, Account account) {
		String url = new UrlBuilder(Method.AUTH_DIGEST).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		Response response = RestAssured
				.given().auth().digest(account.getUsername(), account.getPassword())
				.when().get(url);

		Method.AUTH_DIGEST.saveResponse(response);

		Logger.info.accept(String.format("Status code [%d] has been saved", response.statusCode()));
	}

	@When("^I make digest-auth (.*) request with (.*) credentials and (.*) algorithm$")
	public void whenIMakeDigestAuthRequestWithCredentialsAndAlgorithm(RequestType requestType, Account account, Algorithm algorithm) {
		String url = new UrlBuilder(Method.AUTH_DIGEST)
				.setPath(requestType)
				.setAlgorithm(algorithm.getAlgorithm())
				.build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		Response response = RestAssured
				.given().auth().digest(account.getUsername(), account.getPassword())
				.when().get(url);

		Method.AUTH_DIGEST.saveResponse(response);

		Logger.info.accept(String.format("Status code [%d] has been saved", response.statusCode()));
	}

	@When("^I make digest-auth (.*) request with (.*) credentials, (.*) algorithm and (.*) stale-after$")
	public void whenIMakeDigestAuthRequestWithCredentialsAlgorithmAndStaleAfter(RequestType requestType, Account account, Algorithm algorithm, StaleAfter staleAfter) {
		String url = new UrlBuilder(Method.AUTH_DIGEST)
				.setPath(requestType)
				.setAlgorithm(algorithm.getAlgorithm())
				.setStaleAfter(staleAfter.name().toLowerCase())
				.build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		Response response = RestAssured
				.given().auth().digest(account.getUsername(), account.getPassword())
				.when().get(url);

		Method.AUTH_DIGEST.saveResponse(response);

		Logger.info.accept(String.format("Status code [%d] has been saved", response.statusCode()));
	}

	@When("^I make hidden basic auth (.*) request with (.*) credentials$")
	public void whenIMakeHiddenBasicAuthRequestWithCredentials(RequestType requestType, Account account) {
		String url = new UrlBuilder(Method.AUTH_HIDDEN_BASIC).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		Response response = RestAssured
				.given().auth().preemptive().basic(account.getUsername(), account.getPassword())
				.when().get(url);

		Method.AUTH_HIDDEN_BASIC.saveResponse(response);

		Logger.info.accept(String.format("Status code [%d] has been saved", response.statusCode()));
	}

	@When("^I make bearer (.*) request with (.*) credentials$")
	public void whenIMakeBearerRequestWithCredentials(RequestType requestType, Account account) {
		String url = new UrlBuilder(Method.AUTH_BEARER).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		Response response = RestAssured
				.given().auth().oauth2(account.getBearerToken())
				.when().get(url);

		Method.AUTH_BEARER.saveResponse(response);

		Logger.info.accept(String.format("Status code [%d] has been saved", response.statusCode()));
	}
}
