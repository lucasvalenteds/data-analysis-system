package io.lucasvalenteds.batch.testing;

import java.util.List;
import java.util.stream.Collectors;

public final class DatFileFixtures {

    private static final List<String> validSalesmen = List.of(
        "001ç1234567891234çDiegoç50000",
        "001ç3245678865434çRenatoç40000.99");

    private static final List<String> validCustomers = List.of(
        "002ç2345675434544345çJose da SilvaçRural",
        "002ç2345675433444345çEduardo PereiraçRural");

    private static final List<String> validSales = List.of(
        "003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego",
        "003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");

    public static final List<String> validInputFileContent = List.of(validSalesmen, validCustomers, validSales).stream()
        .flatMap(List::stream)
        .collect(Collectors.toList());
}
