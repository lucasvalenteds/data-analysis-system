package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.mappers.SalesmanMapper;
import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;
import com.ilegra.lucasvalente.desafio.testing.DatFileFixtures;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalesmanParserTest extends LineParserTest {

    private final LineParser<SalesmanData> fileParser = new SalesmanParser(new SalesmanMapper());

    @DisplayName("It can find a salesman data in a line")
    @Test
    void testItCanParseSalesman() {
        var salesmen = fileParser.parseLines(DatFileFixtures.validInputFileContent);

        assertThat(salesmen).hasSize(2);
        assertThat(salesmen).containsAll(List.of(
            new SalesmanData("001", "1234567891234", "Diego", 50000.0),
            new SalesmanData("001", "3245678865434", "Renato", 40000.99)));
    }

    @DisplayName("The customer identifier is 001")
    @Test
    void testIdentifier() {
        assertThat(fileParser.getDataClassIdentifier()).isEqualTo("001");
    }
}
