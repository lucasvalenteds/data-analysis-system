package io.lucasvalenteds.batch.customer;

import io.lucasvalenteds.batch.engine.DataMapper;
import io.lucasvalenteds.batch.engine.LineParser;

public class CustomerParser extends LineParser<Customer> {

    public CustomerParser(DataMapper<Customer> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "002";
    }
}
