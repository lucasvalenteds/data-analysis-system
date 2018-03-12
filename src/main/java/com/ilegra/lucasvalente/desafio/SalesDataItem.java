package com.ilegra.lucasvalente.desafio;

class SalesDataItem {

    private final String id;
    private final int quantity;
    private final double price;

    SalesDataItem(String id, int quantity, double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
