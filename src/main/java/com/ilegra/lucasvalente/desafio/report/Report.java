package com.ilegra.lucasvalente.desafio.report;

import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;

public interface Report {

    int getAmountOfCustomers();

    int getAmountOfSalesmen();

    String getMostExpensiveSaleId();

    SalesmanData getLeastProductiveSalesman();
}
