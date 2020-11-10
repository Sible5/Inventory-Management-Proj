/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryMgmt.view_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import InventoryMgmt.Model.Inventory;
import InventoryMgmt.Model.Part;
import InventoryMgmt.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static InventoryMgmt.Model.Inventory.*;


/**
 * FXML Controller class
 *
 * @author alexs
 */
public class ProductScreenController implements Initializable {
    private int productIDCount;

    @FXML
    private TableColumn<Part,Integer> addProductIDUpper;
    @FXML
    private TableColumn<Part,Integer> addProductIDLower;
    @FXML
    private TableColumn<Part,Integer> addProductInvUpper;
    @FXML
    private TableColumn<Part,Integer> addProductInvLower;
    @FXML
    private TableColumn<Part,String> addProductNameUpper;
    @FXML
    private TableColumn<Part,String> addProductNameLower;
    @FXML
    private TableColumn<Part,Integer> addProductPriceUpper;
    @FXML
    private TableColumn<Part,Integer> addProductPriceLower;
    @FXML
    private TableView<Part> addProductTableUpper;
    @FXML
    private TableView<Part> addProductTableLower;
    @FXML
    private TextField addProductInvField;
    @FXML
    private TextField addProductMaxField;
    @FXML
    private TextField addProductMinField;
    @FXML
    private TextField addProductNameField;
    @FXML
    private TextField addProductPriceField;
    @FXML
    private TextField addProductSearchField;
    @FXML
    private TextField addProductIDField;
    @FXML
    private Button addProductCancelBtn;
    @FXML
    private Button addProductSaveBtn;

    private ObservableList<Part> newlyAdded = FXCollections.observableArrayList();
    private ObservableList<Part> parts = FXCollections.observableArrayList();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        for (Product product : Inventory.getAllProducts()){
                if (product.getID() >= productIDCount){
                    productIDCount = product.getID();
                }
        }
        ++productIDCount;
        addProductIDField.setText(String.valueOf(productIDCount));

        addProductIDUpper.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameUpper.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addProductInvUpper.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        addProductPriceUpper.setCellValueFactory(new PropertyValueFactory<>("Price"));

        addProductIDLower.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameLower.setCellValueFactory(new PropertyValueFactory<>("Name"));
        addProductInvLower.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        addProductPriceLower.setCellValueFactory(new PropertyValueFactory<>("Price"));

        addProductTableUpper.setItems(getAllParts());

    }
    public void onActionProductAdd(){

        newlyAdded.add(addProductTableUpper.getSelectionModel().getSelectedItem());
        addProductTableLower.setItems(newlyAdded);


    }
    public void onActionProductSave() throws IOException{
        Stage stage;
        Parent root;
        stage = (Stage) addProductSaveBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        root = loader.load();

        int id = Integer.parseInt(addProductIDField.getText());
        String name = addProductNameField.getText();
        int stock = Integer.parseInt(addProductInvField.getText());
        double price = Double.parseDouble(addProductPriceField.getText());
        int min = Integer.parseInt(addProductMinField.getText());
        int max = Integer.parseInt(addProductMaxField.getText());

        Product product = new Product(id,name,price,stock,min,max,parts);
        product.setID(id);
        product.setName(name);
        product.setMax(max);
        product.setMin(min);
        product.setStock(stock);

        for (Part p : newlyAdded){
            product.addAssociatedPart(p);
        }
        if(min >= max){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("The values entered for min and max are incorrect, please check them");
            a.show();
        }if(max > min){
            addProduct(product);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }



    }
    public void onActionProductCancel() throws IOException{
        Stage stage;
        Parent root;
        stage = (Stage) addProductCancelBtn.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        root = loader.load();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to exit? Unsaved Changes will be lost.");
        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    public void onActionProductDelete(){

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to delete this associated part?");
        Optional<ButtonType> result = a.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            newlyAdded.remove(addProductTableLower.getSelectionModel().getSelectedItem());
        }


    }
    public void onActionProductSearch() {
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        try {
            int searchID = Integer.parseInt(addProductSearchField.getText());
            lookupPart(searchID);
            foundParts.add(lookupPart(searchID));
            addProductTableUpper.setItems(foundParts);

        }catch (NumberFormatException e){


            foundParts.addAll(lookupPartName(addProductSearchField.getText().toLowerCase()));
            addProductTableUpper.setItems(foundParts);
        }

    }
    }

    

