package com.ilegra.lucasvalente.desafio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SalesDataTest {

    @DisplayName("It has ID and the Salesman name responsible for the sale")
    @Test
    void testBasicProperties() {
        SalesData salesData = new SalesData("003", "Renato", List.of());

        assertThat(salesData.getId()).isEqualTo("003");
        assertThat(salesData.getName()).isEqualTo("Renato");
    }

    @DisplayName("It also has a list of items sold")
    @Test
    void testItemSold() {
        SalesData salesData = new SalesData("003", "Renato", List.of(
                new SalesDataItem("2", 2, 15.0),
                new SalesDataItem("60", 150, 0.25),
                new SalesDataItem("0", 2, 1.0)
        ));

        assertThat(salesData.getItemsSold()).hasSize(3);
    }

}
