package io.lucasvalenteds.batch.salesman;

import io.lucasvalenteds.batch.engine.DataMapper;
import io.lucasvalenteds.batch.engine.LineParser;

public class SalesmanParser extends LineParser<Salesman> {

    public SalesmanParser(DataMapper<Salesman> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "001";
    }
}
