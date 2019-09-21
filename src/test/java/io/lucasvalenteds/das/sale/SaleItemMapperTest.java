package io.lucasvalenteds.das.sale;

import io.lucasvalenteds.das.engine.DataMapperTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class SaleItemMapperTest extends DataMapperTest {

    @Test
    void testConvertFromStringToObject() {
        var mapper = new SalesDataItemMapper();

        var saleItems = mapper.mapStringToObject(new String[]{"[1-10-100,2-30-2.50,3-40-3.10]"});

        assertTrue(saleItems.isPresent());
        assertEquals(3, saleItems.get().size());
    }
}
