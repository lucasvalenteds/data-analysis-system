package io.lucasvalenteds.batch.process.parsing;

import io.lucasvalenteds.batch.process.mapping.DataMapper;
import io.lucasvalenteds.batch.data.SalesmanData;

public class SalesmanParser extends LineParser<SalesmanData> {

    public SalesmanParser(DataMapper<SalesmanData> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "001";
    }
}
