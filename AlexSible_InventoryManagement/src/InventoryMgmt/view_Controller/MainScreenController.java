/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryMgmt.view_Controller;


import InventoryMgmt.Model.Part;
import InventoryMgmt.Model.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
public class MainScreenController implements Initializable{
 
    @FXML
    private TextField mainProductSearchField;
    @FXML
    private TextField mainPartSearchField;
    @FXML
    private TableView<Part> mainPartTableView;
    @FXML
    private TableView<Product> mainProductTableView;
    @FXML
    private TableColumn<Part,Integer> mainPartIDCol;
    @FXML
    private TableColumn<Part,String> mainPartNameCol;
    @FXML
    private TableColumn<Part,Integer> mainInvLevelCol;
    @FXML
    private TableColumn<Part, Double> mainPriceCostCol;
    @FXML
    private TableColumn<Product,Integer> mainProductIDCol;
    @FXML
    private TableColumn<Product,String> mainProductNameCol;
    @FXML 
    private TableColumn<Product, Integer> mainProductInventoryCol;
    @FXML
    private TableColumn<Product, Double> mainProductPriceCol;
    @FXML
    private Button mainAddPartBtn;
    @FXML
    private Button mainModifyPartBtn;

    @FXML
    private Button mainAddProductBtn;
    @FXML
    private Button mainModifyProductBtn;
    @FXML
    private Button mainDeleteProductBtn;
    @FXML
    private Button mainExitBtn;


    public void initialize(URL url, ResourceBundle rb){

    mainPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
    mainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
    mainInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
    mainPriceCostCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

    mainProductIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
    mainProductNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
    mainProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
    mainProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));

    mainPartTableView.setItems(getAllParts());
    mainProductTableView.setItems(getAllProducts());



}
public void onActionProductSearch(){
    ObservableList<Product> foundProducts = FXCollections.observableArrayList();
    try {
        int searchID = Integer.parseInt(mainProductSearchField.getText());
        lookupPart(searchID);
        foundProducts.add(lookupProduct(searchID));
        mainProductTableView.setItems(foundProducts);

    }catch (NumberFormatException e){
        foundProducts.addAll(lookupProductName(mainProductSearchField.getText().toLowerCase()));
        mainProductTableView.setItems(foundProducts);
    }
}

    public void onActionPartSearch(){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        try {
            int searchID = Integer.parseInt(mainPartSearchField.getText());
            lookupPart(searchID);
            foundParts.add(lookupPart(searchID));
            mainPartTableView.setItems(foundParts);

        }catch (NumberFormatException e){


            foundParts.addAll(lookupPartName(mainPartSearchField.getText().toLowerCase()));
            mainPartTableView.setItems(foundParts);
        }

        }




    public void onActionAddPart() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) mainAddPartBtn.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("PartScreen.fxml"));
        root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();



    }

    public void onActionModifyPart() throws IOException{
        Stage stage;
        Parent root;
        stage = (Stage) mainModifyPartBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPartScreen.fxml"));
        loader.load();
        ModifyPartScreenController MPartController = loader.getController();
        MPartController.getPart(mainPartTableView.getSelectionModel().getSelectedItem());
        MPartController.setIndex(mainPartTableView.getSelectionModel().getSelectedIndex());
        MPartController.sentPart(mainPartTableView.getSelectionModel().getSelectedItem());
        root = loader.getRoot();


        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void onActionDeletePart() {


        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deletePart(mainPartTableView.getSelectionModel().getSelectedItem());
        }

    }

    public void onActionAddProduct() throws IOException{
        Stage stage;
        Parent root;
        stage = (Stage) mainAddProductBtn.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ProductScreen.fxml"));
        root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void onActionModifyProduct() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) mainModifyProductBtn.getScene().getWindow();


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProductScreen.fxml"));
        loader.load();
        ModifyProductScreenController MProductController = loader.getController();
        MProductController.getProduct(mainProductTableView.getSelectionModel().getSelectedItem());
        MProductController.setIndex(mainProductTableView.getSelectionModel().getSelectedIndex());
        MProductController.sentProduct(mainProductTableView.getSelectionModel().getSelectedItem());
        root = loader.getRoot();

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void onActionDeleteProduct() {
        Stage stage = (Stage) mainDeleteProductBtn.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to delete this product?");
        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            deleteProduct(mainProductTableView.getSelectionModel().getSelectedItem());
        }
        if (result.isPresent() && result.get() == ButtonType.CANCEL){
            stage.show();
        }
    }

    public void onActionExit() {

        Stage stage = (Stage) mainExitBtn.getScene().getWindow();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to exit? Unsaved Changes will be lost.");
        Optional<ButtonType> result = a.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage.close();
        }
        if (result.isPresent() && result.get() == ButtonType.CANCEL){
            stage.show();
        }
    }

    public void setInventory() {


    }


}






