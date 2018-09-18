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

public class DatFileListener implements FileListener {

    private Path directoryWithDatFiles;
    private FileReader datFileReader;

    public DatFileListener(Path directoryWithDatFiles, FileReader datFileReader) {
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
            errorConsumer.accept(exception);
        }
    }
}
