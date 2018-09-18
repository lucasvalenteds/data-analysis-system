package io.lucasvalenteds.batch.process.mapping;

import io.lucasvalenteds.batch.data.SalesData;
import io.lucasvalenteds.batch.data.SalesDataItem;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SalesMapper implements DataMapper<SalesData> {

    private final DataMapper<List<SalesDataItem>> itemsMapper;

    public SalesMapper(DataMapper<List<SalesDataItem>> itemsMapper) {
        this.itemsMapper = Optional.of(itemsMapper).orElse(new SalesDataItemMapper());
    }

    @Override
    public Optional<SalesData> mapStringToObject(String[] fileLines) {
        return Stream.of(Arrays.asList(fileLines))
                .map(tokens -> Optional
                        .of(new SalesData(
                                tokens.get(0),
                                tokens.get(1),
                                itemsMapper.mapStringToObject(new String[]{tokens.get(2)}).orElseGet(List::of),
                                tokens.get(3)))
                        .orElseThrow(IllegalArgumentException::new))
                .findFirst();
    }
}
