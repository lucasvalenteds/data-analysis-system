package com.ilegra.lucasvalente.desafio.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.function.Function;
import java.util.stream.Stream;

public class DatFileReader {

    private final Function<Object, Boolean> doesFilenameEndsWithDotDat = file -> file.toString().endsWith(".dat");

    private WatchKey key;
    private Path directoryWithDatFiles;

    public DatFileReader(Path directoryWithDatFiles) {
        try {
            WatchService watcher = directoryWithDatFiles.getFileSystem().newWatchService();
            this.key = directoryWithDatFiles.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
            this.directoryWithDatFiles = directoryWithDatFiles;
        } catch (IOException ex) {
            // TODO:
        }
    }

    public Stream<File> listExistingDatFiles() {
        try {
            return Files.list(directoryWithDatFiles)
                    .map(Path::toFile)
                    .filter(doesFilenameEndsWithDotDat::apply)
                    .filter(File::isFile);
        } catch (IOException ex) {
            return Stream.empty();
        }
    }

    public Stream<String> readContentOfExistingDatFiles(File file) {
        try {
            return Files.newBufferedReader(file.toPath()).lines();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return Stream.empty();
        }
    }

    public Stream<File> listenForNewDatFiles() {
        return key.pollEvents().stream()
                .map(WatchEvent::context)
                .filter(doesFilenameEndsWithDotDat::apply)
                .map(Object::toString)
                .map(File::new);
    }
}
