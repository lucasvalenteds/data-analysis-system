package io.lucasvalenteds.batch.engine;

import io.reactivex.Observable;
import io.reactivex.Single;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InputOutput {

    private final Logger logger = LogManager.getLogger(InputOutput.class);

    public Single<List<String>> readFileLines(Path path) {
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

    public Single<String> writeTextToFile(String report, Path path) {
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

    public Observable<Path> watchNewFiles(Path directoryToWatch) {
        logger.info("Watching " + directoryToWatch.toString());

        return Observable.create(emitter -> {
            try (WatchService watcher = directoryToWatch.getFileSystem().newWatchService()) {
                directoryToWatch.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
                WatchKey key;
                do {
                    key = watcher.take();
                    key.pollEvents().stream()
                        .map(WatchEvent::context)
                        .map(Object::toString)
                        .map(Path::of)
                        .peek(path -> logger.info("onNext.watchNewFiles: " + path.toString()))
                        .forEach(emitter::onNext);
                } while (key.reset() && !emitter.isDisposed());
            } catch (IOException | InterruptedException exception) {
                logger.error("onError.watchNewFiles: ", exception);
                emitter.onError(exception);
            } finally {
                logger.info("onComplete.watchNewFiles");
                emitter.onComplete();
            }
        });
    }
}