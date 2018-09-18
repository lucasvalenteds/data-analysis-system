package com.ilegra.lucasvalente.desafio.reader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class FileManager implements ExtensionResolver {

    private final Path baseFilesPath;
    private final Path inputFilesPath;
    private final Path outputFilesPath;
    private final FileExtension inputExtension;
    private final FileExtension outputExtension;

    private FileManager(
        Path baseFilesPath,
        Path inputFilesPath,
        Path outputFilesPath,
        FileExtension inputExtension,
        FileExtension outputExtension) {
        this.baseFilesPath = baseFilesPath;
        this.inputFilesPath = inputFilesPath;
        this.outputFilesPath = outputFilesPath;
        this.inputExtension = inputExtension;
        this.outputExtension = outputExtension;
    }

    public static FileManager.Builder forDirectory(Path directory) {
        return new FileManager.Builder().forDirectory(directory);
    }

    @Override
    public Path getBasePath() {
        return baseFilesPath;
    }

    @Override
    public Path getInputPath() {
        return inputFilesPath;
    }

    @Override
    public Path getOutputPath() {
        return outputFilesPath;
    }

    @Override
    public Path resolveInputFor(String filename) {
        return inputFilesPath.resolve(inputExtension.apply(filename));
    }

    @Override
    public Path resolveOutputFor(String filename) {
        return outputFilesPath.resolve(outputExtension.apply(filename));
    }

    public static class Builder {

        private static final Path DEFAULT_ALTERNATIVE_DIRECTORY = Paths.get("src", "main", "resources");

        private Path directory;
        private String inputFilesPath;
        private String outputFilesPath;
        private FileExtension inputExtension;
        private FileExtension outputExtension;

        private Builder() {}

        private Builder forDirectory(Path directory) {
            this.directory = directory;
            return this;
        }

        public Builder fromExtension(FileExtension extensionResolver) {
            this.inputExtension = extensionResolver;
            return this;
        }

        public Builder toExtension(FileExtension extensionResolver) {
            this.outputExtension = extensionResolver;
            return this;
        }

        public Builder withInputFrom(String input) {
            this.inputFilesPath = input;
            return this;
        }

        public Builder toOutputIn(String output) {
            this.outputFilesPath = output;
            return this;
        }

        public FileManager create() {
            Path basePath = Optional.ofNullable(directory).orElse(DEFAULT_ALTERNATIVE_DIRECTORY);

            return new FileManager(
                basePath,
                basePath.resolve(Optional.ofNullable(inputFilesPath).orElse("")),
                basePath.resolve(Optional.ofNullable(outputFilesPath).orElse("")),
                inputExtension,
                outputExtension);
        }
    }
}
