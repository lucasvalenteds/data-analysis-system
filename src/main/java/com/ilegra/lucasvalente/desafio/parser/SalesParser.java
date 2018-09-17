package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.mappers.DataMapper;
import com.ilegra.lucasvalente.desafio.pojos.SalesData;

public class SalesParser extends LineParser<SalesData> {

    public SalesParser(DataMapper<SalesData> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "003";
    }
}
