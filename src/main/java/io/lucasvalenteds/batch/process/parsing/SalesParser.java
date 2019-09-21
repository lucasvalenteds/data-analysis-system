package io.lucasvalenteds.batch.process.parsing;

import io.lucasvalenteds.batch.process.mapping.DataMapper;
import io.lucasvalenteds.batch.data.Sale;

public class SalesParser extends LineParser<Sale> {

    public SalesParser(DataMapper<Sale> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "003";
    }
}
