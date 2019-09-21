package io.lucasvalenteds.batch.engine;

import java.util.Optional;

@FunctionalInterface
public interface DataMapper<T> {

    Optional<T> mapStringToObject(String[] fileLines);
}
