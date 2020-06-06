@Regression @DynamicData
Feature: Test dynamic data

@Base64Test
Scenario Outline: Test /base64/{value}
When I make <type> base64 request
Then I see <status> response

Examples:
| type    | status     |
| VALID   | SUCCESSFUL |
| INVALID | FAILED     |

