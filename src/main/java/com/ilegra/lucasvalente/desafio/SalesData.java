package com.ilegra.lucasvalente.desafio;

import java.util.List;

class SalesData {

    private final String id;
    private final String name;
    private final List<SalesDataItem> itemsSold;

    SalesData(String id, String name, List<SalesDataItem> itemsSold) {
        this.id = id;
        this.name = name;
        this.itemsSold = itemsSold;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SalesDataItem> getItemsSold() {
        return itemsSold;
    }
}
