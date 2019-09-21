package io.lucasvalenteds.das.sale;

import io.lucasvalenteds.das.engine.DataMapper;
import io.lucasvalenteds.das.engine.LineParser;

public class SalesParser extends LineParser<Sale> {

    public SalesParser(DataMapper<Sale> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "003";
    }
}
