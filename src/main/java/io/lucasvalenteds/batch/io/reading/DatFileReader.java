package io.lucasvalenteds.batch.io.reading;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class DatFileReader implements FileReader {

    @Override
    public Stream<String> readLines(Path file) {
        try {
            return Files.newBufferedReader(file).lines();
        } catch (IOException ex) {
            return Stream.empty();
        }
    }
}
