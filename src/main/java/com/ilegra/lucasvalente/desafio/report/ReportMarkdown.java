package com.ilegra.lucasvalente.desafio.report;

public class ReportMarkdown implements ReportContentFormat {

    private final Report report;

    public ReportMarkdown(Report report) {
        this.report = report;
    }

    @Override
    public String applyFormat() {
        return "* Amount of clients in the production.input file: " + report.getAmountOfCustomers() + "\n" +
                "* Amount of salesman in the production.input file: " + report.getAmountOfSalesmen() + "\n" +
                "* ID of the most expensive sale: " + report.getMostExpensiveSaleId().orElse("") + "\n" +
                "* Worst salesman ever: " + report.getLeastProductiveSalesman().orElse("");
    }
}
