package com.ilegra.lucasvalente.desafio.printer;

import com.ilegra.lucasvalente.desafio.report.Report;
import com.ilegra.lucasvalente.desafio.report.ReportMarkdown;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DatFilePrinterTest {

    private static final Path testingClasspath = Paths.get("src", "test", "resources");
    private static Path testingPath;

    private final Report report = mock(Report.class);
    private Path reportFilePath;

    @BeforeAll
    static void setupClasspath() {
        createDirectoryForTesting();
    }

    @AfterAll
    static void teardownClasspath() {
        deleteDirectoryForTesting();
    }

    @BeforeEach
    void setup() {
        createReportFile();

        when(report.getAmountOfCustomers()).thenReturn(Optional.of(5));
        when(report.getAmountOfSalesmen()).thenReturn(Optional.of(3));
        when(report.getMostExpensiveSaleId()).thenReturn(Optional.of("15"));
        when(report.getLeastProductiveSalesman()).thenReturn(Optional.of("Diego"));
    }

    @AfterEach
    void teardown() {
        deleteReportFile();
    }

    @DisplayName("It can print a report in a file")
    @Test
    void testSuccess() {
        var printer = new DatFilePrinter(reportFilePath);

        printer.printIt(new ReportMarkdown(report));

        verify(report, times(1)).getAmountOfCustomers();
        verify(report, times(1)).getAmountOfSalesmen();
        verify(report, times(1)).getMostExpensiveSaleId();
        verify(report, times(1)).getLeastProductiveSalesman();
    }

    @DisplayName("Trying to write an invalid file throws an exception")
    @Disabled("Need to learn how BufferedWriter handle errors")
    @Test
    void testFailureIO() {
        var fileReader = new DatFilePrinter(testingPath.resolve("invalid"));

        assertThatThrownBy(() -> fileReader.printIt(new ReportMarkdown(report)))
                .isInstanceOf(IOException.class);
    }

    private static void createDirectoryForTesting() {
        try {
            testingPath = Files.createTempDirectory(testingClasspath, "printer");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteDirectoryForTesting() {
        try {
            Files.deleteIfExists(testingPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createReportFile() {
        try {
            this.reportFilePath = Files.createTempFile(testingPath, "printer", ".done.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteReportFile() {
        try {
            Files.deleteIfExists(reportFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
