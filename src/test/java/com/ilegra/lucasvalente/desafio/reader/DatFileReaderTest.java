package com.ilegra.lucasvalente.desafio.reader;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DatFileReaderTest {

    private static final Path testingFolderDirectory = Paths.get("src", "main", "resources", "testing", "input");
    private static DatFileReader datFileReader;

    @BeforeAll
    static void createInstanceOfTheTestSubject() {
        datFileReader = new DatFileReader(testingFolderDirectory.toAbsolutePath());
    }

    @DisplayName("It can detect newly created .dat files in a given path")
    @Disabled("Concurrency is hard to test")
    @Test
    void testItCanObserveNewFilesBeenCreated() throws IOException, InterruptedException {
        Path newFileCreatedForTheTest = Files.createTempFile(testingFolderDirectory, "new-input-file", ".dat");
        ArrayList<File> filesDetectedByTheListener = new ArrayList<>();

        datFileReader.listenForNewDatFiles(filesDetectedByTheListener::add);
        Thread.sleep(1000); // Bad Smell

        assertThat(filesDetectedByTheListener).hasSize(1);
        Files.deleteIfExists(newFileCreatedForTheTest);
    }

    @DisplayName("It can provide the content of a .dat file in a given path")
    @Test
    void testItCanReadADatFile() {
        Path pathOfFileToRead = testingFolderDirectory.resolve("input-a.dat").toAbsolutePath();

        List<String> linesOfTheFile = datFileReader
            .readContentOfExistingDatFile(pathOfFileToRead.toFile())
            .collect(Collectors.toList());

        assertThat(linesOfTheFile)
            .hasSize(6)
            .containsAll(List.of(
                "001ç1234567891234çDiegoç50000",
                "001ç3245678865434çRenatoç40000.99",
                "002ç2345675434544345çJosedaSilvaçRural",
                "002ç2345675433444345çEduardoPereiraçRural",
                "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
                "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato"));
    }

    @DisplayName("Trying to read the content of an invalid file returns an empty Stream")
    @Disabled(value = "Disabled due to lack of knowledge of how to test the exception")
    @Test
    void testTryingToReadAnInvalidFileContentReturnsEmptyStream() {
        Path invalidPath = Paths.get("???");

        DatFileReader fileReader = new DatFileReader(invalidPath);

        assertThat(fileReader.readContentOfExistingDatFile(invalidPath.toFile())).isEqualTo(Stream.empty());
    }


}
