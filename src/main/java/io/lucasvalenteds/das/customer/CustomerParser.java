package io.lucasvalenteds.das.customer;

import io.lucasvalenteds.das.engine.DataMapper;
import io.lucasvalenteds.das.engine.LineParser;

public class CustomerParser extends LineParser<Customer> {

    public CustomerParser(DataMapper<Customer> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "002";
    }
}
