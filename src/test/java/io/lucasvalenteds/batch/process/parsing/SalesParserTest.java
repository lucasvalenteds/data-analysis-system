package io.lucasvalenteds.batch.process.parsing;

import io.lucasvalenteds.batch.process.mapping.SalesDataItemMapper;
import io.lucasvalenteds.batch.process.mapping.SalesMapper;
import io.lucasvalenteds.batch.data.SalesData;
import io.lucasvalenteds.batch.testing.DatFileFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalesParserTest extends LineParserTest {

    private final LineParser<SalesData> fileParser = new SalesParser(new SalesMapper(new SalesDataItemMapper()));

    @DisplayName("It can find a costumer data in a line")
    @Test
    void testItCanParseCustomer() {
        var sales = fileParser.parseLines(DatFileFixtures.validInputFileContent);

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
