package Runner;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/resources/features/"},
		plugin = {"json:target/cucumber/cucumber-json-report.json", "BaseTest.BaseTest"},
		glue = {"StepDefs", "BaseTest"},
		strict = true,
		tags = "@Base64Test"
)
public class Runner {
}
