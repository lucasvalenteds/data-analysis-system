package com.ilegra.lucasvalente.desafio.mappers;

import java.util.Optional;

@FunctionalInterface
public interface DataMapper<T> {

    Optional<T> mapStringToObject(String[] fileLines);
}
