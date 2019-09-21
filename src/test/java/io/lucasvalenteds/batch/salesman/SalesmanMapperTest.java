package io.lucasvalenteds.batch.salesman;

import io.lucasvalenteds.batch.process.mapping.DataMapperTest;
import io.lucasvalenteds.batch.salesman.SalesmanMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class SalesmanMapperTest extends DataMapperTest {

    @DisplayName("It can convert a valid String to an Object")
    @Test
    void testConvertFromStringToObject() {
        var mapper = new SalesmanMapper();

        var customer = mapper.mapStringToObject("001ç3245678865434çRenatoç40000.99".split(TOKEN));

        if (customer.isPresent()) {
            var instance = customer.get();
            assertThat(instance.getId()).isEqualTo("001");
            assertThat(instance.getCpf()).isEqualTo("3245678865434");
            assertThat(instance.getName()).isEqualTo("Renato");
            assertThat(instance.getSalary()).isEqualTo(40000.99);
        } else {
            fail("The object should be available through Optional.");
        }
    }
}
