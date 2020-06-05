package Utils;

import Sources.Methods.AuthMethods;
import Sources.RequestTypes;

public class UrlBuilder {
	private String scheme;
	private String host;
	private String path;
	private AuthMethods authMethod;
	private String algorithm;

	public UrlBuilder() {
		scheme = "http://";
		host = "httpbin.org/";
		algorithm = "";
	}

	public UrlBuilder(AuthMethods authMethod) {
		this();
		this.authMethod = authMethod;
	}

	public UrlBuilder setPath(RequestTypes requestType) {
		this.path = requestType.getGetPathMethod().apply(authMethod);
		return this;
	}

	public UrlBuilder setAlgorithm(String algorithm) {
		this.algorithm = "/" + algorithm;
		return this;
	}

	public String build() {
		return scheme + host + path + algorithm;
	}
}
