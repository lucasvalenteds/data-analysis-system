package io.lucasvalenteds.batch.v2;

import io.lucasvalenteds.batch.customer.Customer;
import io.lucasvalenteds.batch.data.Sale;
import io.lucasvalenteds.batch.data.Salesman;
import io.lucasvalenteds.batch.customer.CustomerParser;
import io.lucasvalenteds.batch.process.parsing.LineParser;
import io.lucasvalenteds.batch.process.parsing.SalesParser;
import io.lucasvalenteds.batch.process.parsing.SalesmanParser;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

class Report {

    private final Logger logger = LogManager.getLogger(Report.class);

    private final List<Customer> customers = new ArrayList<>();
    private final List<Salesman> salesmen = new ArrayList<>();
    private final List<Sale> sales = new ArrayList<>();
    private final Map<Class, LineParser> parsers;

    Report(Map<Class, LineParser> parsers) {
        this.parsers = parsers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Sale> getSales() {
        return sales;
    }

    public List<Salesman> getSalesmen() {
        return salesmen;
    }

    void processLines(List<String> lines) {
        customers.clear();
        salesmen.clear();
        sales.clear();

        customers.addAll(((CustomerParser) parsers.get(Customer.class)).parseLines(lines));
        salesmen.addAll(((SalesmanParser) parsers.get(Salesman.class)).parseLines(lines));
        sales.addAll(((SalesParser) parsers.get(Sale.class)).parseLines(lines));
    }

    int getAmountOfCustomers() {
        return customers.size();
    }

    int getAmountOfSalesmen() {
        return salesmen.size();
    }

    Optional<String> getMostExpensiveSaleId() {
        return sales.stream()
            .map(sale -> {
                double finalPriceOfThisSale = sale.getItemsSold().stream()
                    .mapToDouble(it -> it.getQuantity() * it.getPrice())
                    .sum();

                return new Pair<>(sale.getCode(), finalPriceOfThisSale);
            })
            .max(Comparator.comparing(Pair::getValue1))
            .map(Pair::getValue0);
    }

    Optional<String> getLeastProductiveSalesman() {
        return salesmen.stream()
            .map(salesman -> {
                double totalEarnedByThisSalesman = sales.stream()
                    .filter(it -> it.getSalesmanName().equals(salesman.getName()))
                    .flatMap(it -> it.getItemsSold().stream())
                    .mapToDouble(it -> it.getQuantity() * it.getPrice())
                    .sum();

                return new Pair<>(salesman, totalEarnedByThisSalesman);
            })
            .max(Comparator.comparing(Pair::getValue1))
            .map(Pair::getValue0)
            .map(Salesman::getName);
    }

    Single<String> export() {
        return Observable.just(
            "* Amount of clients: " + getAmountOfCustomers() + "\n",
            "* Amount of salesman: " + getAmountOfSalesmen() + "\n",
            "* ID of the most expensive sale: " + getMostExpensiveSaleId().orElse("") + "\n",
            "* Worst salesman ever: " + getLeastProductiveSalesman().orElse("")
        )
            .collect(StringBuilder::new, StringBuilder::append)
            .map(StringBuilder::toString);
    }

    Single<String> generate(List<String> lines) {
        logger.info("Report.generate: " + lines.size() + " records processed.");
        processLines(lines);
        return export();
    }
}