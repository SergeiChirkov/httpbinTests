package Sources.Methods;

public enum AuthMethods {
	BASIC("basic-auth", null, null),
	BEARER("bearer", "", "/"),
	DIGEST("digest-auth", null, null),
	HIDDEN_BASIC("hidden-basic-auth", null, null);

	private String basePath, validPath, invalidPath;

	AuthMethods(String basePath, String validPath, String invalidPath) {
		this.basePath = basePath;
		this.validPath = validPath;
		this.invalidPath = invalidPath;
	}

	public void setValidPath(String validPath) {
		this.validPath = validPath;
	}

	public void setInvalidPath(String invalidPath) {
		this.invalidPath = invalidPath;
	}

	public String getValidPath() {
		return basePath + validPath;
	}

	public String getInvalidPath() {
		return basePath + invalidPath;
	}
}
