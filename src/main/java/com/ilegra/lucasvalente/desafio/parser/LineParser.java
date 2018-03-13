package com.ilegra.lucasvalente.desafio.parser;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class LineParser<T> {

    private String token;

    LineParser() {
        this.token = "ç";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public abstract String getDataClassIdentifier();

    public abstract List<Optional<T>> parseLines(List<String> line);

    Stream<String[]> prepareStream(List<String> line) {
        return line.stream()
                .filter(it -> it.startsWith(getDataClassIdentifier()))
                .map(it -> it.split(getToken()));
    }
}
