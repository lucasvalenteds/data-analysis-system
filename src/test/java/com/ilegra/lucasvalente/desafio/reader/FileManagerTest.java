package com.ilegra.lucasvalente.desafio.reader;

import java.nio.file.Paths;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileManagerTest {

    private final FileManager resolver = FileManager.forDirectory(Paths.get("ilegra"))
        .withInputFrom("data/in").toOutputIn("data/out")
        .fromExtension(FileExtensions.DAT).toExtension(FileExtensions.DONE_DAT)
        .create();

    @DisplayName("The base path can be retrieved")
    @Test
    void testBaseName() {
        assertThat(resolver.getBasePath().toString()).isEqualTo("ilegra");
    }

    @DisplayName("The input path can be retrieved")
    @Test
    void testInputName() {
        assertThat(resolver.resolveInputFor("input-a").toString()).isEqualTo("ilegra/data/in/input-a.dat");
    }

    @DisplayName("The output path can be retrieved")
    @Test
    void testOutputName() {
        assertThat(resolver.resolveOutputFor("input-a.dat").toString()).isEqualTo("ilegra/data/out/input-a.done.dat");
    }

    @DisplayName("Classpath is the default path in case base and alternative paths are null")
    @Test
    void testNullBaseAndNullAlternative() {
        var resolver = FileManager.forDirectory(null).create();

        assertThat(resolver.getBasePath()).isNotNull();
    }
}
