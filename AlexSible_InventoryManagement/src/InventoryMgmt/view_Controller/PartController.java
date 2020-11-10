/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InventoryMgmt.view_Controller;

import InventoryMgmt.Model.InHouse;
import InventoryMgmt.Model.Inventory;
import InventoryMgmt.Model.Outsourced;
import InventoryMgmt.Model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;



import static InventoryMgmt.Model.Inventory.addPart;


/**
 * FXML Controller class
 *
 * @author alexs
 */



public class PartController implements Initializable {
    @FXML
    private RadioButton addPartInHouseRadio;
    @FXML
    private ToggleGroup addPartInOutGroup;
    @FXML
    private RadioButton addPartOutsourcedRadio;
    @FXML
    private Label addPartCompanyMachLabel;
    @FXML
    private TextField addPartIDField;
    @FXML
    private TextField addPartNameField;
    @FXML
    private TextField addPartInvField;
    @FXML
    private TextField addPartPriceField;
    @FXML
    private TextField addPartMaxField;
    @FXML
    private TextField addPartCompMachField;
    @FXML
    private TextField addPartMinField;
    @FXML
    private Button addPartSaveBtn;
    @FXML
    private Button addPartCancelBtn;
    private int partIdCheck;



    /**
     * Initializes the controller class.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (Part p : Inventory.getAllParts()){
            if (p.getId() >= partIdCheck){
                partIdCheck = p.getId();
            }
        }
        ++partIdCheck;
        addPartIDField.setText(String.valueOf(partIdCheck));

    }



    @FXML
    public void radioPicker() {
        if (this.addPartInOutGroup.getSelectedToggle().equals(this.addPartInHouseRadio)) {
            addPartCompanyMachLabel.setText("Machine ID");
            addPartCompMachField.setPromptText("Machine ID");

        }
        if (this.addPartInOutGroup.getSelectedToggle().equals(this.addPartOutsourcedRadio)) {
            addPartCompanyMachLabel.setText("Company Name");
            addPartCompMachField.setPromptText("Company Name");

        }
    }

    public void onActionPartSave () throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) addPartSaveBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        root = loader.load();

        try {
            if (this.addPartInOutGroup.getSelectedToggle().equals(this.addPartInHouseRadio)) {

                int newID = Integer.parseInt(addPartIDField.getText());
                String name = addPartNameField.getText();
                int inv = Integer.parseInt(addPartInvField.getText());
                double price = Double.parseDouble(addPartPriceField.getText());
                int min = Integer.parseInt(addPartMinField.getText());
                int max = Integer.parseInt(addPartMaxField.getText());
                int machineId = Integer.parseInt(addPartCompMachField.getText());

                Part part = new InHouse(newID, name, price, inv, min, max, machineId);
                part.setPrice(price);
                part.setId(newID);
                part.setMax(max);
                part.setMin(min);
                part.setStock(inv);
                ((InHouse) part).setMachineID(machineId);
                if(min >= max){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("The values entered for min and max are incorrect, please check them");
                    a.show();
                }if(max > min){
                    addPart(part);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                }

            }
            if (this.addPartInOutGroup.getSelectedToggle().equals(this.addPartOutsourcedRadio)) {

                int newID = Integer.parseInt(addPartIDField.getText());
                String name = addPartNameField.getText();
                int inv = Integer.parseInt(addPartInvField.getText());
                double price = Double.parseDouble(addPartPriceField.getText());
                int min = Integer.parseInt(addPartMinField.getText());
                int max = Integer.parseInt(addPartMaxField.getText());
                String companyName = addPartCompMachField.getText();

                Part part = new Outsourced(newID, name, price, inv, min, max, companyName);
                part.setPrice(price);
                part.setId(newID);
                part.setMax(max);
                part.setMin(min);
                part.setStock(inv);
                ((Outsourced) part).setCompanyName(companyName);

                if(min >= max){
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("The values entered for min and max are incorrect, please check them");
                    a.show();
                }if(max > min){
                    addPart(part);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setContentText("You have entered incorrect Values");
            a.show();
        }


    }


    public void onActionPartCancel() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) addPartCancelBtn.getScene().getWindow();
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






}


