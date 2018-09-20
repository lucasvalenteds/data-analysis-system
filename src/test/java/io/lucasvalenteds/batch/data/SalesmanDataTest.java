package io.lucasvalenteds.batch.data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SalesmanDataTest {

    @DisplayName("It has ID, CPF name and Salary")
    @Test
    void testItHasTheNecessaryProperties() {
        var salesman = new SalesmanData("001", "1234567891234","Diego", 50000.0);

        assertThat(salesman.getId()).isEqualTo("001");
        assertThat(salesman.getName()).isEqualTo("Diego");
        assertThat(salesman.getCpf()).isEqualTo("1234567891234");
        assertThat(salesman.getSalary()).isEqualTo(50000.0);
    }
}
