package io.lucasvalenteds.das.customer;

import io.lucasvalenteds.das.engine.DataMapperTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class CustomerMapperTest extends DataMapperTest {

    @Test
    void testConvertFromStringToObject() {
        var mapper = new CustomerMapper();

        var customer = mapper.mapStringToObject("002ç2345675434544345çJose da SilvaçRural".split(TOKEN));

        assertTrue(customer.isPresent());
        assertEquals("002", customer.get().getId());
        assertEquals("2345675434544345", customer.get().getCnpj());
        assertEquals("Jose da Silva", customer.get().getName());
        assertEquals("Rural", customer.get().getBusinessArea());
    }
}
