package io.lucasvalenteds.das.customer;

import io.lucasvalenteds.das.engine.LineParser;
import io.lucasvalenteds.das.engine.LineParserTest;
import io.lucasvalenteds.das.testing.DatFileFixtures;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CustomerParserTest extends LineParserTest {

    private final LineParser<Customer> fileParser = new CustomerParser(new CustomerMapper());

    @Test
    void testItCanParseCustomer() {
        var customers = fileParser.parseLines(DatFileFixtures.validInputFileContent);

        assertEquals(2, customers.size());
        assertEquals(new Customer("002", "2345675434544345", "Jose da Silva", "Rural"), customers.get(0));
        assertEquals(new Customer("002", "2345675433444345", "Eduardo Pereira", "Rural"), customers.get(1));
    }

    @Test
    void testIdentifier() {
        assertEquals("002", fileParser.getDataClassIdentifier());
    }
}
