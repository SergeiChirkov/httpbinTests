package Sources;

public enum Status {
	SUCCESS(200),
	FAIL(401),
	NOT_FOUND(404),
	ERROR(400);

	int statuscode;

	Status(int statuscode) {
		this.statuscode = statuscode;
	}

	public int getStatuscode() {
		return statuscode;
	}
	}
