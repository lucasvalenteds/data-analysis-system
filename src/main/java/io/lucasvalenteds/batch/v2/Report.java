package io.lucasvalenteds.batch.v2;

import io.lucasvalenteds.batch.data.CustomerData;
import io.lucasvalenteds.batch.data.SalesData;
import io.lucasvalenteds.batch.data.SalesmanData;
import io.lucasvalenteds.batch.process.parsing.CustomerParser;
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

    private final List<CustomerData> customers = new ArrayList<>();
    private final List<SalesmanData> salesmen = new ArrayList<>();
    private final List<SalesData> sales = new ArrayList<>();
    private final Map<Class, LineParser> parsers;

    Report(Map<Class, LineParser> parsers) {
        this.parsers = parsers;
    }

    void processLines(List<String> lines) {
        customers.clear();
        salesmen.clear();
        sales.clear();

        customers.addAll(((CustomerParser) parsers.get(CustomerData.class)).parseLines(lines));
        salesmen.addAll(((SalesmanParser) parsers.get(SalesmanData.class)).parseLines(lines));
        sales.addAll(((SalesParser) parsers.get(SalesData.class)).parseLines(lines));
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
            .map(SalesmanData::getName);
    }

    Single<String> export() {
        return Observable.just(
            "* Amount of clients in the production.input file: " + getAmountOfCustomers() + "\n",
            "* Amount of salesman in the production.input file: " + getAmountOfSalesmen() + "\n",
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