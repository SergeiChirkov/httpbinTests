@Regression @DynamicData
Feature: Test dynamic data

@Base64Test
Scenario Outline: Test /base64/{value}
When I execute <type> GET BASE64 request
Then I see that BASE64 has <expected> response

Examples:
| type    | expected              |
| VALID   | HTTPBIN_IS_AWESOM     |
| INVALID | INCORRECT_BASE64_DATA |

@ByteTest
Scenario: Test /bytes/{n} (positive)
Given I generate bytes length for BYTES method
When I execute VALID GET BYTES request
Then I see that random bytes array for BYTES has right length

@ByteTest
Scenario: Test /bytes/{n} (negative)
Given I generate bytes length for BYTES method
When I execute INVALID GET BYTES request
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

@LinksTest
Scenario: Test /links/{n}/{offset} (positive)
Given I generate links
When I execute VALID GET LINKS request
Then I see correct numbers of links

@LinksTest
Scenario: Test /links/{n}/{offset} (negative)
Given I generate links
When I execute INVALID GET LINKS request
Then I see that LINKS status is NOT_FOUND

@RangeTest
Scenario: Test /range/{numbytes} (positive)
Given I generate bytes length for RANGE method
When I execute VALID GET RANGE request
Then I see that random bytes array for RANGE has right length
And I see content range

@RangeTest
Scenario: Test /range/{numbytes} (negative)
Given I generate bytes length for RANGE method
When I execute INVALID GET RANGE request
Then I see that RANGE status is NOT_FOUND

@StreamBytesTest
Scenario: Test /stream-bytes/{n} (positive)
Given I generate bytes length for STREAM_BYTES method
When I execute VALID GET STREAM_BYTES request
Then I see that random bytes array for STREAM_BYTES has right length
And I see content stream-bytes

@StreamBytesTest
Scenario: Test /stream-bytes/{n} (negative)
Given I generate bytes length for STREAM_BYTES method
When I execute INVALID GET STREAM_BYTES request
Then I see that STREAM_BYTES status is NOT_FOUND

@StreamTest
Scenario: Test /stream/{n} (positive)
Given I generate stream number for STREAM method
When I execute VALID GET STREAM request
Then I see correct numbers of streams

@StreamTest
Scenario: Test /stream/{n} (negative)
Given I generate stream number for STREAM method
When I execute INVALID GET STREAM request
Then I see that STREAM status is NOT_FOUND