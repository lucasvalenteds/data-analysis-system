package io.lucasvalenteds.das.salesman;

import io.lucasvalenteds.das.engine.LineParser;
import io.lucasvalenteds.das.engine.LineParserTest;
import io.lucasvalenteds.das.testing.DatFileFixtures;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class SalesmanParserTest extends LineParserTest {

    private final LineParser<Salesman> fileParser = new SalesmanParser(new SalesmanMapper());

    @Test
    void testItCanParseSalesman() {
        var salesmen = fileParser.parseLines(DatFileFixtures.validInputFileContent);

        assertEquals(2, salesmen.size());
        assertEquals(new Salesman("001", "1234567891234", "Diego", 50000.0), salesmen.get(0));
        assertEquals(new Salesman("001", "3245678865434", "Renato", 40000.99), salesmen.get(1));
    }

    @Test
    void testIdentifier() {
        assertThat(fileParser.getDataClassIdentifier()).isEqualTo("001");
    }
}
