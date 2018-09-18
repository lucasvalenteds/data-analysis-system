package io.lucasvalenteds.batch.io.config;

import java.nio.file.Path;

public interface ExtensionResolver {

    Path getBasePath();

    Path getInputPath();

    Path getOutputPath();

    Path resolveInputFor(String filename);

    Path resolveOutputFor(String filename);
}
