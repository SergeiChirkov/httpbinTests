package Sources.Accounts;

import Utils.StringUtils;

public enum Accounts {
	CORRECT(null, null, null, null),
	INCORRECT(null, null, "", ""),
	INVALID("", "", "", "");

	private String username, password, bearerToken, qop;

	Accounts(String username, String password, String bearerToken, String qop) {
		this.username = username;
		this.password = password;
		this.bearerToken = bearerToken;
		this.qop = qop;
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

	public void setQop(String qop) {
		this.qop = qop;
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

	public String getQop() {
		return qop;
	}
}
