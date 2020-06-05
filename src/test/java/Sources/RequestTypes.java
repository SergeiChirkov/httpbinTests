package Sources;

import java.util.function.Function;

import Sources.Methods.AuthMethods;

public enum RequestTypes {
	VALID(AuthMethods::getValidPath),
	INVALID(AuthMethods::getInvalidPath);

	private Function<AuthMethods, String> getPathMethod;

	RequestTypes(Function<AuthMethods, String> getPathMethod) {
		this.getPathMethod = getPathMethod;
	}

	public Function<AuthMethods, String> getGetPathMethod() {
		return getPathMethod;
	}
}
