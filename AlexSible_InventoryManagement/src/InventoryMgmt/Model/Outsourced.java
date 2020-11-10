package InventoryMgmt.Model;

public class Outsourced extends Part {
    private final int id;
    private final String name;
    private final double price;
    private final int stock;
    private final int min;
    private final int max;
    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id,name,price,stock,min,max);
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.companyName = companyName;
    }

    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public String getCompanyName() {

        return companyName;
    }
}
