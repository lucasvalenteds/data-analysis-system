package com.ilegra.lucasvalente.desafio;

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
}
