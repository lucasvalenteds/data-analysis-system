package io.lucasvalenteds.batch.io.config;

@FunctionalInterface
public interface FileExtension {

    String apply(String extension);
}
