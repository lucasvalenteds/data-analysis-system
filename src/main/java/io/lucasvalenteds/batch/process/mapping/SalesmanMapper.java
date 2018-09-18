package io.lucasvalenteds.batch.process.mapping;

import io.lucasvalenteds.batch.data.SalesmanData;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class SalesmanMapper implements DataMapper<SalesmanData> {

    @Override
    public Optional<SalesmanData> mapStringToObject(String[] fileLines) {
        return Stream.of(Arrays.asList(fileLines))
                .map(tokens -> Optional
                        .of(new SalesmanData(tokens.get(0), tokens.get(1), tokens.get(2), Double.valueOf(tokens.get(3))))
                        .orElseThrow(IllegalArgumentException::new))
                .findFirst();
    }
}
