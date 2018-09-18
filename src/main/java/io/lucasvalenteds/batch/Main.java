package io.lucasvalenteds.batch;

import io.lucasvalenteds.batch.io.config.FileExtensions;
import io.lucasvalenteds.batch.io.config.FileManager;
import io.lucasvalenteds.batch.io.reading.DatFileListener;
import io.lucasvalenteds.batch.io.reading.DatFileReader;
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
import java.nio.file.Path;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(final String[] args) {
        var fileManager = FileManager.forDirectory(Path.of(System.getProperty("user.dir")))
            .withInputFrom("data/in").toOutputIn("data/out")
            .fromExtension(FileExtensions.DAT).toExtension(FileExtensions.DONE_DAT)
            .create();

        LOGGER.info("Base path: " + fileManager.getBasePath());
        LOGGER.info("Input path: " + fileManager.getInputPath());
        LOGGER.info("Output path: " + fileManager.getOutputPath());

        var fileListener = new DatFileListener(fileManager.getInputPath(), new DatFileReader());

        fileListener.listenForNewFiles(LOGGER::throwing, (newFile, reader) -> {
            LOGGER.info("Processing " + fileManager.resolveOutputFor(newFile.getName()));

            var lines = reader.readLines(fileManager.resolveInputFor(newFile.getName()))
                .collect(Collectors.toList());

            new DatFilePrinter(fileManager.resolveOutputFor(newFile.getName()))
                .printIt(new ReportMarkdown(new ReportFromFile(
                    new SalesmanParser(new SalesmanMapper()).parseLines(lines),
                    new CustomerParser(new CustomerMapper()).parseLines(lines),
                    new SalesParser(new SalesMapper(new SalesDataItemMapper())).parseLines(lines))));
        });
    }
}
