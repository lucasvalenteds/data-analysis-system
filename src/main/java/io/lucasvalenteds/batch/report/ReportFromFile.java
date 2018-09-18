package io.lucasvalenteds.batch.report;

import io.lucasvalenteds.batch.data.CustomerData;
import io.lucasvalenteds.batch.data.SalesData;
import io.lucasvalenteds.batch.data.SalesmanData;
import org.javatuples.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class ReportFromFile implements Report {

    private final List<SalesmanData> salesmenList;
    private final List<CustomerData> customersList;
    private final List<SalesData> salesList;

    public ReportFromFile(List<SalesmanData> salesmenList, List<CustomerData> customersList, List<SalesData> salesList) {
        this.salesmenList = salesmenList;
        this.customersList = customersList;
        this.salesList = salesList;
    }

    @Override
    public Optional<Integer> getAmountOfCustomers() {
        return Optional.of(customersList.size());
    }

    @Override
    public Optional<Integer> getAmountOfSalesmen() {
        return Optional.of(salesmenList.size());
    }

    @Override
    public Optional<String> getMostExpensiveSaleId() {
        return salesList.stream()
            .map(sale -> {
                double finalPriceOfThisSale = sale.getItemsSold().stream()
                    .mapToDouble(it -> it.getQuantity() * it.getPrice())
                    .sum();

                return new Pair<>(sale.getCode(), finalPriceOfThisSale);
            })
            .max(Comparator.comparing(Pair::getValue1))
            .map(Pair::getValue0);
    }

    @Override
    public Optional<String> getLeastProductiveSalesman() {
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
            .map(Pair::getValue0)
            .map(SalesmanData::getName);
    }
}
