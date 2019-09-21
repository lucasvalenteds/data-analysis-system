package io.lucasvalenteds.das.salesman;

import io.lucasvalenteds.das.engine.DataMapper;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class SalesmanMapper implements DataMapper<Salesman> {

    @Override
    public Optional<Salesman> mapStringToObject(String[] fileLines) {
        return Stream.of(Arrays.asList(fileLines))
                .map(tokens -> Optional
                        .of(new Salesman(tokens.get(0), tokens.get(1), tokens.get(2), Double.parseDouble(tokens.get(3))))
                        .orElseThrow(IllegalArgumentException::new))
                .findFirst();
    }
}
