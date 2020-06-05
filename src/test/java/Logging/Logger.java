package Logging;

import java.util.function.Consumer;

import org.slf4j.LoggerFactory;

import BaseTest.BaseTest;
import io.cucumber.java.Scenario;

public class Logger extends BaseTest {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);
	private static Scenario scenario;

	public static void setScenario(Scenario scenario) {
		Logger.scenario = scenario;
	}

	public static Consumer<String> info = msg -> {
		logger.info(msg);
		scenario.write(msg);
		System.out.println(msg);
	};
}
