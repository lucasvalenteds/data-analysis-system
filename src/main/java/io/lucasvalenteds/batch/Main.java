package io.lucasvalenteds.batch;

import io.lucasvalenteds.batch.process.mapping.CustomerMapper;
import io.lucasvalenteds.batch.process.mapping.SalesDataItemMapper;
import io.lucasvalenteds.batch.process.mapping.SalesMapper;
import io.lucasvalenteds.batch.process.mapping.SalesmanMapper;
import io.lucasvalenteds.batch.process.parsing.CustomerParser;
import io.lucasvalenteds.batch.process.parsing.SalesParser;
import io.lucasvalenteds.batch.process.parsing.SalesmanParser;
import io.lucasvalenteds.batch.io.writing.DatFilePrinter;
import io.lucasvalenteds.batch.io.reading.DatFileReader;
import io.lucasvalenteds.batch.io.config.FileExtensions;
import io.lucasvalenteds.batch.io.config.FileManager;
import io.lucasvalenteds.batch.report.ReportFromFile;
import io.lucasvalenteds.batch.report.ReportMarkdown;
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
