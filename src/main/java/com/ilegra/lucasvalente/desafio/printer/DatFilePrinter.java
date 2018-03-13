package com.ilegra.lucasvalente.desafio.printer;

import com.ilegra.lucasvalente.desafio.report.ReportContentFormat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class DatFilePrinter implements FilePrinter {

    private BufferedWriter bufferedWriter;

    public DatFilePrinter(Path outputFilePath) {
        try {
            bufferedWriter = new BufferedWriter(new PrintWriter(outputFilePath.toFile()));
        } catch (IOException ex) {
            // TODO:
        }
    }

    @Override
    public void printIt(ReportContentFormat reportFormat) {
        try {
            bufferedWriter.write(reportFormat.applyFormat());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException ex) {
            // TODO:
        }
    }
}
