@Regression @Auth
Feature: Test auth method

@BaseAuthTest
Scenario Outline: Test /basic-auth/{user}/{passwd}
Given I generate correct credentials
And I generate basic auth paths
When I make basic auth <type> request with <credentialsType> credentials
Then I see that AUTH_BASIC status is <status>

Examples:
| credentialsType | type    | status  |
| CORRECT         | VALID   | SUCCESS |
| INCORRECT       | VALID   | FAIL    |
| INVALID         | INVALID | ERROR   |

@BearerAuthTest
Scenario Outline: Test /bearer
When I make bearer <type> request with <credentialsType> credentials
Then I see that AUTH_BEARER status is <status>

Examples:
| credentialsType | type    | status  |
| CORRECT         | VALID   | SUCCESS |
| INCORRECT       | VALID   | FAIL    |
| INVALID         | INVALID | ERROR   |

@DigestAuthSimpleTest
Scenario Outline: Test /digest-auth/{qop}/{user}/{passwd}
Given I generate correct credentials
And I generate digest-auth paths
When I make digest-auth <type> request with <credentialsType> credentials
Then I see that AUTH_DIGEST status is <status>

Examples:
| credentialsType | type    | status  |
| CORRECT         | VALID   | SUCCESS |
| INCORRECT       | VALID   | FAIL    |
| INVALID         | INVALID | ERROR   |

@DigestAuthTest
Scenario Outline: Test /digest-auth/{qop}/{user}/{passwd}/{algorithm}
Given I generate correct credentials
And I generate digest-auth paths
When I make digest-auth <type> request with <credentialsType> credentials and <algorithm> algorithm
Then I see that AUTH_DIGEST status is <status>

Examples:
| credentialsType | type    | algorithm | status  |
| CORRECT         | VALID   | MD5       | SUCCESS |
| CORRECT         | VALID   | SHA256    | SUCCESS |
| CORRECT         | VALID   | SHA512    | SUCCESS |
| INCORRECT       | VALID   | MD5       | FAIL    |
| INCORRECT       | VALID   | SHA256    | FAIL    |
| INCORRECT       | VALID   | SHA512    | FAIL    |
| INVALID         | INVALID | MD5       | ERROR   |
| INVALID         | INVALID | SHA256    | ERROR   |
| INVALID         | INVALID | SHA512    | ERROR   |

@DigestAuthHardTest
Scenario Outline: Test /digest-auth/{qop}/{user}/{passwd}/{algorithm}/{stale_after}
Given I generate correct credentials
And I generate digest-auth paths
When I make digest-auth <type> request with <credentialsType> credentials, <algorithm> algorithm and NEVER stale-after
Then I see that AUTH_DIGEST status is <status>

Examples:
| credentialsType | type    | algorithm | status  |
| CORRECT         | VALID   | MD5       | SUCCESS |
| CORRECT         | VALID   | SHA256    | SUCCESS |
| CORRECT         | VALID   | SHA512    | SUCCESS |
| INCORRECT       | VALID   | MD5       | FAIL    |
| INCORRECT       | VALID   | SHA256    | FAIL    |
| INCORRECT       | VALID   | SHA512    | FAIL    |
| INVALID         | INVALID | MD5       | ERROR   |
| INVALID         | INVALID | SHA256    | ERROR   |
| INVALID         | INVALID | SHA512    | ERROR   |

@HiddenBasicAuthTest
Scenario Outline: Test /hidden-basic-auth/{user}/{passwd}
Given I generate correct credentials
And I generate hidden-basic auth paths
When I make hidden basic auth <type> request with <credentialsType> credentials
Then I see that AUTH_HIDDEN_BASIC status is <status>

Examples:
| credentialsType | type    | status  |
| CORRECT         | VALID   | SUCCESS |
| INCORRECT       | VALID   | ERROR   |
| INVALID         | INVALID | ERROR   |