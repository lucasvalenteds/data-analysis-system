package io.lucasvalenteds.batch.io.reading;

import java.io.File;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@FunctionalInterface
public interface FileListener {

    void listenForNewFiles(Consumer<Throwable> error, BiConsumer<File, FileReader> fileConsumer);
}
