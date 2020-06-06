@Regression @DynamicData
Feature: Test dynamic data

@Base64Test
Scenario Outline: Test /base64/{value}
When I make <type> base64 request
Then I see <expected> response

Examples:
| type    | expected              |
| VALID   | HTTPBIN_IS_AWESOM     |
| INVALID | INCORRECT_BASE64_DATA |

@ByteTest
Scenario: Test /bytes/{n}
Given I generate bytes length
When I make VALID bytes request
Then I see BYTES_LENGTH response