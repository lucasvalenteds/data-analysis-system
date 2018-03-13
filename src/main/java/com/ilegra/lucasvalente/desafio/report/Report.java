package com.ilegra.lucasvalente.desafio.report;

import com.ilegra.lucasvalente.desafio.pojos.CustomerData;
import com.ilegra.lucasvalente.desafio.pojos.SalesData;
import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;
import org.javatuples.Pair;

import java.util.Comparator;
import java.util.List;

public class Report {

    private final List<SalesmanData> salesmenList;
    private final List<CustomerData> customersList;
    private final List<SalesData> salesList;

    public Report(List<SalesmanData> salesmenList, List<CustomerData> customersList, List<SalesData> salesList) {
        this.salesmenList = salesmenList;
        this.customersList = customersList;
        this.salesList = salesList;
    }

    public int getAmountOfCustomers() {
        return customersList.size();
    }

    public int getAmountOfSalesmen() {
        return salesmenList.size();
    }

    public String getMostExpensiveSaleId() {
        return salesList.stream()
                .map(sale -> {
                    double finalPriceOfThisSale = sale.getItemsSold().stream()
                            .mapToDouble(it -> it.getQuantity() * it.getPrice())
                            .sum();

                    return new Pair<>(sale.getCode(), finalPriceOfThisSale);
                })
                .max(Comparator.comparing(Pair::getValue1))
                .get()
                .getValue0();
    }

    public SalesmanData getLeastProductiveSalesman() {
        return salesmenList.stream()
                .map(salesman -> {
                    double totalEarnedByThisSalesman = salesList.stream()
                            .filter(it -> it.getSalesmanName().equals(salesman.getName()))
                            .flatMap(it -> it.getItemsSold().stream())
                            .mapToDouble(it -> it.getQuantity() * it.getPrice())
                            .sum();

                    return new Pair<>(salesman, totalEarnedByThisSalesman);
                })
                .max(Comparator.comparing(Pair::getValue1))
                .get()
                .getValue0();
    }
}
