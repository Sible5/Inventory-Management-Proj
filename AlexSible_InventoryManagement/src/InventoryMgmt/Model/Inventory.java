package InventoryMgmt.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    //lookups via ID
    public static Part lookupPart(int partID){
        for (Part p: allParts){
            if (p.getId() == partID){
                return p;
            }
        }
        return null;
    }
    public static Product lookupProduct(int productId){
        for(Product p: allProducts){
            if(p.getID() == productId){
                return p;
            }
        }
        return null;
    }
    public static ObservableList<Part> lookupPartName(String partName){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
     for (Part p: allParts){
         if (p.getName().toLowerCase().contains(partName)){
             foundParts.add(p);
         }
     }
     return foundParts;
     }

     public static ObservableList<Product> lookupProductName(String productName){
         ObservableList<Product> foundProducts = FXCollections.observableArrayList();
         for (Product p: allProducts){
             if (p.getName().toLowerCase().contains(productName)){
                 foundProducts.add(p);
             }
         }
         return foundProducts;

     }

    //updates
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);

    }
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.set(index,selectedProduct);

    }
    //deletes ---need to ask why it returns a boolean    can use void
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);

    }
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);

    }
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
}