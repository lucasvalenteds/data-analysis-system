package io.lucasvalenteds.batch.process.mapping;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class SaleItemMapperTest extends DataMapperTest {

    @DisplayName("It can convert a valid String to an Object")
    @Test
    void testConvertFromStringToObject() {
        var mapper = new SalesDataItemMapper();

        var customer = mapper.mapStringToObject(new String[]{"[1-10-100,2-30-2.50,3-40-3.10]"});
        assertThat(customer.isPresent()).isTrue();

        if (customer.isPresent()) {
            var instance = customer.get();
            Assertions.assertThat(instance).hasSize(3);
        } else {
            fail("The object should be available through Optional.");
        }
    }
}
