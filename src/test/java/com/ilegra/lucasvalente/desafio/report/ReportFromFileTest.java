package com.ilegra.lucasvalente.desafio.report;

import com.ilegra.lucasvalente.desafio.pojos.CustomerData;
import com.ilegra.lucasvalente.desafio.pojos.SalesData;
import com.ilegra.lucasvalente.desafio.pojos.SalesDataItem;
import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReportFromFileTest {

    private final List<SalesmanData> salesmenList = List.of(
            new SalesmanData("001", "1234567891234", "Diego", 50000.0),
            new SalesmanData("001", "3245678865434", "Renato", 40000.99));

    private final List<CustomerData> customersList = List.of(
            new CustomerData("002", "2345675434544345", "Jose da Silva", "Rural"),
            new CustomerData("002", "2345675433444345", "Eduardo Pereira", "Rural"));

    private final List<SalesData> salesList = List.of(
            new SalesData("003", "10", List.of(
                    new SalesDataItem("1", 10, 100.0),
                    new SalesDataItem("2", 30, 2.50),
                    new SalesDataItem("3", 40, 3.10)
            ), "Diego"),
            new SalesData("003", "08", List.of(
                    new SalesDataItem("1", 34, 10),
                    new SalesDataItem("2", 33, 1.50),
                    new SalesDataItem("3", 40, 0.10)
            ), "Renato"));

    private final ReportFromFile report = new ReportFromFile(salesmenList, customersList, salesList);

    @DisplayName("Contains the amount of customers")
    @Test
    void testAmountOfCustomers() {
        assertThat(report.getAmountOfCustomers()).isEqualTo(Optional.of(2));
    }

    @DisplayName("Contains the amount of salesmen")
    @Test
    void testAmountOfSalesmen() {
        assertThat(report.getAmountOfSalesmen()).isEqualTo(Optional.of(2));
    }

    @DisplayName("Contains the most expensive sale")
    @Test
    void testMostExpensiveSale() {
        assertThat(report.getMostExpensiveSaleId().isPresent()).isTrue();
        assertThat(report.getMostExpensiveSaleId().get()).isEqualTo("10");
    }

    @DisplayName("Contains the salesman less productive which is who sold less items")
    @Test
    void testLeastProductiveSalesman() {
        assertThat(report.getLeastProductiveSalesman().isPresent()).isTrue();
        assertThat(report.getLeastProductiveSalesman().get()).isEqualTo("Diego");
    }
}
