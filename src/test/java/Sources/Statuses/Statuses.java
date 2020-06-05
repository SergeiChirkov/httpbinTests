package Sources.Statuses;

public enum Statuses {
	SUCCESS (200),
	FAIL (401),
	ERROR (404);

	int statuscode;

	Statuses(int statuscode) {
		this.statuscode = statuscode;
	}

	public int getStatuscode() {
		return statuscode;
	}
}
