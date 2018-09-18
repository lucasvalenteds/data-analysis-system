package com.ilegra.lucasvalente.desafio.mappers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class CustomerMapperTest extends DataMapperTest {

    @DisplayName("It can convert a valid String to an Object")
    @Test
    void testConvertFromStringToObject() {
        var mapper = new CustomerMapper();

        var customer = mapper.mapStringToObject("002ç2345675434544345çJose da SilvaçRural".split(TOKEN));

        if (customer.isPresent()) {
            var instance = customer.get();
            assertThat(instance.getId()).isEqualTo("002");
            assertThat(instance.getCnpj()).isEqualTo("2345675434544345");
            assertThat(instance.getName()).isEqualTo("Jose da Silva");
            assertThat(instance.getBusinessArea()).isEqualTo("Rural");
        } else {
            fail("The object should be available through Optional.");
        }
    }
}
