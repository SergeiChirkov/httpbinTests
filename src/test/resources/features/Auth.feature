@Regression @Auth
Feature: Test auth method

@BaseAuthTest
Scenario Outline: Base auth test
Given I generate correct credentials
And I generate basic auth paths
When I make basic auth <type> request with <credentialsType> credentials
Then I see that request <status>

Examples:
| credentialsType | type    | status  |
| CORRECT         | VALID   | SUCCESS |
| INCORRECT       | VALID   | FAIL    |
| INVALID         | INVALID | ERROR   |

@BearerAuthTest
Scenario Outline: Bearer auth test
When I make bearer <type> request with <credentialsType> credentials
Then I see that request <status>

Examples:
| credentialsType | type    | status  |
| CORRECT         | VALID   | SUCCESS |
| INCORRECT       | VALID   | FAIL    |
| INVALID         | INVALID | ERROR   |

