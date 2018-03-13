package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.mappers.CustomerMapper;
import com.ilegra.lucasvalente.desafio.pojos.CustomerData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LineParserTest {

    static final List<String> inputDataAsString = List.of(
            "001ç1234567891234çDiegoç50000",
            "001ç3245678865434çRenatoç40000.99",
            "002ç2345675434544345çJose da SilvaçRural",
            "002ç2345675433444345çEduardo PereiraçRural",
            "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
            "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");

    @DisplayName("It is possible to use other String instead of cedilla to separate the data")
    @Test
    void testTheTokenCanBeChanged() {
        LineParser<CustomerData> customerParser = new CustomerParser(mock(CustomerMapper.class));
        assertThat(customerParser.getToken()).isEqualTo("ç");

        customerParser.setToken(";");
        assertThat(customerParser.getToken()).isEqualTo(";");
    }

}
