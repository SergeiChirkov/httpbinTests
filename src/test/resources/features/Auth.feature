@Regression @Auth
Feature: Test auth method

@BaseAuthTest
Scenario Outline: Test /basic-auth/{user}/{passwd}
Given I generate correct credentials
And I generate basic auth paths
When I make basic auth <type> request with <credentialsType> credentials
Then I see that AUTH_BASIC status is <status>

Examples:
| credentialsType | type    | status    |
| CORRECT         | VALID   | SUCCESS   |
| INCORRECT       | VALID   | FAIL      |
| INVALID         | INVALID | NOT_FOUND |

@BearerAuthTest
Scenario Outline: Test /bearer
When I make bearer <type> request with <credentialsType> credentials
Then I see that AUTH_BEARER status is <status>

Examples:
| credentialsType | type    | status    |
| CORRECT         | VALID   | SUCCESS   |
| INCORRECT       | VALID   | FAIL      |
| INVALID         | INVALID | NOT_FOUND |

@DigestAuthSimpleTest
Scenario Outline: Test /digest-auth/{qop}/{user}/{passwd}
Given I generate correct credentials
And I generate digest-auth paths
When I make digest-auth <type> request with <credentialsType> credentials
Then I see that AUTH_DIGEST status is <status>

Examples:
| credentialsType | type    | status    |
| CORRECT         | VALID   | SUCCESS   |
| INCORRECT       | VALID   | FAIL      |
| INVALID         | INVALID | NOT_FOUND |

@DigestAuthTest
Scenario Outline: Test /digest-auth/{qop}/{user}/{passwd}/{algorithm}
Given I generate correct credentials
And I generate digest-auth paths
When I make digest-auth <type> request with <credentialsType> credentials and <algorithm> algorithm
Then I see that AUTH_DIGEST status is <status>

Examples:
| credentialsType | type    | algorithm | status    |
| CORRECT         | VALID   | MD5       | SUCCESS   |
| CORRECT         | VALID   | SHA256    | SUCCESS   |
| CORRECT         | VALID   | SHA512    | SUCCESS   |
| INCORRECT       | VALID   | MD5       | FAIL      |
| INCORRECT       | VALID   | SHA256    | FAIL      |
| INCORRECT       | VALID   | SHA512    | FAIL      |
| INVALID         | INVALID | MD5       | NOT_FOUND |
| INVALID         | INVALID | SHA256    | NOT_FOUND |
| INVALID         | INVALID | SHA512    | NOT_FOUND |

@DigestAuthHardTest
Scenario Outline: Test /digest-auth/{qop}/{user}/{passwd}/{algorithm}/{stale_after}
Given I generate correct credentials
And I generate digest-auth paths
When I make digest-auth <type> request with <credentialsType> credentials, <algorithm> algorithm and NEVER stale-after
Then I see that AUTH_DIGEST status is <status>

Examples:
| credentialsType | type    | algorithm | status    |
| CORRECT         | VALID   | MD5       | SUCCESS   |
| CORRECT         | VALID   | SHA256    | SUCCESS   |
| CORRECT         | VALID   | SHA512    | SUCCESS   |
| INCORRECT       | VALID   | MD5       | FAIL      |
| INCORRECT       | VALID   | SHA256    | FAIL      |
| INCORRECT       | VALID   | SHA512    | FAIL      |
| INVALID         | INVALID | MD5       | NOT_FOUND |
| INVALID         | INVALID | SHA256    | NOT_FOUND |
| INVALID         | INVALID | SHA512    | NOT_FOUND |

@HiddenBasicAuthTest
Scenario Outline: Test /hidden-basic-auth/{user}/{passwd}
Given I generate correct credentials
And I generate hidden-basic auth paths
When I make hidden basic auth <type> request with <credentialsType> credentials
Then I see that AUTH_HIDDEN_BASIC status is <status>

Examples:
| credentialsType | type    | status    |
| CORRECT         | VALID   | SUCCESS   |
| INCORRECT       | VALID   | NOT_FOUND |
| INVALID         | INVALID | NOT_FOUND |