package io.lucasvalenteds.batch.process.parsing;

import io.lucasvalenteds.batch.process.mapping.DataMapper;
import io.lucasvalenteds.batch.data.CustomerData;

public class CustomerParser extends LineParser<CustomerData> {

    public CustomerParser(DataMapper<CustomerData> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "002";
    }
}
