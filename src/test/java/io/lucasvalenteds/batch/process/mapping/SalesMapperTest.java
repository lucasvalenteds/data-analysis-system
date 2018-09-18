package io.lucasvalenteds.batch.process.mapping;

import io.lucasvalenteds.batch.data.SalesDataItem;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

class SalesMapperTest extends DataMapperTest {

    private final DataMapper<List<SalesDataItem>> itemsMapper = new SalesDataItemMapper();

    @DisplayName("It can convert a valid String to an Object")
    @Test
    void testConvertFromStringToObject() {
        var mapper = new SalesMapper(itemsMapper);

        var customer = mapper.mapStringToObject("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego".split(TOKEN));

        if (customer.isPresent()) {
            var instance = customer.get();
            assertThat(instance.getId()).isEqualTo("003");
            assertThat(instance.getCode()).isEqualTo("10");
            assertThat(instance.getSalesmanName()).isEqualTo("Diego");
            assertThat(instance.getItemsSold())
                .hasSize(3)
                .containsAll(List.of(
                    new SalesDataItem("1", 10, 100),
                    new SalesDataItem("2", 30, 2.50),
                    new SalesDataItem("3", 40, 3.10)
                ));
        } else {
            fail("The object should be available through Optional.");
        }
    }
}
