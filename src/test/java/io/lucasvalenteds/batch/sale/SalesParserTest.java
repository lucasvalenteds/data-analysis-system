package io.lucasvalenteds.batch.sale;

import io.lucasvalenteds.batch.process.parsing.LineParser;
import io.lucasvalenteds.batch.process.parsing.LineParserTest;
import io.lucasvalenteds.batch.testing.DatFileFixtures;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalesParserTest extends LineParserTest {

    private final LineParser<Sale> fileParser = new SalesParser(new SalesMapper(new SalesDataItemMapper()));

    @DisplayName("It can find a costumer data in a line")
    @Test
    void testItCanParseCustomer() {
        var sales = fileParser.parseLines(DatFileFixtures.validInputFileContent);

        assertThat(sales).hasSize(2);
        assertThat(sales.stream().map(Sale::getSalesmanName)).contains("Diego", "Renato");
        assertThat(sales.stream().map(Sale::getId)).contains("003", "003");
        assertThat(sales.stream().map(Sale::getCode)).contains("10", "08");
    }

    @DisplayName("The customer identifier is 003")
    @Test
    void testIdentifier() {
        assertThat(fileParser.getDataClassIdentifier()).isEqualTo("003");
    }
}
