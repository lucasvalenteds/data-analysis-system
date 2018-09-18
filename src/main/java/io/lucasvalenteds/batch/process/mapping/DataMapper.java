package io.lucasvalenteds.batch.process.mapping;

import java.util.Optional;

@FunctionalInterface
public interface DataMapper<T> {

    Optional<T> mapStringToObject(String[] fileLines);
}
