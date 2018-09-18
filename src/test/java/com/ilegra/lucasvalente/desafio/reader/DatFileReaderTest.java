package com.ilegra.lucasvalente.desafio.reader;

import com.ilegra.lucasvalente.desafio.testing.DatFileFixtures;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;

class DatFileReaderTest {

    private static final Path testingClasspath = Paths.get("src", "test", "resources");
    private static Path testingPath;

    private Path pathOfFileToRead;
    private Path pathOfFolderToBeDeleted;

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
        createFolderToBeDeleted();
    }

    @AfterEach
    void teardown() {
        deleteFileWithValidData();
        deleteFolderToCauseInterruption();
    }

    @DisplayName("It can detect newly created .dat files in a given path")
    @Disabled("Concurrency is hard to test")
    @Test
    void testListenerSuccess() throws IOException, InterruptedException {
        Path newFileCreatedForTheTest = Files.createTempFile(testingClasspath, "new-input-file", ".dat");
        ArrayList<File> filesDetectedByTheListener = new ArrayList<>();

        new DatFileReader(testingClasspath.toAbsolutePath())
            .listenForNewDatFiles(filesDetectedByTheListener::add);
        Thread.sleep(1000); // Bad Smell

        assertThat(filesDetectedByTheListener).hasSize(1);
        Files.deleteIfExists(newFileCreatedForTheTest);
    }

    @DisplayName("Trying to watch a invalid folder returns an empty Stream")
    @Test
    void testListenerFailureIO() {
        var invalidPath = testingClasspath.resolve("not-found");

        var stream = new DatFileReader(invalidPath)
            .listenForNewDatFiles(newFile -> {
            });

        assertThat(stream.count()).isEqualTo(0);
    }

    @DisplayName("Deleting a folder been watched returns an empty Stream")
    @Test
    void testListenerFailureInterruption() {
        new Thread(this::deleteFolderToCauseInterruption).start();

        var stream = new DatFileReader(pathOfFolderToBeDeleted)
            .listenForNewDatFiles(newFile -> {
            });

        assertThat(stream.count()).isEqualTo(0);
    }

    @DisplayName("It can provide the content of a .dat file in a given path")
    @Test
    void testReadingSuccess() {
        var linesOfTheFile = new DatFileReader(testingClasspath.toAbsolutePath())
            .readContentOfExistingDatFile(pathOfFileToRead.toFile())
            .collect(Collectors.toList());

        assertThat(linesOfTheFile)
            .hasSize(DatFileFixtures.validInputFileContent.size())
            .containsAll(DatFileFixtures.validInputFileContent);
    }

    @DisplayName("Trying to read the content of an invalid file returns an empty Stream")
    @Test
    void testReadingFailureIO() {
        var invalidPath = testingClasspath.resolve("not-found");

        var linesRead = new DatFileReader(invalidPath)
            .readContentOfExistingDatFile(invalidPath.toFile());

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

    private void createFolderToBeDeleted() {
        try {
            this.pathOfFolderToBeDeleted = Files.createTempDirectory(testingClasspath, "delete-me");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFolderToCauseInterruption() {
        try {
            Thread.sleep(250);
            Files.deleteIfExists(pathOfFolderToBeDeleted);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
