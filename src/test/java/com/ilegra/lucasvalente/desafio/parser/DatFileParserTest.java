package com.ilegra.lucasvalente.desafio.parser;

import com.ilegra.lucasvalente.desafio.pojos.CustomerData;
import com.ilegra.lucasvalente.desafio.pojos.SalesData;
import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class DatFileParserTest {

    private final DatFileParser fileParser = new DatFileParser();

    private final List<String> inputDataAsString = List.of(
            "001ç1234567891234çDiegoç50000",
            "001ç3245678865434çRenatoç40000.99",
            "002ç2345675434544345çJose da SilvaçRural",
            "002ç2345675433444345çEduardo PereiraçRural",
            "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
            "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");

    @DisplayName("It can find a salesman data in a line")
    @Test
    void testItCanParseSalesman() {
        List<SalesmanData> salesmen = fileParser.findSalesmen(inputDataAsString);

        assertThat(salesmen)
                .hasSize(2)
                .containsAll(List.of(
                        new SalesmanData("001", "1234567891234", "Diego", 50000.0),
                        new SalesmanData("001", "3245678865434", "Renato", 40000.99)));
    }

    @DisplayName("It can find a costumer data in a line")
    @Test
    void testItCanParseCustomer() {
        List<CustomerData> customers = fileParser.findCustomers(inputDataAsString);

        assertThat(customers)
                .hasSize(2)
                .containsAll(List.of(
                        new CustomerData("002", "2345675434544345", "Jose da Silva", "Rural"),
                        new CustomerData("002", "2345675433444345", "Eduardo Pereira", "Rural")
                ));
    }

    @DisplayName("It can find a sale data in a line")
    @Test
    void testItCanFindASale() {
        List<SalesData> sales = fileParser.findSales(inputDataAsString);

        assertThat(sales).hasSize(2);
        assertThat(sales.stream().map(SalesData::getSalesmanName)).contains("Diego", "Renato");
        assertThat(sales.stream().map(SalesData::getId)).contains("003", "003");
        assertThat(sales.stream().map(SalesData::getCode)).contains("10", "08");
    }

    @DisplayName("It can aggregate SalesData and SalesItemData")
    @Test
    void testParseSalesItemBabySteps() {
        String saleAsString = "003ç10ç[ 1-10-100 , 2-30-2.50 , 3-40-3.10 ]çDiego";

        List<String> separetedByCedilla = Arrays.asList(saleAsString.split("ç"));
        assertThat(separetedByCedilla).containsAll(List.of("003", "10", "[ 1-10-100 , 2-30-2.50 , 3-40-3.10 ]", "Diego"));

        List<String> itemsAsString = Arrays.asList(separetedByCedilla.get(2)
                .replaceAll("\\[", "")
                .replaceAll("\\]", "")
                .replaceAll(" ", "")
                .split(","));
        assertThat(itemsAsString).containsAll(List.of("1-10-100", "2-30-2.50", "3-40-3.10"));

        List<List<String>> itemProperties = itemsAsString.stream()
                .map(it -> Arrays.asList(it.split("-")))
                .collect(Collectors.toList());

        assertThat(itemProperties).containsAll(List.of(
                List.of("1", "10", "100"),
                List.of("2", "30", "2.50"),
                List.of("3", "40", "3.10")
        ));
    }
}
