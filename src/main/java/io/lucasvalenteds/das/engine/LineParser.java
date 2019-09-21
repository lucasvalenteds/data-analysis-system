package io.lucasvalenteds.das.engine;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class LineParser<T> {

    private final DataMapper<T> mapper;
    private String token;

    public LineParser(DataMapper<T> mapper) {
        this.mapper = mapper;
    }

    public String getToken() {
        return Optional.ofNullable(token).orElse("ç");
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<T> parseLines(List<String> line) {
        return line.stream()
            .filter(it -> it.startsWith(getDataClassIdentifier()))
            .map(it -> it.split(getToken()))
            .map(mapper::mapStringToObject)
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
    }

    public abstract String getDataClassIdentifier();
}
