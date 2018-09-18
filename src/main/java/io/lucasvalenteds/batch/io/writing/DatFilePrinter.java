package io.lucasvalenteds.batch.io.writing;

import io.lucasvalenteds.batch.io.reading.DatFileReader;
import io.lucasvalenteds.batch.report.ReportContentFormat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatFilePrinter implements FilePrinter {

    private static final Logger LOGGER = LogManager.getLogger(DatFileReader.class);

    private final Path outputFilePath;

    public DatFilePrinter(Path outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void printIt(ReportContentFormat reportFormat) {
        try (var buffer = Files.newBufferedWriter(outputFilePath)) {
            buffer.write(reportFormat.applyFormat());
        } catch (IOException exception) {
            LOGGER.warn(exception.getMessage());
        }
    }
}
