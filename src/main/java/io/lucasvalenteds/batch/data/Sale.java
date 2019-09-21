package io.lucasvalenteds.batch.data;

import java.util.List;
import java.util.Objects;

public class Sale {

    private final String id;
    private final String code;
    private final List<SaleItem> itemsSold;
    private final String salesmanName;

    public Sale(String id, String code, List<SaleItem> itemsSold, String salesmanName) {
        this.id = id;
        this.code = code;
        this.itemsSold = itemsSold;
        this.salesmanName = salesmanName;
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public List<SaleItem> getItemsSold() {
        return itemsSold;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sale sale = (Sale) o;
        return Objects.equals(id, sale.id) &&
                Objects.equals(code, sale.code) &&
                Objects.equals(salesmanName, sale.salesmanName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, salesmanName);
    }

    @Override
    public String toString() {
        return "SalesData{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", itemsSold=" + itemsSold +
                ", salesmanName='" + salesmanName + '\'' +
                '}';
    }
}
