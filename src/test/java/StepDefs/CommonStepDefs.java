package StepDefs;

import org.junit.Assert;

import Sources.Method;
import Sources.Status;
import io.cucumber.java.en.Then;

public class CommonStepDefs {
	@Then("^I see that (.*) status is (.*)$")
	public void thenISeeResult(Method method, Status status) {
		Assert.assertEquals("Status codes should match", status.getStatuscode(), method.getSavedResponse().statusCode());
	}
}
