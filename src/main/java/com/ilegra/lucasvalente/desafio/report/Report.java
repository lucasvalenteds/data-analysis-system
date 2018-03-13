package com.ilegra.lucasvalente.desafio.report;

import java.util.Optional;

public interface Report {

    int getAmountOfCustomers();

    int getAmountOfSalesmen();

    Optional<String> getMostExpensiveSaleId();

    Optional<String> getLeastProductiveSalesman();
}
