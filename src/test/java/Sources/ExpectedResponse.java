package Sources;

public enum ExpectedResponse {
	SUCCESSFUL("HTTPBIN is awesome"),
	FAILED("Incorrect Base64 data try: SFRUUEJJTiBpcyBhd2Vzb21l")
	;

	String responseText;

	ExpectedResponse(String responseText) {
		this.responseText = responseText;
	}

	public String getResponseText() {
		return responseText;
	}
}
