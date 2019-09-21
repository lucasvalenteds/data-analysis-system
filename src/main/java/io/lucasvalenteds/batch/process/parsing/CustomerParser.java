package io.lucasvalenteds.batch.process.parsing;

import io.lucasvalenteds.batch.process.mapping.DataMapper;
import io.lucasvalenteds.batch.data.Customer;

public class CustomerParser extends LineParser<Customer> {

    public CustomerParser(DataMapper<Customer> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "002";
    }
}
