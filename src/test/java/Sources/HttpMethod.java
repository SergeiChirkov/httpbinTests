package Sources;

import java.util.function.Function;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public enum HttpMethod {
	DELETE(url -> RestAssured.delete(url)),
	GET(url -> RestAssured.get(url)),
	PATCH(url -> RestAssured.patch(url)),
	POST(url -> RestAssured.post(url)),
	PUT(url -> RestAssured.put(url));

	Function<String, Response> httpMethodFactory;

	HttpMethod(Function<String, Response> httpMethodFactory) {
		this.httpMethodFactory = httpMethodFactory;
	}

	public Function<String, Response> factory() {
		return httpMethodFactory;
	}
}
