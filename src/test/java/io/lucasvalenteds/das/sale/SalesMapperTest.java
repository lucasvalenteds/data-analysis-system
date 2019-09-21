package io.lucasvalenteds.das.sale;

import io.lucasvalenteds.das.engine.DataMapper;
import io.lucasvalenteds.das.engine.DataMapperTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class SalesMapperTest extends DataMapperTest {

    private final DataMapper<List<SaleItem>> itemsMapper = new SalesDataItemMapper();

    @Test
    void testConvertFromStringToObject() {
        var mapper = new SalesMapper(itemsMapper);

        var sale = mapper.mapStringToObject("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego".split(TOKEN));

        assertTrue(sale.isPresent());
        assertEquals("003", sale.get().getId());
        assertEquals("10", sale.get().getCode());
        assertEquals("Diego", sale.get().getSalesmanName());

        assertEquals(3, sale.get().getItemsSold().size());
        assertEquals(new SaleItem("1", 10, 100), sale.get().getItemsSold().get(0));
        assertEquals(new SaleItem("2", 30, 2.50), sale.get().getItemsSold().get(1));
        assertEquals(new SaleItem("3", 40, 3.10), sale.get().getItemsSold().get(2));
    }
}
