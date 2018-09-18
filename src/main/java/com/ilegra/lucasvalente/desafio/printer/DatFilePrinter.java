package com.ilegra.lucasvalente.desafio.printer;

import com.ilegra.lucasvalente.desafio.report.ReportContentFormat;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DatFilePrinter implements FilePrinter {

    private final Path outputFilePath;

    public DatFilePrinter(Path outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void printIt(ReportContentFormat reportFormat) {
        try (var buffer = Files.newBufferedWriter(outputFilePath)) {
            buffer.write(reportFormat.applyFormat());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
