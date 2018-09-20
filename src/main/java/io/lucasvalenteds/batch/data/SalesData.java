package io.lucasvalenteds.batch.data;

import java.util.List;
import java.util.Objects;

public class SalesData {

    private final String id;
    private final String code;
    private final List<SalesDataItem> itemsSold;
    private final String salesmanName;

    public SalesData(String id, String code, List<SalesDataItem> itemsSold, String salesmanName) {
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

    public List<SalesDataItem> getItemsSold() {
        return itemsSold;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SalesData salesData = (SalesData) o;
        return Objects.equals(id, salesData.id) &&
                Objects.equals(code, salesData.code) &&
                Objects.equals(salesmanName, salesData.salesmanName);
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
