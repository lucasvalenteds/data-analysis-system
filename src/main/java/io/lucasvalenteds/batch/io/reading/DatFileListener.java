package io.lucasvalenteds.batch.io.reading;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatFileListener implements FileListener {

    private static final Logger LOGGER = LogManager.getLogger(DatFileReader.class);

    private final Path directoryWithDatFiles;
    private final FileReader datFileReader;

    public DatFileListener(final Path directoryWithDatFiles, final FileReader datFileReader) {
        this.directoryWithDatFiles = directoryWithDatFiles;
        this.datFileReader = datFileReader;
    }

    @Override
    public void listenForNewFiles(Consumer<Throwable> errorConsumer, BiConsumer<File, FileReader> fileConsumer) {
        try (WatchService watcher = directoryWithDatFiles.getFileSystem().newWatchService()) {
            directoryWithDatFiles.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey key;
            do {
                key = watcher.take();
                key.pollEvents().stream()
                    .map(WatchEvent::context)
                    .filter(file -> file.toString().endsWith(".dat"))
                    .map(Object::toString)
                    .map(File::new)
                    .forEach(file -> fileConsumer.accept(file, datFileReader));
            } while (key.reset());
        } catch (IOException | InterruptedException exception) {
            LOGGER.warn("Could not listen the folder: " + exception.getMessage());
            errorConsumer.accept(exception);
        }
    }
}
