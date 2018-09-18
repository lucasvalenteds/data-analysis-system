package io.lucasvalenteds.batch.io.reading;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatFileReader implements FileReader {

    private static final Logger LOGGER = LogManager.getLogger(DatFileReader.class);

    @Override
    public Stream<String> readLines(Path file) {
        try {
            return Files.newBufferedReader(file).lines();
        } catch (IOException exception) {
            LOGGER.warn(exception.getMessage());
            return Stream.empty();
        }
    }
}
