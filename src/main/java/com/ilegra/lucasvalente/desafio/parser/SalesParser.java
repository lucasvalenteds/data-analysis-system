package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.mappers.DataMapper;
import com.ilegra.lucasvalente.desafio.pojos.SalesData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SalesParser extends LineParser<SalesData> {

    private final DataMapper<SalesData> mapper;

    public SalesParser(DataMapper<SalesData> mapper) {
        this.mapper = mapper;
    }

    @Override
    public String getDataClassIdentifier() {
        return "003";
    }

    @Override
    public List<Optional<SalesData>> parseLines(List<String> line) {
        return prepareStream(line)
                .map(mapper::mapStringToObject)
                .collect(Collectors.toList());
    }
}
