package com.ilegra.lucasvalente;

import com.ilegra.lucasvalente.desafio.parser.DatFileParser;
import com.ilegra.lucasvalente.desafio.pojos.CustomerData;
import com.ilegra.lucasvalente.desafio.pojos.SalesData;
import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;
import com.ilegra.lucasvalente.desafio.printer.DatFilePrinter;
import com.ilegra.lucasvalente.desafio.printer.FilePrinter;
import com.ilegra.lucasvalente.desafio.reader.DatFileReader;
import com.ilegra.lucasvalente.desafio.report.Report;
import com.ilegra.lucasvalente.desafio.report.ReportFromFile;
import com.ilegra.lucasvalente.desafio.report.ReportMarkdown;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

class Main {

    public static void main(final String[] args) {
        Path basePath = Paths.get("src", "main", "resources", "production").toAbsolutePath();
        Path inputFolderPath = basePath.resolve("input");
        Path outputFolderPath = basePath.resolve("output");

        DatFileReader fileReader = new DatFileReader(inputFolderPath);
        DatFileParser fileParser = new DatFileParser();

        AtomicBoolean keepItRunning = new AtomicBoolean(true);

        try {
            while (keepItRunning.get()) {
                fileReader.listenForNewDatFiles().forEach(newFile -> {
                    Path newFileAbsolutePath = inputFolderPath.resolve(newFile.toString());

                    List<String> fileContent = fileReader.readContentOfExistingDatFile(newFileAbsolutePath.toFile())
                            .collect(Collectors.toList());

                    List<CustomerData> customers = fileParser.findCustomers(fileContent);
                    List<SalesmanData> salesmen = fileParser.findSalesmen(fileContent);
                    List<SalesData> sales = fileParser.findSales(fileContent);

                    Report report = new ReportFromFile(salesmen, customers, sales);
                    ReportMarkdown reportFormattedInMarkdown = new ReportMarkdown(report);

                    Path newFileOutputPath = outputFolderPath.resolve(newFile.toPath());
                    FilePrinter filePrinter = new DatFilePrinter(newFileOutputPath);
                    filePrinter.printIt(reportFormattedInMarkdown);
                });
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            keepItRunning.set(false);
        }
    }
}
