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

import InventoryMgmt.Model.InHouse;
import InventoryMgmt.Model.Outsourced;
import InventoryMgmt.Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static InventoryMgmt.Model.Inventory.updatePart;

/**
 * FXML Controller class
 *
 * @author alexs
 */
public class ModifyPartScreenController implements Initializable {
    @FXML
    private RadioButton modPartInHouseRadio;
    @FXML
    private ToggleGroup modPartInOutGroup;
    @FXML
    private RadioButton modPartOutsourcedRadio;
    @FXML
    private Label modPartCompanyMachLabel;
    @FXML
    private TextField modPartIDField;
    @FXML
    private TextField modPartNameField;
    @FXML
    private TextField modPartInvField;
    @FXML
    private TextField modPartPriceField;
    @FXML
    private TextField modPartMaxField;
    @FXML
    private TextField modPartCompMachField;
    @FXML
    private TextField modPartMinField;
    @FXML
    private Button modPartCancelBtn;
    @FXML
    private Button modPartSaveBtn;
    private int index;
    private Part currentPart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void getPart(Part part){
        modPartIDField.setText(String.valueOf(part.getId()));
        modPartInvField.setText(String.valueOf(part.getStock()));
        modPartNameField.setText(part.getName());
        modPartPriceField.setText(String.valueOf(part.getPrice()));
        modPartMaxField.setText(String.valueOf(part.getMax()));
        modPartMinField.setText(String.valueOf(part.getMin()));

        if(part instanceof InHouse){
            modPartCompanyMachLabel.setText("Machine ID");
            modPartInHouseRadio.setSelected(true);
            modPartCompMachField.setText(String.valueOf(((InHouse) part).getMachineID()));

        }if(part instanceof Outsourced){
            modPartCompanyMachLabel.setText("Company Name");
            modPartOutsourcedRadio.setSelected(true);
            modPartCompMachField.setText(((Outsourced)part).getCompanyName());
        }

    }
    public void sentPart(Part part){
        currentPart = part;
    }
    public void setIndex(int index){
        this.index = index;
    }
    public int getIndex(){
        return index;
    }
    public void onActionModPartSave() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) modPartSaveBtn.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        root = loader.load();

        if (this.modPartInOutGroup.getSelectedToggle().equals(this.modPartInHouseRadio)){

            int newID = Integer.parseInt(modPartIDField.getText());
            String name = modPartNameField.getText();
            int inv = Integer.parseInt(modPartInvField.getText());
            double price = Double.parseDouble(modPartPriceField.getText());
            int min = Integer.parseInt(modPartMinField.getText());
            int max = Integer.parseInt(modPartMaxField.getText());
            int machineId = Integer.parseInt(modPartCompMachField.getText());

            currentPart.setName(name);
            currentPart.setPrice(price);
            currentPart.setId(newID);
            currentPart.setMax(max);
            currentPart.setMin(min);
            currentPart.setStock(inv);
            ((InHouse) currentPart).setMachineID(machineId);

            if(min >= max){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("The values entered for min and max are incorrect, please check them");
                a.show();
            }if(max > min){
                updatePart(getIndex(),currentPart);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }


        }
        if (this.modPartInOutGroup.getSelectedToggle().equals(this.modPartOutsourcedRadio)){

            int newID = Integer.parseInt(modPartIDField.getText());
            String name = modPartNameField.getText();
            int inv = Integer.parseInt(modPartInvField.getText());
            double price = Double.parseDouble(modPartPriceField.getText());
            int min = Integer.parseInt(modPartMinField.getText());
            int max = Integer.parseInt(modPartMaxField.getText());
            String companyName = modPartCompMachField.getText();

            currentPart.setName(name);
            currentPart.setPrice(price);
            currentPart.setId(newID);
            currentPart.setMax(max);
            currentPart.setMin(min);
            currentPart.setStock(inv);
            ((Outsourced) currentPart).setCompanyName(companyName);
            if(min >= max){
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("The values entered for min and max are incorrect, please check them");
                a.show();
            }if(max > min){
                updatePart(getIndex(),currentPart);
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }

        }

    }
    public void onActionModPartCancel() throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) modPartCancelBtn.getScene().getWindow();
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
