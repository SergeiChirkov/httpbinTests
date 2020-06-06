package Sources;

public enum ExpectedResponse {
	HTTPBIN_IS_AWESOM("HTTPBIN is awesome"),
	INCORRECT_BASE64_DATA("Incorrect Base64 data try: SFRUUEJJTiBpcyBhd2Vzb21l"),
	;

	String expectedResponse;

	ExpectedResponse(String expectedResponse) {
		this.expectedResponse = expectedResponse;
	}

	public String getExpectedResponse() {
		return expectedResponse;
	}

	public void setExpectedResponse(String expectedResponse) {
		this.expectedResponse = expectedResponse;
	}
}
