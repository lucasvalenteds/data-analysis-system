package com.ilegra.lucasvalente.desafio.pojos;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerDataTest {

    @DisplayName("It has ID, CNPJ, Name and business area")
    @Test
    void testItHasTheNecessaryProperties() {
        CustomerData customer = new CustomerData("002", "2345675434544345", "Jose da Silva", "Rural");

        assertThat(customer.getId()).isEqualTo("002");
        assertThat(customer.getCnpj()).isEqualTo("2345675434544345");
        assertThat(customer.getName()).isEqualTo("Jose da Silva");
        assertThat(customer.getBusinessArea()).isEqualTo("Rural");
    }

}
