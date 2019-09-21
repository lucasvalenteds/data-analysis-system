package io.lucasvalenteds.das.engine;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LineParserTest {

    private final DataMapper<String> mapper = lines -> Optional.of(lines[0]);
    private final LineParser<String> lineParser = new LineParser<>(mapper) {
        @Override
        public String getDataClassIdentifier() {
            return "000";
        }
    };

    @Test
    void testDefaultTokenIsCedilla() {
        assertEquals("ç", lineParser.getToken());
    }

    @Test
    void testCustomTokenCanBeSet() {
        lineParser.setToken(";");

        assertEquals(";", lineParser.getToken());
    }
}
