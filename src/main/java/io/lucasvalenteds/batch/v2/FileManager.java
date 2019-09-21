package io.lucasvalenteds.batch.v2;

import io.reactivex.Single;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class FileManager {

    private final Logger logger = LogManager.getLogger(FileManager.class);

    Single<List<String>> readFileLines(Path path) {
        return Single.create(emitter -> {
            try (var lines = Files.newBufferedReader(path).lines()) {
                logger.info("onNext.readFileLines: " + path.toString());
                emitter.onSuccess(lines.collect(Collectors.toList()));
            } catch (IOException exception) {
                logger.error("onError.readFileLines: " + exception);
                emitter.onError(exception);
            }
        });
    }

    Single<String> writeTextToFile(String report, Path path) {
        return Single.create(emitter -> {
            try (var buffer = Files.newBufferedWriter(path)) {
                buffer.write(report);
                logger.info("onNext.writeTextToFile: " + path.toString());
                emitter.onSuccess(report);
            } catch (IOException exception) {
                logger.error("onError.writeTextToFile: " + exception);
                emitter.onError(exception);
            }
        });
    }
}