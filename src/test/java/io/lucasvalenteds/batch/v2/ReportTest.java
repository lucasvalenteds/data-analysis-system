package io.lucasvalenteds.batch.v2;

import io.lucasvalenteds.batch.data.CustomerData;
import io.lucasvalenteds.batch.data.SalesData;
import io.lucasvalenteds.batch.data.SalesmanData;
import io.lucasvalenteds.batch.process.mapping.CustomerMapper;
import io.lucasvalenteds.batch.process.mapping.SalesDataItemMapper;
import io.lucasvalenteds.batch.process.mapping.SalesMapper;
import io.lucasvalenteds.batch.process.mapping.SalesmanMapper;
import io.lucasvalenteds.batch.process.parsing.CustomerParser;
import io.lucasvalenteds.batch.process.parsing.LineParser;
import io.lucasvalenteds.batch.process.parsing.SalesParser;
import io.lucasvalenteds.batch.process.parsing.SalesmanParser;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReportTest {

    private final Map<Class, LineParser> parsers = Map.ofEntries(
        Map.entry(SalesmanData.class, new SalesmanParser(new SalesmanMapper())),
        Map.entry(CustomerData.class, new CustomerParser(new CustomerMapper())),
        Map.entry(SalesData.class, new SalesParser(new SalesMapper(new SalesDataItemMapper())))
    );
    private final List<String> fixtures = List.of(
        "001ç1234567891234çDiegoç50000",
        "001ç3245678865434çRenatoç40000.99",
        "002ç2345675434544345çJose da SilvaçRural",
        "002ç2345675433444345çEduardo PereiraçRural",
        "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
        "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato"
    );

    @Nested
    class Interface {

        private final Report report = new Report(parsers);

        @Test
        void testProcessLinesGroupRecordsByType() {
            report.generate(List.of("001ç1234567891234çDiegoç50000"));
            assertEquals(0, report.getCustomers().size());
            assertEquals(1, report.getSalesmen().size());
            assertEquals(0, report.getSales().size());
        }

        @Test
        void testExportReturnsTheReportWithAllInformation() {
            report.generate(fixtures);

            var text = report.export().blockingGet();

            assertTrue(text.contains("* Amount of clients: 2\n"));
            assertTrue(text.contains("* Amount of salesman: 2\n"));
            assertTrue(text.contains("* ID of the most expensive sale: 10\n"));
            assertTrue(text.contains("* Worst salesman ever: Diego"));
        }
    }

    @Nested
    class Data {

        private final Report report = new Report(parsers);

        @BeforeEach
        void setUp() {
            report.generate(fixtures);
        }

        @Test
        void testAmountOfCustomers() {
            assertEquals(2, report.getAmountOfCustomers());
        }

        @Test
        void testAmountOfSalesmen() {
            assertEquals(2, report.getAmountOfSalesmen());
        }

        @Test
        void testMostExpensiveSale() {
            assertTrue(report.getMostExpensiveSaleId().isPresent());
            assertEquals("10", report.getMostExpensiveSaleId().get());
        }

        @Test
        void testLeastProductiveSalesman() {
            assertTrue(report.getLeastProductiveSalesman().isPresent());
            assertEquals("Diego", report.getLeastProductiveSalesman().get());
        }
    }
}