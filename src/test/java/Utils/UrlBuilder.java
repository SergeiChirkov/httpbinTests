package Utils;

import Sources.Method;
import Sources.RequestType;

public class UrlBuilder {
	private String scheme;
	private String host;
	private String path;
	private Method method;
	private String algorithm;
	private String staleAfter;

	public UrlBuilder() {
		scheme = "http://";
		host = "httpbin.org/";
		algorithm = "";
		staleAfter = "";
	}

	public UrlBuilder(Method method) {
		this();
		this.method = method;
	}

	public UrlBuilder setPath(RequestType requestType) {
		this.path = requestType.getGetPathMethod().apply(method);
		return this;
	}

	public UrlBuilder setAlgorithm(String algorithm) {
		this.algorithm = "/" + algorithm;
		return this;
	}

	public UrlBuilder setStaleAfter(String staleAfter) {
		this.staleAfter = staleAfter;
		return this;
	}

	public String build() {
		return scheme + host + path + algorithm + staleAfter;
	}
}
