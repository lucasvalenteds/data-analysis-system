package com.ilegra.lucasvalente;

import com.ilegra.lucasvalente.desafio.mappers.CustomerMapper;
import com.ilegra.lucasvalente.desafio.mappers.SalesDataItemMapper;
import com.ilegra.lucasvalente.desafio.mappers.SalesMapper;
import com.ilegra.lucasvalente.desafio.mappers.SalesmanMapper;
import com.ilegra.lucasvalente.desafio.parser.CustomerParser;
import com.ilegra.lucasvalente.desafio.parser.SalesParser;
import com.ilegra.lucasvalente.desafio.parser.SalesmanParser;
import com.ilegra.lucasvalente.desafio.printer.DatFilePrinter;
import com.ilegra.lucasvalente.desafio.reader.DatFileReader;
import com.ilegra.lucasvalente.desafio.reader.FileExtensions;
import com.ilegra.lucasvalente.desafio.reader.FileManager;
import com.ilegra.lucasvalente.desafio.report.ReportFromFile;
import com.ilegra.lucasvalente.desafio.report.ReportMarkdown;
import java.nio.file.Path;
import java.util.stream.Collectors;

class Main {

    public static void main(final String[] args) {
        var fileManager = FileManager.forDirectory(Path.of(System.getProperty("user.dir")))
            .withInputFrom("data/in").toOutputIn("data/out")
            .fromExtension(FileExtensions.DAT).toExtension(FileExtensions.DONE_DAT)
            .create();

        var fileReader = new DatFileReader(fileManager.getInputPath());

        fileReader.listenForNewDatFiles(newFile -> {
            var fileContent = fileReader.readContentOfExistingDatFile(fileManager.resolveInputFor(newFile.getName()))
                .collect(Collectors.toList());

            System.out.println(fileManager.resolveOutputFor(newFile.getName()));

            new DatFilePrinter(fileManager.resolveOutputFor(newFile.getName()))
                .printIt(new ReportMarkdown(new ReportFromFile(
                    new SalesmanParser(new SalesmanMapper()).parseLines(fileContent),
                    new CustomerParser(new CustomerMapper()).parseLines(fileContent),
                    new SalesParser(new SalesMapper(new SalesDataItemMapper())).parseLines(fileContent))));
        });
    }
}
