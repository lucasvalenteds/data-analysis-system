package io.lucasvalenteds.batch.v2;

import io.lucasvalenteds.batch.customer.Customer;
import io.lucasvalenteds.batch.sale.Sale;
import io.lucasvalenteds.batch.salesman.Salesman;
import io.lucasvalenteds.batch.customer.CustomerMapper;
import io.lucasvalenteds.batch.sale.SalesDataItemMapper;
import io.lucasvalenteds.batch.sale.SalesMapper;
import io.lucasvalenteds.batch.salesman.SalesmanMapper;
import io.lucasvalenteds.batch.customer.CustomerParser;
import io.lucasvalenteds.batch.engine.LineParser;
import io.lucasvalenteds.batch.sale.SalesParser;
import io.lucasvalenteds.batch.salesman.SalesmanParser;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ReportTest {

    private final Map<Class, LineParser> parsers = Map.ofEntries(
        Map.entry(Salesman.class, new SalesmanParser(new SalesmanMapper())),
        Map.entry(Customer.class, new CustomerParser(new CustomerMapper())),
        Map.entry(Sale.class, new SalesParser(new SalesMapper(new SalesDataItemMapper())))
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