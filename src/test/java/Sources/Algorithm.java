package Sources;

public enum Algorithm {
	MD5("MD5"),
	SHA256("SHA-256"),
	SHA512("SHA-512");

	String algorithm;

	Algorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public String getAlgorithm() {
		return algorithm;
	}
}
