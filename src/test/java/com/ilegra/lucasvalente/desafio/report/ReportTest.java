package com.ilegra.lucasvalente.desafio.report;

import com.ilegra.lucasvalente.desafio.pojos.CustomerData;
import com.ilegra.lucasvalente.desafio.pojos.SalesData;
import com.ilegra.lucasvalente.desafio.pojos.SalesDataItem;
import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReportTest {

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

    private final Report report = new Report(salesmenList, customersList, salesList);

    @DisplayName("The amount of clients can be found")
    @Test
    void testItCanFindTheAmountOfClients() {
        assertThat(report.getAmountOfCustomers()).isEqualTo(2);
    }

    @DisplayName("The amount of salesmen can be found")
    @Test
    void testItCanFindTheAmountOfSalesmen() {
        assertThat(report.getAmountOfSalesmen()).isEqualTo(2);
    }

    @DisplayName("The id of the most expensive sale can be found")
    @Test
    void testTheMostExpensiveSaleCanBeFound() {
        assertThat(report.getMostExpensiveSaleId()).isEqualTo("10");
    }

    @DisplayName("The worst salesman is the one who sold less items")
    @Test
    void testTheLeastProductiveSalesmanCanBeFound() {
        assertThat(report.getLeastProductiveSalesman().getName()).isEqualTo("Diego");
    }
}
