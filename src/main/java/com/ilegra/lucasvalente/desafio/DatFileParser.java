package com.ilegra.lucasvalente.desafio;


import com.ilegra.lucasvalente.desafio.pojos.CustomerData;
import com.ilegra.lucasvalente.desafio.pojos.SalesData;
import com.ilegra.lucasvalente.desafio.pojos.SalesDataItem;
import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class DatFileParser {

    public List<SalesmanData> findSalesmen(List<String> inputDataAsString) {
        return inputDataAsString.stream()
                .filter(it -> it.startsWith("001"))
                .map(it -> it.split("ç"))
                .map(splitted -> {
                    String id = splitted[0];
                    String cpf = splitted[1];
                    String name = splitted[2];
                    double salary = Double.valueOf(splitted[3]);

                    return new SalesmanData(id, cpf, name, salary);
                })
                .collect(Collectors.toList());
    }

    public List<CustomerData> findCustomers(List<String> inputDataAsString) {
        return inputDataAsString.stream()
                .filter(it -> it.startsWith("002"))
                .map(it -> it.split("ç"))
                .map(splitted -> {
                    String id = splitted[0];
                    String cnpj = splitted[1];
                    String name = splitted[2];
                    String businessArea = splitted[3];

                    return new CustomerData(id, cnpj, name, businessArea);
                })
                .collect(Collectors.toList());
    }

    public List<SalesData> findSales(List<String> inputDataAsString) {
        return inputDataAsString.stream()
                .filter(it -> it.startsWith("003"))
                .map(it -> Arrays.asList(it.split("ç")))
                .map(saleInfoSplited -> {
                    String id = saleInfoSplited.get(0);
                    String code = saleInfoSplited.get(1);
                    String name = saleInfoSplited.get(3);

                    // Items parsing
                    List<String> itemsAsString = Arrays.asList(saleInfoSplited.get(2)
                            .replaceAll("\\[", "")
                            .replaceAll("\\]", "")
                            .replaceAll(" ", "")
                            .split(","));

                    List<List<String>> itemProperties = itemsAsString.stream()
                            .map(it -> Arrays.asList(it.split("-")))
                            .collect(Collectors.toList());

                    List<SalesDataItem> collect = itemProperties.stream()
                            .map(item -> {
                                String sId = item.get(0);
                                int iQuantity = Integer.parseInt(item.get(1));
                                double dPrice = Double.valueOf(item.get(2));

                                return new SalesDataItem(sId, iQuantity, dPrice);
                            })
                            .collect(Collectors.toList());

                    return new SalesData(id, code, collect, name);
                })
                .collect(Collectors.toList());
    }
}
