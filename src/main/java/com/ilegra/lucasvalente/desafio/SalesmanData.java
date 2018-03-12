package com.ilegra.lucasvalente.desafio;

import java.util.Objects;

class SalesmanData {

    private final String id;
    private final String cpf;
    private final String name;
    private final double salary;

    SalesmanData(String id, String cpf, String name, double salary) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesmanData that = (SalesmanData) o;
        return Double.compare(that.salary, salary) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(cpf, that.cpf) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, cpf, name, salary);
    }

    @Override
    public String toString() {
        return "SalesmanData{" +
                "id='" + id + '\'' +
                ", cpf='" + cpf + '\'' +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
