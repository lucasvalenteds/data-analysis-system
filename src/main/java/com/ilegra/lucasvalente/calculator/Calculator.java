package com.ilegra.lucasvalente.calculator;

import java.util.List;

class Calculator {

    double sum(final List<Double> numbers) {
        return numbers.stream()
                .reduce(0.0, (sum, number) -> sum + number);
    }
}
