package io.lucasvalenteds.das.customer;

import java.util.Objects;

public class Customer {

    private final String id;
    private final String cnpj;
    private final String name;
    private final String businessArea;

    public Customer(String id, String cnpj, String name, String businessArea) {
        this.id = id;
        this.cnpj = cnpj;
        this.name = name;
        this.businessArea = businessArea;
    }

    public String getId() {
        return id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getName() {
        return name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer that = (Customer) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(cnpj, that.cnpj) &&
                Objects.equals(name, that.name) &&
                Objects.equals(businessArea, that.businessArea);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, cnpj, name, businessArea);
    }

    @Override
    public String toString() {
        return "CustomerData{" +
                "id='" + id + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", name='" + name + '\'' +
                ", businessArea='" + businessArea + '\'' +
                '}';
    }
}
