package io.lucasvalenteds.batch.v2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

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
    @Disabled
    void testItFailsToReadFileThatDoesNotExist() {
        var filePath = Path.of("src", "test", "resources", "not-sample.dat");

        assertThrows(Exception.class, () -> {
            manager.readFileLines(filePath).subscribe();
        });
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
            Thread.sleep(500);
            var file1 = Files.createFile(classpath.resolve("file-1.txt"));
            var file2 = Files.createFile(classpath.resolve("file-2.txt"));
            Files.deleteIfExists(file1);
            Files.deleteIfExists(file2);
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}