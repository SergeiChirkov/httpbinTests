package Sources;

public enum Algorithms {
	MD5("MD5"),
	SHA256("SHA-256"),
	SHA512("SHA-512");

	String algorithm;

	Algorithms(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getAlgorithm() {
		return algorithm;
	}
}
