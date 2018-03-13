package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.mappers.DataMapper;
import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SalesmanParser extends LineParser<SalesmanData> {

    private final DataMapper<SalesmanData> mapper;

    public SalesmanParser(DataMapper<SalesmanData> mapper) {
        this.mapper = mapper;
    }

    @Override
    public String getDataClassIdentifier() {
        return "001";
    }

    @Override
    public List<Optional<SalesmanData>> parseLines(List<String> line) {
        return prepareStream(line)
                .map(mapper::mapStringToObject)
                .collect(Collectors.toList());
    }
}
