package com.ilegra.lucasvalente.desafio.printer;

import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;
import com.ilegra.lucasvalente.desafio.report.Report;
import com.ilegra.lucasvalente.desafio.report.ReportMarkdown;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DatFilePrinterTest {

    private final Report report = mock(Report.class);

    @BeforeEach
    void setUpReportMock() {
        when(report.getAmountOfCustomers()).thenReturn(5);
        when(report.getAmountOfSalesmen()).thenReturn(3);
        when(report.getMostExpensiveSaleId()).thenReturn(Optional.of("15"));
        when(report.getLeastProductiveSalesman()).thenReturn(Optional.of("Diego"));
    }

    @DisplayName("It can print a report in a file")
    @Test
    void testItCanPrintAReportInAFile() {
        Path reportOutputPath = Paths.get("src", "main", "resources", "testing", "output", "report.done.dat");

        FilePrinter printer = new DatFilePrinter(reportOutputPath.toAbsolutePath());

        printer.printIt(new ReportMarkdown(report));
    }

}
