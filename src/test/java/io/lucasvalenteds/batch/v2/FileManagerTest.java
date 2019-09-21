package io.lucasvalenteds.batch.v2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class FileManagerTest {

    private final FileManager manager = new FileManager();

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
}