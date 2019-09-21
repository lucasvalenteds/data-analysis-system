package io.lucasvalenteds.batch.sale;

import io.lucasvalenteds.batch.process.mapping.DataMapper;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SalesMapper implements DataMapper<Sale> {

    private final DataMapper<List<SaleItem>> itemsMapper;

    public SalesMapper(DataMapper<List<SaleItem>> itemsMapper) {
        this.itemsMapper = Optional.of(itemsMapper).orElse(new SalesDataItemMapper());
    }

    @Override
    public Optional<Sale> mapStringToObject(String[] fileLines) {
        return Stream.of(Arrays.asList(fileLines))
                .map(tokens -> Optional
                        .of(new Sale(
                                tokens.get(0),
                                tokens.get(1),
                                itemsMapper.mapStringToObject(new String[]{tokens.get(2)}).orElseGet(List::of),
                                tokens.get(3)))
                        .orElseThrow(IllegalArgumentException::new))
                .findFirst();
    }
}
