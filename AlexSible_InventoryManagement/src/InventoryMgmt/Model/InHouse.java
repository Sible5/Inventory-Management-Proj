package InventoryMgmt.Model;

public class InHouse extends Part{
    private final int id;
    private final String name;
    private final double price;
    private final int stock;
    private final int min;
    private final int max;
    private int machineID;


    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        super(id, name, price, stock, min, max);
        //super();
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }
    public void setMachineID(int machineId){
        this.machineID = machineId;
    }
    public int getMachineID(){
        return machineID;
    }
}
