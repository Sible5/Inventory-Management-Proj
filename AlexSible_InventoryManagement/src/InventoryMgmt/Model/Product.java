package InventoryMgmt.Model;

import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max,ObservableList<Part> associatedParts){

        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = associatedParts;
    }
    //setters
    public void setID(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public void setStock(int stock){
        this.stock = stock;
    }
    public void setMin(int min){
        this.min = min;

    }
    public void setMax(int max) {
        this.max = max;
    }

    //getters below here
    public int getID(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
    public int getStock(){
        return this.stock;
    }
    public int getMin(){
        return this.min;
    }
    public int getMax(){
        return this.max;
    }

    public void addAssociatedPart(Part part){
        associatedParts.add(part);

    }
    public void deleteAssociatedPart(Part selectedAsPart){
        associatedParts.remove(selectedAsPart);
    }
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}