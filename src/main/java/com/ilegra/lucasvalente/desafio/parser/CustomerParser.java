package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.mappers.DataMapper;
import com.ilegra.lucasvalente.desafio.pojos.CustomerData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomerParser extends LineParser<CustomerData> {

    public CustomerParser(DataMapper<CustomerData> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "002";
    }
}
