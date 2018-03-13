package com.ilegra.lucasvalente.desafio.mappers;

import com.ilegra.lucasvalente.desafio.pojos.SalesDataItem;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SalesDataItemMapper implements DataMapper<List<SalesDataItem>> {
    @Override
    public Optional<List<SalesDataItem>> mapStringToObject(String[] fileLines) {
        return Stream.of(fileLines)
                .map(line -> line
                        .replaceAll("\\[", "")
                        .replaceAll("]", "")
                        .replaceAll(" ", "")
                        .split(","))
                .map(Arrays::asList)
                .map(line -> line.stream()
                        .map(it -> Arrays.asList(it.split("-")))
                        .collect(Collectors.toList()))
                .map(tokens -> tokens.stream()
                        .map(item -> new SalesDataItem(
                                item.get(0),
                                Integer.parseInt(item.get(1)),
                                Double.valueOf(item.get(2))))
                        .collect(Collectors.toList()))
                .findFirst();
    }
}
