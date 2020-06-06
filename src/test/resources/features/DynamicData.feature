@Regression @DynamicData
Feature: Test dynamic data

@Base64Test
Scenario Outline: Test /base64/{value}
When I make <type> BASE64 request
Then I see that BASE64 has <expected> response

Examples:
| type    | expected              |
| VALID   | HTTPBIN_IS_AWESOM     |
| INVALID | INCORRECT_BASE64_DATA |

@ByteTest
Scenario: Test /bytes/{n} (positive)
Given I generate bytes length
When I make VALID BYTES request
Then I see that random bytes array for BYTES has right length

@ByteTest
Scenario: Test /bytes/{n} (negative)
Given I generate bytes length
When I make INVALID BYTES request
Then I see that BYTES status is NOT_FOUND

@DelayTest
Scenario Outline: Test /delay/{delay}
Given I generate delay in seconds
When I execute VALID <httpMethod> DELAY request
Then I check that response delay is correct

Examples:
| httpMethod |
| DELETE     |
| GET        |
| PATCH      |
| POST       |
| PUT        |

@DelayTest
Scenario Outline: Test /delay/{delay}
Given I generate invalid path and set maximum expected delay
When I execute INVALID <httpMethod> DELAY request
Then I check that response delay is correct

Examples:
| httpMethod |
| DELETE     |
| GET        |
| PATCH      |
| POST       |
| PUT        |

@DripTest
Scenario: Test /drip (positive)
Given I generate VALID data for drip request
When I execute VALID GET DRIP request
Then I check that response delay is correct
And I see that DRIP executed with correct status
And I see that random bytes array for DRIP has right length
And I check that response duration is correct

@DripTest
Scenario: Test /drip (negative)
Given I generate INVALID data for drip request
When I execute VALID GET DRIP request
Then I see that DRIP status is ERROR