package com.ilegra.lucasvalente.desafio.mappers;

import com.ilegra.lucasvalente.desafio.pojos.CustomerData;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class CustomerMapper implements DataMapper<CustomerData> {

    @Override
    public Optional<CustomerData> mapStringToObject(String[] fileLines) {
        return Stream.of(Arrays.asList(fileLines))
                .map(tokens -> Optional
                        .of(new CustomerData(tokens.get(0), tokens.get(1), tokens.get(2), tokens.get(3)))
                        .orElseThrow(IllegalArgumentException::new))
                .findFirst();
    }

}
