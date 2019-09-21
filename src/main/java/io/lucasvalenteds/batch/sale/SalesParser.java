package io.lucasvalenteds.batch.sale;

import io.lucasvalenteds.batch.engine.DataMapper;
import io.lucasvalenteds.batch.engine.LineParser;

public class SalesParser extends LineParser<Sale> {

    public SalesParser(DataMapper<Sale> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "003";
    }
}
