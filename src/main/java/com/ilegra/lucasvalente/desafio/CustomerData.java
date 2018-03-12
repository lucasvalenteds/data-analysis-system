package com.ilegra.lucasvalente.desafio;

public class CustomerData {

    private final String id;
    private final String cnpj;
    private final String name;
    private final String businessArea;

    CustomerData(String id, String cnpj, String name, String businessArea) {
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
}
