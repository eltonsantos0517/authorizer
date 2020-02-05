# authorizer-java

# About Project
## Architecture
Choose Clean architecture pattern because it provides easy maintainability and minor change impact risk

## Chains of Responsibility
Choose this pattern in business rule validation because is very easy to apply new rules and change their order in the chain

## Strategy of refactoring
How integration tests had already been implemented i choose to refactoring by parts, 
i started with account flow refactoring, after the end, run the integration tests and they passed with success. 
Right after it's time to refactor transaction flow, after ending run the integration tests and they passed with success.

## Running
You can run and test everything with the gradlew script:

```bash
$ ./gradlew run # to run the program
$ ./gradlew --console plain --quiet run # to run the program without annoying messages
$ ./gradlew test # to run the tests
```

Below is one way you can test the program in the command line:
```bash
$ ./gradlew --console plain --quiet run < resources/example.txt
{"account":null,"violations":["account-not-initialized"]}
{"account":{"active-card":true,"available-limit":100},"violations":[]}
{"account":{"active-card":true,"available-limit":100},"violations":["account-already-initialized"]}
{"account":{"active-card":true,"available-limit":80},"violations":[]}
{"account":{"active-card":true,"available-limit":80},"violations":["insufficient-limit"]}
{"account":{"active-card":true,"available-limit":70},"violations":[]}
{"account":{"active-card":true,"available-limit":60},"violations":[]}
{"account":{"active-card":true,"available-limit":50},"violations":[]}
{"account":{"active-card":true,"available-limit":50},"violations":["high-frequency-small-interval","doubled-transaction"]}
{"account":{"active-card":true,"available-limit":50},"violations":["high-frequency-small-interval"]}
```

## Available dependencies

Dependencies available on this project are:
- `com.google.code.gson:gson:2.8.6`, used for parsing JSON
- `junit:junit:4.12`, for testing

