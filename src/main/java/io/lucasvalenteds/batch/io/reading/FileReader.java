package io.lucasvalenteds.batch.io.reading;

import java.nio.file.Path;
import java.util.stream.Stream;

@FunctionalInterface
public interface FileReader {

    Stream<String> readLines(Path file);
}
