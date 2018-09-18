# File Processor

## How to access the code

| Tag | Branch | Description |
| :--- | :--- | :--- |
| `0.1.0` | `main` | Implementation sent in March |
| `0.2.0` | `main` | Implementation sent in September |
| `0.2.0` | `spring` | Prototype using Spring Batch |

## How to run

![](https://img.shields.io/badge/openjdk-11-green.svg?style=for-the-badge)

| Description | Command |
| :--- | :--- |
| Generate JAR file | `./gradlew clean jar` |
| Run tests and coverage | `./gradlew clean test` |
| Update dependencies | `./gradlew useLatestVersion` |

## Retrospective

**Good**

* Fun
* Better WatchService implementation
* Better configuration API
* Better SOC
* Less boilerplate code
* Make it resilient
* JDK 11 just worked

**Bad**

* More LOCs (1065 vs 1153)
* Unfinished Spring Batch prototype
* Unfinished Project Reactor prototype
* Mixing OOP and FP
* Suppressed exception
* Pitest intermittent

## Test report

```
> Task :test

io.lucasvalenteds.batch.report.ReportFromFileTest

  Test Contains the most expensive sale PASSED
  Test Contains the amount of salesmen PASSED
  Test Contains the salesman less productive which is who sold less items PASSED
  Test Contains the amount of customers PASSED

io.lucasvalenteds.batch.report.ReportFromFileMarkdownTest

  Test It puts a star before all items in the report PASSED

io.lucasvalenteds.batch.process.mapping.CustomerMapperTest

  Test It can convert a valid String to an Object PASSED

io.lucasvalenteds.batch.process.mapping.SalesMapperTest

  Test It can convert a valid String to an Object PASSED

io.lucasvalenteds.batch.process.mapping.SalesmanMapperTest

  Test It can convert a valid String to an Object PASSED

io.lucasvalenteds.batch.process.mapping.SalesDataItemMapperTest

  Test It can convert a valid String to an Object PASSED

io.lucasvalenteds.batch.process.parsing.LineParserTest

  Test The default token is cedilla PASSED
  Test It accepts a token to split the data PASSED

io.lucasvalenteds.batch.process.parsing.SalesmanParserTest

  Test The default token is cedilla PASSED
  Test It accepts a token to split the data PASSED
  Test It can find a salesman data in a line PASSED
  Test The customer identifier is 001 PASSED

io.lucasvalenteds.batch.process.parsing.SalesParserTest

  Test The default token is cedilla PASSED
  Test It accepts a token to split the data PASSED
  Test It can find a costumer data in a line PASSED
  Test The customer identifier is 003 PASSED

io.lucasvalenteds.batch.process.parsing.CustomerParserTest

  Test The default token is cedilla PASSED
  Test It accepts a token to split the data PASSED
  Test It can find a costumer data in a line PASSED
  Test The customer identifier is 002 PASSED

io.lucasvalenteds.batch.io.config.FileExtensionsTest

  Test DONE_DAT always appends extensions PASSED
  Test DAT does not duplicate the extension PASSED
  Test DAT appends extension PASSED

io.lucasvalenteds.batch.io.config.FileManagerTest

  Test The output path can be retrieved PASSED
  Test The input path can be retrieved PASSED
  Test The base path can be retrieved PASSED
  Test Classpath is the default path in case base and alternative paths are null PASSED

io.lucasvalenteds.batch.io.reading.DatFileReaderTest

  Test It can provide the content of a .dat file in a given path PASSED
  Test Trying to read the content of an invalid file returns an empty Stream PASSED

io.lucasvalenteds.batch.io.reading.DatFileListenerTest

  Test It can detect newly created .dat files in a given path SKIPPED
  Test Deleting a folder been watched returns an empty Stream PASSED
  Test Trying to watch a invalid folder returns an empty Stream PASSED   

io.lucasvalenteds.batch.io.writing.DatFilePrinterTest

  Test Trying to write an invalid file throws an exception SKIPPED
  Test It can print a report in a file PASSED

io.lucasvalenteds.batch.data.SalesDataTest

  Test It has ID, a code and the name of the responsible salesman PASSED
  Test It also has a list of items sold PASSED

io.lucasvalenteds.batch.data.SalesmanDataTest

  Test It has ID, CPF name and Salary PASSED

io.lucasvalenteds.batch.data.CustomerDataTest

  Test It has ID, CNPJ, Name and business area PASSED

io.lucasvalenteds.batch.data.SalesDataItemTest

  Test It has ID, quantity and price PASSED

SUCCESS: Executed 42 tests in 6.6s (2 skipped)

Coverage summary:
batch:  70.2%
```
