package Sources.Accounts;

import Utils.StringUtils;

public enum Accounts {
	CORRECT(null, null, null),
	INCORRECT(null, null, ""),
	INVALID("", "", "");

	private String username, password, bearerToken;

	Accounts(String username, String password, String bearerToken) {
		this.username = username;
		this.password = password;
		this.bearerToken = bearerToken;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setBearerToken(String bearerToken) {
		this.bearerToken = bearerToken;
	}

	public String getUsername() {
		return username == null ? StringUtils.randomString.get() : username;
	}

	public String getPassword() {
		return password == null ? StringUtils.randomString.get() : password;
	}

	public String getBearerToken() {
		return bearerToken;
	}
}
