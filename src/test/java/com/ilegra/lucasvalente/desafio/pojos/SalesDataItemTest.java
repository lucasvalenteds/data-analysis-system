package com.ilegra.lucasvalente.desafio.pojos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalesDataItemTest {

    @DisplayName("It has ID, quantity and price")
    @Test
    void testItHasTheNecessaryProperties() {
        SalesDataItem item = new SalesDataItem("1", 34, 1.50);

        assertThat(item.getId()).isEqualTo("1");
        assertThat(item.getQuantity()).isEqualTo(34);
        assertThat(item.getPrice()).isEqualTo(1.50);
    }
}
