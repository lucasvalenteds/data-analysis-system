package io.lucasvalenteds.batch.v2;

import io.reactivex.Observable;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

class PathWatcher {

    private final Logger logger = LogManager.getLogger(PathWatcher.class);

    Observable<Path> watchNewFiles(Path directoryToWatch) {
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