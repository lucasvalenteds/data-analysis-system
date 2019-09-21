package io.lucasvalenteds.batch.process.parsing;

import io.lucasvalenteds.batch.process.mapping.DataMapper;
import io.lucasvalenteds.batch.data.Salesman;

public class SalesmanParser extends LineParser<Salesman> {

    public SalesmanParser(DataMapper<Salesman> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "001";
    }
}
