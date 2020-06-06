package Sources;

import java.util.function.Function;

public enum RequestType {
	VALID(Method::getValidPath),
	INVALID(Method::getInvalidPath);

	private Function<Method, String> getPathMethod;

	RequestType(Function<Method, String> getPathMethod) {
		this.getPathMethod = getPathMethod;
	}

	public Function<Method, String> getGetPathMethod() {
		return getPathMethod;
	}
}
