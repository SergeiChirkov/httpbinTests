package Sources;

public enum Statuse {
	SUCCESS (200),
	FAIL (401),
	ERROR (404);

	int statuscode;

	Statuse(int statuscode) {
		this.statuscode = statuscode;
	}

	public int getStatuscode() {
		return statuscode;
	}
}
