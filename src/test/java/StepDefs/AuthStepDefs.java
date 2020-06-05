package StepDefs;

import org.junit.Assert;

import Logging.Logger;
import Sources.Accounts.Accounts;
import Sources.Algorithms;
import Sources.Methods.AuthMethods;
import Sources.RequestTypes;
import Sources.Statuses.Statuses;
import Utils.StringUtils;
import Utils.UrlBuilder;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;

public class AuthStepDefs {
	private int savedStatusCode;

	@Given("^I generate correct credentials$")
	public void givenIGenerateCorrectCredentials() {
		Accounts.CORRECT.setUsername(StringUtils.randomString.get());
		Accounts.CORRECT.setPassword(StringUtils.randomString.get());
		Accounts.CORRECT.setBearerToken(StringUtils.randomString.get());
		Accounts.CORRECT.setQop(StringUtils.randomString.get());

		Logger.info.accept(String.format("User with username [%s] and password [%s] has been generated",
				Accounts.CORRECT.getUsername(), Accounts.CORRECT.getPassword()));
	}

	@Given("^I generate basic auth paths$")
	public void givenIGenerateBasicAuthPath() {
		AuthMethods.BASIC.setValidPath(String.format("/%s/%s",
				Accounts.CORRECT.getUsername(),
				Accounts.CORRECT.getPassword()));
		AuthMethods.BASIC.setInvalidPath("/");
	}

	@Given("^I generate digest-auth paths$")
	public void givenIGenerateDigestAuthAuthPath() {
		AuthMethods.DIGEST_AUTH.setValidPath(String.format("/%s/%s/%s", Accounts.CORRECT.getQop(), Accounts.CORRECT.getUsername(), Accounts.CORRECT.getPassword()));
		AuthMethods.DIGEST_AUTH.setInvalidPath("/");
	}

	@When("^I make basic auth (.*) request with (.*) credentials$")
	public void whenIMakeBasicAuthRequestWithCredentials(RequestTypes requestType, Accounts account) {
		String url = new UrlBuilder(AuthMethods.BASIC).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		savedStatusCode = RestAssured
				.given().auth().basic(account.getUsername(), account.getPassword())
				.when().get(url).statusCode();

		Logger.info.accept(String.format("Status code [%d] has been saved", savedStatusCode));
	}

	@When("^I make digest-auth (.*) request with (.*) credentials$")
	public void whenIMakeDigestAuthRequestWithCredentials(RequestTypes requestType, Accounts account) {
		String url = new UrlBuilder(AuthMethods.DIGEST_AUTH).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		savedStatusCode = RestAssured
				.given().auth().digest(account.getUsername(), account.getPassword())
				.when().get(url).statusCode();

		Logger.info.accept(String.format("Status code [%d] has been saved", savedStatusCode));
	}

	@When("^I make digest-auth (.*) request with (.*) credentials and (.*) algorithm$")
	public void whenIMakeDigestAuthRequestWithCredentialsAndAlgorithm(RequestTypes requestType, Accounts account, Algorithms algorithm) {
		String url = new UrlBuilder(AuthMethods.DIGEST_AUTH)
				.setPath(requestType)
				.setAlgorithm(algorithm.getAlgorithm())
				.build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		savedStatusCode = RestAssured
				.given().auth().digest(account.getUsername(), account.getPassword())
				.when().get(url).statusCode();

		Logger.info.accept(String.format("Status code [%d] has been saved", savedStatusCode));
	}

	@When("^I make bearer (.*) request with (.*) credentials$")
	public void whenIMakeBearerRequestWithCredentials(RequestTypes requestType, Accounts account) {
		String url = new UrlBuilder(AuthMethods.BEARER).setPath(requestType).build();

		Logger.info.accept(String.format("Navigate to URL [%s]", url));

		savedStatusCode = RestAssured
				.given().auth().oauth2(account.getBearerToken())
				.when().get(url).statusCode();

		Logger.info.accept(String.format("Status code [%d] has been saved", savedStatusCode));
	}

	@Then("^I see that request (.*)$")
	public void thenISeeResult(Statuses status) {
		Assert.assertEquals("Status codes should match", status.getStatuscode(), savedStatusCode);
	}

	@After
	public void tearDown() {
		savedStatusCode = 0;
		Logger.info.accept(String.format("Saved Status Code has been reset to [%d]", savedStatusCode));
	}
}
