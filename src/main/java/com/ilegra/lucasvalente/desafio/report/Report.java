package com.ilegra.lucasvalente.desafio.report;

import java.util.Optional;

public interface Report {

    Optional<Integer> getAmountOfCustomers();

    Optional<Integer> getAmountOfSalesmen();

    Optional<String> getMostExpensiveSaleId();

    Optional<String> getLeastProductiveSalesman();
}
