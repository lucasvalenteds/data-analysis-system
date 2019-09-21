package io.lucasvalenteds.das.sale;

import io.lucasvalenteds.das.engine.LineParser;
import io.lucasvalenteds.das.engine.LineParserTest;
import io.lucasvalenteds.das.testing.DatFileFixtures;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import org.junit.jupiter.api.Test;

class SalesParserTest extends LineParserTest {

    private final LineParser<Sale> fileParser = new SalesParser(new SalesMapper(new SalesDataItemMapper()));

    @Test
    void testItCanParseSales() {
        var sales = fileParser.parseLines(DatFileFixtures.validInputFileContent);

        assertEquals(2, sales.size());
        assertIterableEquals(List.of("Diego", "Renato"), sales.stream().map(Sale::getSalesmanName).collect(Collectors.toList()));
        assertIterableEquals(List.of("003", "003"), sales.stream().map(Sale::getId).collect(Collectors.toList()));
        assertIterableEquals(List.of("10", "08"), sales.stream().map(Sale::getCode).collect(Collectors.toList()));
    }

    @Test
    void testIdentifier() {
        assertThat(fileParser.getDataClassIdentifier()).isEqualTo("003");
    }
}
