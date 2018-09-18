package io.lucasvalenteds.batch.app;

import com.nurkiewicz.asyncretry.AsyncRetryExecutor;
import io.lucasvalenteds.batch.io.config.ExtensionResolver;
import io.lucasvalenteds.batch.io.reading.FileListener;
import io.lucasvalenteds.batch.io.writing.DatFilePrinter;
import io.lucasvalenteds.batch.process.mapping.CustomerMapper;
import io.lucasvalenteds.batch.process.mapping.SalesDataItemMapper;
import io.lucasvalenteds.batch.process.mapping.SalesMapper;
import io.lucasvalenteds.batch.process.mapping.SalesmanMapper;
import io.lucasvalenteds.batch.process.parsing.CustomerParser;
import io.lucasvalenteds.batch.process.parsing.SalesParser;
import io.lucasvalenteds.batch.process.parsing.SalesmanParser;
import io.lucasvalenteds.batch.report.ReportFromFile;
import io.lucasvalenteds.batch.report.ReportMarkdown;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {

    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private final SalesmanParser salesmanParser = new SalesmanParser(new SalesmanMapper());
    private final CustomerParser customerParser = new CustomerParser(new CustomerMapper());
    private final SalesParser salesParser = new SalesParser(new SalesMapper(new SalesDataItemMapper()));

    private final ExtensionResolver fileManager;
    private final FileListener fileListener;

    public Application(ExtensionResolver fileManager, FileListener fileListener) {
        this.fileManager = fileManager;
        this.fileListener = fileListener;

        LOGGER.info("Base path: " + fileManager.getBasePath());
        LOGGER.info("Input path: " + fileManager.getInputPath());
        LOGGER.info("Output path: " + fileManager.getOutputPath());
    }

    public CompletableFuture<Void> start() {
        return new AsyncRetryExecutor(executor)
            .getWithRetry(x -> {
                fileListener.listenForNewFiles(LOGGER::throwing, (newFile, reader) -> {
                    LOGGER.info("Processing " + fileManager.resolveOutputFor(newFile.getName()));

                    var lines = reader.readLines(fileManager.resolveInputFor(newFile.getName()))
                        .collect(Collectors.toList());

                    new DatFilePrinter(fileManager.resolveOutputFor(newFile.getName()))
                        .printIt(new ReportMarkdown(new ReportFromFile(
                            salesmanParser.parseLines(lines),
                            customerParser.parseLines(lines),
                            salesParser.parseLines(lines))));
                });
                return null;
            });
    }
}
