# File Processor

## Useful commands

| Description | Command |
| :--- | :--- |
| Generate the app jar | `docker run --rm -v "$PWD":/app -w /app gradle:4.6.0-jdk9 gradle jar` |
| Run the app jar | `docker run --rm -v "$PWD":/app -w /app openjdk:9.0-slim java -jar build/libs/lucasvalente-0.1.0.jar` |

## Test report

<img src=".art/test-results.png" width="450px" alt="IntelliJ test results with all tests passing except for 3 tests disabled">

## Test coverage

<img src=".art/test-coverage.png" width="450px" alt="IntelliJ test coverage report indicating 9$% of coverage in classes and 76% in lines">
