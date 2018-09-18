package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.mappers.CustomerMapper;
import com.ilegra.lucasvalente.desafio.pojos.CustomerData;
import com.ilegra.lucasvalente.desafio.testing.DatFileFixtures;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerParserTest extends LineParserTest {

    private final LineParser<CustomerData> fileParser = new CustomerParser(new CustomerMapper());

    @DisplayName("It can find a costumer data in a line")
    @Test
    void testItCanParseCustomer() {
        var customers = fileParser.parseLines(DatFileFixtures.validInputFileContent);

        assertThat(customers)
            .hasSize(2)
            .containsAll(List.of(
                new CustomerData("002", "2345675434544345", "Jose da Silva", "Rural"),
                new CustomerData("002", "2345675433444345", "Eduardo Pereira", "Rural")
            ));
    }

    @DisplayName("The customer identifier is 002")
    @Test
    void testIdentifier() {
        assertThat(fileParser.getDataClassIdentifier()).isEqualTo("002");
    }
}
