package io.lucasvalenteds.batch.process.parsing;

import io.lucasvalenteds.batch.process.mapping.SalesmanMapper;
import io.lucasvalenteds.batch.data.Salesman;
import io.lucasvalenteds.batch.testing.DatFileFixtures;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalesmanParserTest extends LineParserTest {

    private final LineParser<Salesman> fileParser = new SalesmanParser(new SalesmanMapper());

    @DisplayName("It can find a salesman data in a line")
    @Test
    void testItCanParseSalesman() {
        var salesmen = fileParser.parseLines(DatFileFixtures.validInputFileContent);

        assertThat(salesmen).hasSize(2);
        assertThat(salesmen).containsAll(List.of(
            new Salesman("001", "1234567891234", "Diego", 50000.0),
            new Salesman("001", "3245678865434", "Renato", 40000.99)));
    }

    @DisplayName("The customer identifier is 001")
    @Test
    void testIdentifier() {
        assertThat(fileParser.getDataClassIdentifier()).isEqualTo("001");
    }
}
