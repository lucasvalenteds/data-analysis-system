package io.lucasvalenteds.batch.io.config;

public class FileExtensions {

    public static final FileExtension DAT = filename ->
        filename.endsWith(".dat") ? filename : filename.concat(".dat");

    public static final FileExtension DONE_DAT = filename ->
        filename.replaceFirst(".dat", ".done.dat");

    public static final FileExtension EMPTY = filename -> "";
}
