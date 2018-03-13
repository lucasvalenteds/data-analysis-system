package com.ilegra.lucasvalente.desafio.printer;

import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;
import com.ilegra.lucasvalente.desafio.report.Report;
import com.ilegra.lucasvalente.desafio.report.ReportMarkdown;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

        verify(report, times(1)).getAmountOfCustomers();
        verify(report, times(1)).getAmountOfSalesmen();
        verify(report, times(1)).getMostExpensiveSaleId();
        verify(report, times(1)).getLeastProductiveSalesman();
    }

    @DisplayName("Trying to write an invalid file throws an exception")
    @Disabled(value = "Disabled due to lack of knowledge of how to test the exception")
    @Test
    void testTryingToWriteAnInvalidFileThrowsAnException() {
        Path invalidPath = Paths.get("???");

        FilePrinter fileReader = new DatFilePrinter(invalidPath);

        assertThatThrownBy(() -> fileReader.printIt(new ReportMarkdown(report)))
                .isInstanceOf(IOException.class);
    }
}
