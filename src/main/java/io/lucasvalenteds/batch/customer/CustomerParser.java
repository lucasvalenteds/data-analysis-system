package io.lucasvalenteds.batch.customer;

import io.lucasvalenteds.batch.process.mapping.DataMapper;
import io.lucasvalenteds.batch.customer.Customer;
import io.lucasvalenteds.batch.process.parsing.LineParser;

public class CustomerParser extends LineParser<Customer> {

    public CustomerParser(DataMapper<Customer> mapper) {
        super(mapper);
    }

    @Override
    public String getDataClassIdentifier() {
        return "002";
    }
}
