package io.lucasvalenteds.batch.process.parsing;

import io.lucasvalenteds.batch.process.mapping.DataMapper;
import io.lucasvalenteds.batch.data.SalesData;

public class SalesParser extends LineParser<SalesData> {

    public SalesParser(DataMapper<SalesData> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "003";
    }
}
