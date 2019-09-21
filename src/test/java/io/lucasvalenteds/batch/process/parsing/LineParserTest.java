package io.lucasvalenteds.batch.process.parsing;

import io.lucasvalenteds.batch.process.mapping.DataMapper;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LineParserTest {

    private final DataMapper<String> mapper = lines -> Optional.of(lines[0]);
    private final LineParser<String> lineParser = new LineParser<>(mapper) {
        @Override
        public String getDataClassIdentifier() {
            return "000";
        }
    };

    @DisplayName("The default token is cedilla")
    @Test
    void testDefaultToken() {
        assertThat(lineParser.getToken()).isEqualTo("ç");
    }

    @DisplayName("It accepts a token to split the data")
    @Test
    void testCustomToken() {
        lineParser.setToken(";");

        assertThat(lineParser.getToken()).isEqualTo(";");
    }
}
