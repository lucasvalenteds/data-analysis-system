package com.ilegra.lucasvalente.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

    private final Calculator calculator = new Calculator();

    private static Stream<Arguments> testSumArguments() {
        return Stream.of(
                Arguments.of(2.0, 2.0, 4.0),
                Arguments.of(0.0, 0.0, 0.0),
                Arguments.of(-2.0, -3.0, -5.0),
                Arguments.of(0.123, 321.0, 321.123),
                Arguments.of(0.34, -0.34, 0.0));
    }

    @DisplayName(value = "It can sum numbers")
    @ParameterizedTest(name = "The sum of {0} and {1} should be {2}")
    @MethodSource(value = "testSumArguments")
    void testSum(double firstNumber, double secondNumber, double expectedResult) {
        assertThat(calculator.sum(List.of(firstNumber, secondNumber))).isEqualTo(expectedResult);
    }

    @Test
    @DisplayName("Calling sum method with zero parameters return zero")
    void testPassingZeroNumbersReturnZero() {
        assertThat(calculator.sum(List.of(0.0))).isEqualTo(0);
    }

    @Test
    @DisplayName("It can sum an infinite amount of numbers")
    void testSumManyNumbers() {
        assertThat(calculator.sum(List.of(0.0, 1.0, 2.0, 3.0, 4.0, 5.0))).isEqualTo(15.0);
    }
}
