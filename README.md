# authorizer-java

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
