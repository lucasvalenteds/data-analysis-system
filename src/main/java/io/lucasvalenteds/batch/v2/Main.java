package io.lucasvalenteds.batch.v2;

import io.lucasvalenteds.batch.customer.Customer;
import io.lucasvalenteds.batch.engine.InputOutput;
import io.lucasvalenteds.batch.engine.PathConfiguration;
import io.lucasvalenteds.batch.engine.Report;
import io.lucasvalenteds.batch.sale.Sale;
import io.lucasvalenteds.batch.salesman.Salesman;
import io.lucasvalenteds.batch.customer.CustomerMapper;
import io.lucasvalenteds.batch.sale.SalesDataItemMapper;
import io.lucasvalenteds.batch.sale.SalesMapper;
import io.lucasvalenteds.batch.salesman.SalesmanMapper;
import io.lucasvalenteds.batch.customer.CustomerParser;
import io.lucasvalenteds.batch.sale.SalesParser;
import io.lucasvalenteds.batch.salesman.SalesmanParser;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
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