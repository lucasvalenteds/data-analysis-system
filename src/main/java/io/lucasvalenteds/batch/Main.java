package io.lucasvalenteds.batch;

import io.lucasvalenteds.batch.io.config.FileExtensions;
import io.lucasvalenteds.batch.io.config.FileManager;
import io.lucasvalenteds.batch.io.reading.DatFileListener;
import io.lucasvalenteds.batch.io.reading.DatFileReader;
import io.lucasvalenteds.batch.app.Application;
import java.nio.file.Path;

class Main {

    public static void main(final String[] args) {
        var fileManager = FileManager.forDirectory(Path.of(System.getProperty("user.dir")))
            .withInputFrom("data/in").toOutputIn("data/out")
            .fromExtension(FileExtensions.DAT).toExtension(FileExtensions.DONE_DAT)
            .create();

        var fileListener = new DatFileListener(fileManager.getInputPath(), new DatFileReader());

        var application = new Application(fileManager, fileListener);
        application.start().join();
    }
}
