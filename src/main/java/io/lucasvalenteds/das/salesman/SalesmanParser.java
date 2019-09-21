package io.lucasvalenteds.das.salesman;

import io.lucasvalenteds.das.engine.DataMapper;
import io.lucasvalenteds.das.engine.LineParser;

public class SalesmanParser extends LineParser<Salesman> {

    public SalesmanParser(DataMapper<Salesman> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "001";
    }
}
