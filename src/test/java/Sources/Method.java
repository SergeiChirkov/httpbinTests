package Sources;

import Utils.StringUtils;

public enum Method {
	AUTH_BASIC("basic-auth", null, null),
	AUTH_BEARER("bearer", "", "/"),
	AUTH_DIGEST("digest-auth", null, null),
	AUTH_HIDDEN_BASIC("hidden-basic-auth", null, null),
	BASE64("base64", "/SFRUUEJJTiBpcyBhd2Vzb21l", "/" + StringUtils.randomString.get()),
	;

	private String basePath, validPath, invalidPath;

	Method(String basePath, String validPath, String invalidPath) {
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
