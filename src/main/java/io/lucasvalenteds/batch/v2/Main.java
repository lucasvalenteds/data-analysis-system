package io.lucasvalenteds.batch.v2;

import io.lucasvalenteds.batch.data.Customer;
import io.lucasvalenteds.batch.data.Sale;
import io.lucasvalenteds.batch.data.Salesman;
import io.lucasvalenteds.batch.process.mapping.CustomerMapper;
import io.lucasvalenteds.batch.process.mapping.SalesDataItemMapper;
import io.lucasvalenteds.batch.process.mapping.SalesMapper;
import io.lucasvalenteds.batch.process.mapping.SalesmanMapper;
import io.lucasvalenteds.batch.process.parsing.CustomerParser;
import io.lucasvalenteds.batch.process.parsing.SalesParser;
import io.lucasvalenteds.batch.process.parsing.SalesmanParser;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        var paths = new PathManager();
        var io = new FileManager();
        var watcher = new PathWatcher();
        var report = new Report(Map.ofEntries(
            Map.entry(Salesman.class, new SalesmanParser(new SalesmanMapper())),
            Map.entry(Customer.class, new CustomerParser(new CustomerMapper())),
            Map.entry(Sale.class, new SalesParser(new SalesMapper(new SalesDataItemMapper())))
        ));

        var dispose = watcher.watchNewFiles(paths.directoryWithDatFiles())
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