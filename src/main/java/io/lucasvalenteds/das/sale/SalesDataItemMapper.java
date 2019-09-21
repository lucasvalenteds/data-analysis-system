package io.lucasvalenteds.das.sale;

import io.lucasvalenteds.das.engine.DataMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SalesDataItemMapper implements DataMapper<List<SaleItem>> {
    @Override
    public Optional<List<SaleItem>> mapStringToObject(String[] fileLines) {
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
                        .map(item -> new SaleItem(
                                item.get(0),
                                Integer.parseInt(item.get(1)),
                                Double.parseDouble(item.get(2))))
                        .collect(Collectors.toList()))
                .findFirst();
    }
}
