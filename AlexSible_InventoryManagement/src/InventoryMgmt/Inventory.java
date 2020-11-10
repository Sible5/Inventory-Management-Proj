package InventoryMgmt;

import InventoryMgmt.view_Controller.MainScreenController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Inventory extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Inventory Management System");
        showMainScreen();

    }
    public void showMainScreen(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Inventory.class.getResource("view_Controller/MainScreen.fxml"));
            AnchorPane mainOverview = loader.load();

            Scene scene = new Scene(mainOverview);
            primaryStage.setScene(scene);
            primaryStage.show();

            MainScreenController controller = loader.getController();
            controller.setInventory();


        } catch (IOException e){
            e.printStackTrace();
        }


    }


    public static void main(String[] args) {
        launch(args);
    }
}
