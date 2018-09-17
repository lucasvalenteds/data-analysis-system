package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.mappers.SalesDataItemMapper;
import com.ilegra.lucasvalente.desafio.mappers.SalesMapper;
import com.ilegra.lucasvalente.desafio.pojos.SalesData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class SalesParserTest extends LineParserTest {

    private final LineParser<SalesData> fileParser = new SalesParser(new SalesMapper(new SalesDataItemMapper()));

    @DisplayName("It can find a costumer data in a line")
    @Test
    void testItCanParseCustomer() {
        List<SalesData> sales = fileParser.parseLines(inputDataAsString);

        assertThat(sales).hasSize(2);
        assertThat(sales.stream().map(SalesData::getSalesmanName)).contains("Diego", "Renato");
        assertThat(sales.stream().map(SalesData::getId)).contains("003", "003");
        assertThat(sales.stream().map(SalesData::getCode)).contains("10", "08");
    }

    @DisplayName("The customer identifier is 003")
    @Test
    void testIdentifier() {
        assertThat(fileParser.getDataClassIdentifier()).isEqualTo("003");
    }
}
