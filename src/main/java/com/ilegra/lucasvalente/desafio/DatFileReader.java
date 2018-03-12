package com.ilegra.lucasvalente.desafio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.function.Function;
import java.util.stream.Stream;

class DatFileReader {

    private final Function<Object, Boolean> doesFilenameEndsWithDotDat = file -> file.toString().endsWith(".dat");

    private final WatchKey key;
    private final Path directoryWithDatFiles;

    DatFileReader(Path directoryWithDatFiles) throws IOException {
        WatchService watcher = directoryWithDatFiles.getFileSystem().newWatchService();
        this.key = directoryWithDatFiles.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
        this.directoryWithDatFiles = directoryWithDatFiles;
    }

    public Stream<File> listExistingDatFiles() throws IOException {
        return Files.list(directoryWithDatFiles)
                .map(Path::toFile)
                .filter(doesFilenameEndsWithDotDat::apply)
                .filter(File::isFile);
    }

    public Stream<String> readContentOfExistingDatFiles(File file) throws IOException {
        return new BufferedReader(new FileReader(file)).lines();
    }

    public Stream<File> listenForNewDatFiles() {
        return key.pollEvents().stream()
                .map(WatchEvent::context)
                .filter(doesFilenameEndsWithDotDat::apply)
                .map(Object::toString)
                .map(File::new);
    }
}
