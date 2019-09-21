package io.lucasvalenteds.batch.v2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PathWatcherTest {

    private final PathWatcher watcher = new PathWatcher();
    private final Path classpath = Path.of("src", "test", "resources");

    @Test
    void testItWatchesCreatedFiles() {
        new Thread(this::createFixtures).start();

        var filesCreated = new AtomicInteger();
        watcher.watchNewFiles(classpath)
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