package io.lucasvalenteds.das;

import io.lucasvalenteds.das.customer.Customer;
import io.lucasvalenteds.das.engine.InputOutput;
import io.lucasvalenteds.das.engine.PathConfiguration;
import io.lucasvalenteds.das.engine.Report;
import io.lucasvalenteds.das.sale.Sale;
import io.lucasvalenteds.das.salesman.Salesman;
import io.lucasvalenteds.das.customer.CustomerMapper;
import io.lucasvalenteds.das.sale.SalesDataItemMapper;
import io.lucasvalenteds.das.sale.SalesMapper;
import io.lucasvalenteds.das.salesman.SalesmanMapper;
import io.lucasvalenteds.das.customer.CustomerParser;
import io.lucasvalenteds.das.sale.SalesParser;
import io.lucasvalenteds.das.salesman.SalesmanParser;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        var paths = new PathConfiguration();
        var io = new InputOutput();
        var report = new Report(Map.ofEntries(
            Map.entry(Salesman.class, new SalesmanParser(new SalesmanMapper())),
            Map.entry(Customer.class, new CustomerParser(new CustomerMapper())),
            Map.entry(Sale.class, new SalesParser(new SalesMapper(new SalesDataItemMapper())))
        ));

        var dispose = io.watchNewFiles(paths.directoryWithDatFiles())
            .filter(paths::hasDatExtension)
            .flatMapSingle(filename ->
                io.readFileLines(paths.directoryWithDatFiles().resolve(filename))
                    .flatMap(report::generate)
                    .flatMap(text -> io.writeTextToFile(text, paths.resolveReportFilename(filename.getFileName()))))
            .doOnError(logger::error)
            .subscribe();

        //noinspection StatementWithEmptyBody
        while (!dispose.isDisposed()) ;

        logger.info("Batch stopped");
    }
}