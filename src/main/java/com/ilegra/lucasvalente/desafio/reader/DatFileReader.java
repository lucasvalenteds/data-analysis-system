package com.ilegra.lucasvalente.desafio.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

public class DatFileReader {

    private final Function<Object, Boolean> doesFilenameEndsWithDotDat = file -> file.toString().endsWith(".dat");

    private WatchKey key;
    private Path directoryWithDatFiles;

    public DatFileReader(Path directoryWithDatFiles) {
        this.directoryWithDatFiles = directoryWithDatFiles;
    }

    public Stream<String> readContentOfExistingDatFile(File file) {
        try {
            return Files.newBufferedReader(file.toPath()).lines();
        } catch (IOException ex) {
            return Stream.empty();
        }
    }

    public Stream<File> listenForNewDatFiles(Consumer<File> fileConsumer) {
        try (WatchService watcher = directoryWithDatFiles.getFileSystem().newWatchService()) {
            directoryWithDatFiles.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
            WatchKey key;
            do {
                key = watcher.take();
                key.pollEvents().stream()
                    .map(WatchEvent::context)
                    .filter(doesFilenameEndsWithDotDat::apply)
                    .map(Object::toString)
                    .map(File::new)
                    .forEach(fileConsumer);
            } while (key.reset());
        } catch (IOException | InterruptedException exception) {
            return Stream.empty();
        }

        return Stream.empty();
    }
}
