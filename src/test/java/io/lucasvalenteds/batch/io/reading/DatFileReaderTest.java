package io.lucasvalenteds.batch.io.reading;

import io.lucasvalenteds.batch.testing.DatFileFixtures;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

class DatFileReaderTest {

    private static final Path testingClasspath = Paths.get("src", "test", "resources");
    private static Path testingPath;

    private Path pathOfFileToRead;

    @BeforeAll
    static void setupClasspath() {
        createDirectoryForTesting();
    }

    @AfterAll
    static void teardownClasspath() {
        deleteDirectoryForTesting();
    }

    @BeforeEach
    void setup() {
        createFileWithValidData();
    }

    @AfterEach
    void teardown() {
        deleteFileWithValidData();
    }

    @DisplayName("It can provide the content of a .dat file in a given path")
    @Test
    void testReadingSuccess() {
        var linesOfTheFile = new DatFileReader().readLines(pathOfFileToRead)
            .collect(Collectors.toList());

        assertThat(linesOfTheFile)
            .hasSize(DatFileFixtures.validInputFileContent.size())
            .containsAll(DatFileFixtures.validInputFileContent);
    }

    @DisplayName("Trying to read the content of an invalid file returns an empty Stream")
    @Test
    void testReadingFailureIO() {
        var invalidPath = testingClasspath.resolve("not-found");

        var linesRead = new DatFileReader().readLines(invalidPath);

        assertThat(linesRead.count()).isEqualTo(0);
    }

    private static void createDirectoryForTesting() {
        try {
            testingPath = Files.createTempDirectory(testingClasspath, "reader");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDirectoryForTesting() {
        try {
            Files.deleteIfExists(testingPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createFileWithValidData() {
        try {
            this.pathOfFileToRead = Files.createTempFile(testingClasspath, "read-input", "dat");
            Files.writeString(this.pathOfFileToRead, String.join("\n", DatFileFixtures.validInputFileContent));
        } catch (IOException exception) {
            fail("Creating files for testing should be possible");
        }
    }

    private void deleteFileWithValidData() {
        try {
            Files.deleteIfExists(pathOfFileToRead);
        } catch (IOException exception) {
            fail("Deleting files for testing should be possible");
        }
    }
}
