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
Then I see that random bytes array has right length

@ByteTest
Scenario: Test /bytes/{n} (negative)
Given I generate bytes length
When I make INVALID BYTES request
Then I see that BYTES status is ERROR

@DelayTest
Scenario Outline: Test /delay/{delay}
Given I generate delay in seconds
When I execute <type> <httpMethod> http method
Then I check that response delay is correct

Examples:
| type    | httpMethod |
| VALID   | DELETE     |
| VALID   | GET        |
| VALID   | PATCH      |
| VALID   | POST       |
| VALID   | PUT        |