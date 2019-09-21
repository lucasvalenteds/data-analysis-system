package io.lucasvalenteds.das.salesman;

import io.lucasvalenteds.das.engine.DataMapperTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class SalesmanMapperTest extends DataMapperTest {

    @Test
    void testConvertFromStringToObject() {
        var mapper = new SalesmanMapper();

        var salesman = mapper.mapStringToObject("001ç3245678865434çRenatoç40000.99".split(TOKEN));

        assertTrue(salesman.isPresent());
        assertEquals("001", salesman.get().getId());
        assertEquals("3245678865434", salesman.get().getCpf());
        assertEquals("Renato", salesman.get().getName());
        assertEquals(40000.99, salesman.get().getSalary(), 0.01);
    }
}
