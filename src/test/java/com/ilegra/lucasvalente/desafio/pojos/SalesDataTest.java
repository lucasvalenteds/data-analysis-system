package com.ilegra.lucasvalente.desafio.pojos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SalesDataTest {

    @DisplayName("It has ID, a code and the name of the responsible salesman")
    @Test
    void testBasicProperties() {
        SalesData salesData = new SalesData("003", "10", List.of(), "Renato");

        assertThat(salesData.getId()).isEqualTo("003");
        assertThat(salesData.getCode()).isEqualTo("10");
        assertThat(salesData.getSalesmanName()).isEqualTo("Renato");
    }

    @DisplayName("It also has a list of items sold")
    @Test
    void testItemSold() {
        List<SalesDataItem> itemsSold = List.of(
                new SalesDataItem("2", 2, 15.0),
                new SalesDataItem("60", 150, 0.25),
                new SalesDataItem("0", 2, 1.0));

        SalesData salesData = new SalesData("003", "1", itemsSold, "Renato");

        assertThat(salesData.getItemsSold()).hasSize(3);
    }

}
