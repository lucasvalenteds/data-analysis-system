package com.ilegra.lucasvalente.desafio.mappers;

import com.ilegra.lucasvalente.desafio.pojos.SalesDataItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class SalesDataItemMapperTest extends DataMapperTest {

    @DisplayName("It can convert a valid String to an Object")
    @Test
    void testConvertFromStringToObject() {
        DataMapper<List<SalesDataItem>> mapper = new SalesDataItemMapper();

        Optional<List<SalesDataItem>> customer = mapper.mapStringToObject(new String[]{"[1-10-100,2-30-2.50,3-40-3.10]"});
        assertThat(customer.isPresent()).isTrue();

        if (customer.isPresent()) {
            List<SalesDataItem> instance = customer.get();
            assertThat(instance).hasSize(3);
        } else {
            fail("The object should be available through Optional.");
        }
    }
}
