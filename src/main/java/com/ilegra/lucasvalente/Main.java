package com.ilegra.lucasvalente;

import com.ilegra.lucasvalente.desafio.mappers.CustomerMapper;
import com.ilegra.lucasvalente.desafio.mappers.SalesDataItemMapper;
import com.ilegra.lucasvalente.desafio.mappers.SalesMapper;
import com.ilegra.lucasvalente.desafio.mappers.SalesmanMapper;
import com.ilegra.lucasvalente.desafio.parser.CustomerParser;
import com.ilegra.lucasvalente.desafio.parser.LineParser;
import com.ilegra.lucasvalente.desafio.parser.SalesParser;
import com.ilegra.lucasvalente.desafio.parser.SalesmanParser;
import com.ilegra.lucasvalente.desafio.pojos.CustomerData;
import com.ilegra.lucasvalente.desafio.pojos.SalesData;
import com.ilegra.lucasvalente.desafio.pojos.SalesmanData;
import com.ilegra.lucasvalente.desafio.printer.DatFilePrinter;
import com.ilegra.lucasvalente.desafio.printer.FilePrinter;
import com.ilegra.lucasvalente.desafio.reader.DatFileReader;
import com.ilegra.lucasvalente.desafio.report.Report;
import com.ilegra.lucasvalente.desafio.report.ReportContentFormat;
import com.ilegra.lucasvalente.desafio.report.ReportFromFile;
import com.ilegra.lucasvalente.desafio.report.ReportMarkdown;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

class Main {

    private static Path basePath = Paths.get("src", "main", "resources", "production").toAbsolutePath();
    private static Path inputFolderPath = basePath.resolve("data").resolve("in");
    private static Path outputFolderPath = basePath.resolve("data").resolve("out");

    private static String homepath = Optional.ofNullable(System.getenv("HOMEPATH")).orElse(inputFolderPath.toString());

    public static void main(final String[] args) {
        DatFileReader fileReader = new DatFileReader(Paths.get(homepath));

        LineParser<CustomerData> customerParser = new CustomerParser(new CustomerMapper());
        LineParser<SalesmanData> salesmanParser = new SalesmanParser(new SalesmanMapper());
        LineParser<SalesData> salesParser = new SalesParser(new SalesMapper(new SalesDataItemMapper()));

        AtomicBoolean keepItRunning = new AtomicBoolean(true);

        try {
            while (keepItRunning.get()) {
                fileReader.listenForNewDatFiles().forEach(newFile -> {
                    Path newFileAbsolutePath = inputFolderPath.resolve(newFile.toString());

                    List<String> fileContent = fileReader.readContentOfExistingDatFile(newFileAbsolutePath.toFile())
                            .collect(Collectors.toList());

                    ReportContentFormat reportFormattedInMarkdown = new ReportMarkdown(new ReportFromFile(
                        salesmanParser.parseLines(fileContent),
                        customerParser.parseLines(fileContent),
                        salesParser.parseLines(fileContent)));

                    Path newFileOutputPath = outputFolderPath.resolve(newFile.getName().replace(".dat", ".done.dat"));
                    System.out.println(newFileOutputPath);
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
