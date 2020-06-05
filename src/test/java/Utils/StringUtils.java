package Utils;

import java.util.Random;
import java.util.function.Supplier;

import org.apache.commons.lang.RandomStringUtils;

public class StringUtils {
	public static Supplier<String> randomString = () ->
			RandomStringUtils.random(5 + new Random().nextInt(15),true,true).toLowerCase();
}
