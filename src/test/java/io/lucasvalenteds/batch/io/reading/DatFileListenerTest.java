package io.lucasvalenteds.batch.io.reading;

import io.lucasvalenteds.batch.testing.DatFileFixtures;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.mockito.Mockito.mock;

class DatFileListenerTest {

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

        var listener = new DatFileListener(testingClasspath.toAbsolutePath(), mock(FileReader.class));
        listener.listenForNewFiles(
            System.err::println,
            (file, fileReader) -> filesDetectedByTheListener.add(file));

        Thread.sleep(1000); // Bad Smell

        assertThat(filesDetectedByTheListener).hasSize(1);
        Files.deleteIfExists(newFileCreatedForTheTest);
    }

    @DisplayName("Trying to watch a invalid folder returns an empty Stream")
    @Test
    void testListenerFailureIO() {
        var invalidPath = testingClasspath.resolve("not-found");

        var listener = new DatFileListener(invalidPath, mock(FileReader.class));

        listener.listenForNewFiles(
            error -> assertThat(error).isInstanceOf(IOException.class),
            (newFile, reader) -> fail("This is not called when there are errors."));
    }

    @DisplayName("Deleting a folder been watched returns an empty Stream")
    @Test
    void testListenerFailureInterruption() {
        new Thread(this::deleteFolderToCauseInterruption).start();

        var listener = new DatFileListener(pathOfFolderToBeDeleted, mock(FileReader.class));

        listener.listenForNewFiles(
            error -> assertThat(error).isInstanceOf(InterruptedException.class),
            (newFile, reader) -> fail("This is not called when there are errors."));
    }

    private static void createDirectoryForTesting() {
        try {
            testingPath = Files.createTempDirectory(testingClasspath, "listener");
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
