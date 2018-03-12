package com.ilegra.lucasvalente.desafio;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalesmanDataTest {

    @DisplayName("It has ID, CPF and Salary")
    @Test
    void testItHasTheNecessaryProperties() {
        SalesmanData salesman = new SalesmanData("001", "1234567891234","Diego", 50000.0);

        assertThat(salesman.getId()).isEqualTo("001");
        assertThat(salesman.getCpf()).isEqualTo("1234567891234");
        assertThat(salesman.getSalary()).isEqualTo(50000.0);
    }
}
