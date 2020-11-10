package InventoryMgmt.view_Controller;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

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

public class ModifyProductScreenController implements Initializable{

    @FXML
    private TableColumn<Product,Integer> modProductIDUpper;
    @FXML
    private TableColumn<Product,Integer> modProductIDLower;
    @FXML
    private TableColumn<Product,Integer> modProductInvUpper;
    @FXML
    private TableColumn<Product,Integer> modProductInvLower;
    @FXML
    private TableColumn<Product,String> modProductNameUpper;
    @FXML
    private TableColumn<Product,String> modProductNameLower;
    @FXML
    private TableColumn<Product,Integer> modProductPriceUpper;
    @FXML
    private TableColumn<Product,Integer> modProductPriceLower;
    @FXML
    private TableView<Part> modProductTableUpper;
    @FXML
    private TableView<Part> modProductTableLower;
    @FXML
    private TextField modProductInvField;
    @FXML
    private TextField modProductMaxField;
    @FXML
    private TextField modProductMinField;
    @FXML
    private TextField modProductNameField;
    @FXML
    private TextField modProductPriceField;
    @FXML
    private TextField modProductSearchField;
    @FXML
    private TextField modProductIDField;
    @FXML
    private Button modProductCancelBtn;
    @FXML
    private Button modProductSaveBtn;

    private Product currentProduct;
    private int index;
    public ObservableList<Part> parts = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modProductIDUpper.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProductNameUpper.setCellValueFactory(new PropertyValueFactory<>("Name"));
        modProductInvUpper.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        modProductPriceUpper.setCellValueFactory(new PropertyValueFactory<>("Price"));

        modProductIDLower.setCellValueFactory(new PropertyValueFactory<>("id"));
        modProductNameLower.setCellValueFactory(new PropertyValueFactory<>("Name"));
        modProductInvLower.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        modProductPriceLower.setCellValueFactory(new PropertyValueFactory<>("Price"));

        modProductTableUpper.setItems(getAllParts());



    }

    public void getProduct(Product product){

        modProductIDField.setText(String.valueOf(product.getID()));
        modProductInvField.setText(String.valueOf(product.getStock()));
        modProductNameField.setText(product.getName());
        modProductPriceField.setText(String.valueOf(product.getPrice()));
        modProductMaxField.setText(String.valueOf(product.getMax()));
        modProductMinField.setText(String.valueOf(product.getMin()));
        parts = product.getAllAssociatedParts();
        modProductTableLower.setItems(parts);


    }
    public void onActionModProductAdd(){
    parts.add(modProductTableUpper.getSelectionModel().getSelectedItem());
    modProductTableLower.setItems(parts);
    }
    public void sentProduct(Product product){
        currentProduct = product;
    }
    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){
        return index;
    }
    public void onActionModProductSave() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) modProductSaveBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        root = loader.load();
        int newID = Integer.parseInt(modProductIDField.getText());
        String name = modProductNameField.getText();
        int inv = Integer.parseInt(modProductInvField.getText());
        double price = Double.parseDouble(modProductPriceField.getText());
        int min = Integer.parseInt(modProductMinField.getText());
        int max = Integer.parseInt(modProductMaxField.getText());


        currentProduct.setName(name);
        currentProduct.setPrice(price);
        currentProduct.setID(newID);
        currentProduct.setMax(max);
        currentProduct.setMin(min);
        currentProduct.setStock(inv);
        if(min >= max){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("The values entered for min and max are incorrect, please check them");
            a.show();
        }if(max > min){
            updateProduct(getIndex(),currentProduct);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }


    }
    public void onActionModProductDelete(){

        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setContentText("Are you sure you want to delete this associated part?");
        Optional<ButtonType> result = a.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            currentProduct.deleteAssociatedPart(modProductTableLower.getSelectionModel().getSelectedItem());
        }
    }
    public void onActionModProductCancel() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) modProductCancelBtn.getScene().getWindow();
        //load Add Part FXML
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
    public void onActionModProductSearch(){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        try {
            int searchID = Integer.parseInt(modProductSearchField.getText());
            lookupPart(searchID);
            foundParts.add(lookupPart(searchID));
            modProductTableUpper.setItems(foundParts);

        }catch (NumberFormatException e){

            foundParts.addAll(lookupPartName(modProductSearchField.getText().toLowerCase()));
            modProductTableUpper.setItems(foundParts);
        }
    }
}
