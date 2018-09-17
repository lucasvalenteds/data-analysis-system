package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.mappers.DataMapper;
import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;

public class SalesmanParser extends LineParser<SalesmanData> {

    public SalesmanParser(DataMapper<SalesmanData> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "001";
    }
}
