package com.ilegra.lucasvalente.desafio.reader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileExtensionsTest {

    @DisplayName("DAT appends extension")
    @Test
    void datDefault() {
        assertThat(FileExtensions.DAT.apply("foo")).isEqualTo("foo.dat");
    }

    @DisplayName("DAT does not duplicate the extension")
    @Test
    void datDoesNotDuplicate() {
        assertThat(FileExtensions.DAT.apply("foo.dat")).isEqualTo("foo.dat");
    }

    @DisplayName("DONE_DAT always appends extensions")
    @Test
    void doneDatDefault() {
        assertThat(FileExtensions.DONE_DAT.apply("foo")).isEqualTo("foo");
        assertThat(FileExtensions.DONE_DAT.apply("foo.dat")).isEqualTo("foo.done.dat");
    }
}
