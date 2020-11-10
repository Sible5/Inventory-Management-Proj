package InventoryMgmt.Model;



public abstract class Part {


    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;



    public Part(int id, String name, double price, int stock, int min, int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

    }
    //ID get set
    public void setId(int id){
        this.id = id;
    }
    public int getId(){

        return this.id;
    }

    //name
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    //price
    public void setPrice(double price){
        this.price = price;
    }
    public double getPrice(){
        return price;
    }

    //stock
    public void setStock(int stock){
        this.stock = stock;
    }

    public int getStock(){
        return stock;
    }
    //min
    public void setMin(int min){
        this.min = min;
    }
    public int getMin(){
        return min;
    }

    //max
    public void setMax(int max){
        this.max = max;
    }
    public int getMax(){
        return max;
    }

}
