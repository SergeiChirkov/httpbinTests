package Sources;

import Logging.Logger;
import Utils.StringUtils;
import io.restassured.response.Response;

public enum Method {
	AUTH_BASIC("basic-auth", null, null, null),
	AUTH_BEARER("bearer", "", "/", null),
	AUTH_DIGEST("digest-auth", null, null, null),
	AUTH_HIDDEN_BASIC("hidden-basic-auth", null, null, null),
	BASE64("base64", "/SFRUUEJJTiBpcyBhd2Vzb21l", "/" + StringUtils.randomString.get(), null),
	BYTES("bytes", null, null, null),
	DELAY("delay", null ,null, null),
	DRIP("drip", null, null, null),
	LINKS("links", null, null, null),
	RANGE("range", null, null, null),
	;

	private String basePath, validPath, invalidPath;
	Response savedResponse;

	Method(String basePath, String validPath, String invalidPath, Response savedResponse) {
		this.basePath = basePath;
		this.validPath = validPath;
		this.invalidPath = invalidPath;
		this.savedResponse = savedResponse;
	}

	public void setValidPath(String validPath) {
		this.validPath = validPath;
	}

	public void setInvalidPath(String invalidPath) {
		this.invalidPath = invalidPath;
	}

	public void saveResponse(Response savedResponse) {
		this.savedResponse = savedResponse;
	}

	public void removeResponse() {
		this.savedResponse = null;
		Logger.info.accept("Saved response has been removed");
	}

	public String getValidPath() {
		return basePath + validPath;
	}

	public String getInvalidPath() {
		return basePath + invalidPath;
	}

	public Response getSavedResponse() {
		return savedResponse;
	}
}
