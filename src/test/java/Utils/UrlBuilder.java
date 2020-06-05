package Utils;

import Sources.Methods.AuthMethods;
import Sources.RequestTypes;

public class UrlBuilder {
	private String scheme;
	private String host;
	private String path;
	private AuthMethods authMethod;

	public UrlBuilder() {
		scheme = "http://";
		host = "httpbin.org/";
	}

	public UrlBuilder setPath(String path) {
		this.path = path;
		return this;
	}

	public UrlBuilder(AuthMethods authMethod) {
		this();
		this.authMethod = authMethod;
	}

	public UrlBuilder setPath(RequestTypes requestType) {
		this.path = requestType.getGetPathMethod().apply(authMethod);
		return this;
	}

	public String build() {
		return scheme + host + path;
	}
}
