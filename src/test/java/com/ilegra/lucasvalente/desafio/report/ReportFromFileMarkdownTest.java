package com.ilegra.lucasvalente.desafio.report;

import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class ReportFromFileMarkdownTest {

    private final Report report = mock(Report.class);

    @BeforeEach
    void setUpReportMock() {
        when(report.getAmountOfCustomers()).thenReturn(10);
        when(report.getAmountOfSalesmen()).thenReturn(2);
        when(report.getMostExpensiveSaleId()).thenReturn("08");
        when(report.getLeastProductiveSalesman()).thenReturn(new SalesmanData("08", "1234567891234", "Diego", 50000.0));
    }

    @DisplayName("It puts a star before all items in the report")
    @Test
    void testItPutsAStarBeforeAllItemsInTheReport() {
        ReportContentFormat markdown = new ReportMarkdown(report);

        assertThat(markdown.applyFormat())
                .contains("* Amount of clients in the production.input file: 10")
                .contains("* Amount of salesman in the production.input file: 2")
                .contains("* ID of the most expensive sale: 08")
                .contains("* Worst salesman ever: Diego");
    }
}
