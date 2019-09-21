package io.lucasvalenteds.batch.customer;

import io.lucasvalenteds.batch.engine.DataMapper;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class CustomerMapper implements DataMapper<Customer> {

    @Override
    public Optional<Customer> mapStringToObject(String[] fileLines) {
        return Stream.of(Arrays.asList(fileLines))
                .map(tokens -> Optional
                        .of(new Customer(tokens.get(0), tokens.get(1), tokens.get(2), tokens.get(3)))
                        .orElseThrow(IllegalArgumentException::new))
                .findFirst();
    }

}
