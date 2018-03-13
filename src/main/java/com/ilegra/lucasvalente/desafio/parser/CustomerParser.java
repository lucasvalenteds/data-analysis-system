package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.mappers.DataMapper;
import com.ilegra.lucasvalente.desafio.pojos.CustomerData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerParser extends LineParser<CustomerData> {

    private final DataMapper<CustomerData> mapper;

    public CustomerParser(DataMapper<CustomerData> mapper) {
        this.mapper = mapper;
    }

    @Override
    public String getDataClassIdentifier() {
        return "002";
    }

    @Override
    public List<Optional<CustomerData>> parseLines(List<String> line) {
        return prepareStream(line)
                .map(mapper::mapStringToObject)
                .collect(Collectors.toList());
    }
}
