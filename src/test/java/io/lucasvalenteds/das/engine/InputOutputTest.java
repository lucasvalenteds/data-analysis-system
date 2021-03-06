package io.lucasvalenteds.das.engine;

import io.reactivex.Single;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InputOutputTest {

    private final InputOutput manager = new InputOutput();
    private final Path classpath = Path.of("src", "test", "resources");

    @Test
    void testItCanReadLinesOfFile() {
        var filePath = Path.of("src", "test", "resources", "sample.dat");
        var lines = manager.readFileLines(filePath)
            .blockingGet();

        assertEquals(6, lines.size());
    }

    @Test
    void testItFailsToReadFileThatDoesNotExist() {
        var filePath = Path.of("src", "test", "resources", "not-sample.dat");

        manager.readFileLines(filePath)
            .test()
            .assertError(NoSuchFileException.class)
            .assertError(throwable -> throwable.getMessage().contains(filePath.toString()))
            .dispose();
    }

    @Test
    void testItCanWriteReportToFile() throws IOException {
        var filePath = Path.of("src", "test", "resources", "sample.done.date");
        var report = "Hello World";

        var reportWritten = manager.writeTextToFile(report, filePath)
            .blockingGet();

        assertEquals(report, reportWritten);

        Files.deleteIfExists(filePath);
    }

    @Test
    void testItWatchesCreatedFiles() {
        new Thread(this::createFixtures).start();

        var filesCreated = new AtomicInteger();
        manager.watchNewFiles(classpath)
            .take(2)
            .doOnNext(path -> filesCreated.incrementAndGet())
            .subscribe();

        assertEquals(2, filesCreated.get());
    }

    private void createFixtures() {
        try {
            assertEquals(0, Single.timer(500, TimeUnit.MILLISECONDS).blockingGet());
            var file1 = Files.createFile(classpath.resolve("file-1.txt"));
            var file2 = Files.createFile(classpath.resolve("file-2.txt"));
            Files.deleteIfExists(file1);
            Files.deleteIfExists(file2);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}