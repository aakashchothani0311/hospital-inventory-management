package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class Main extends Application{
	
	private static Stage stg;
	
	private static ArrayList<FixedItem> fItem;
	private static ArrayList<Consumables> cItem;
	
	public ArrayList<FixedItem> getfItem() {
		return fItem;
	}
	
	public void setfItem(ArrayList<FixedItem> fItem) {
		Main.fItem = fItem;
	}

	public ArrayList<Consumables> getcItem() {
		return cItem;
	}

	public void setcItem(ArrayList<Consumables> cItem) {
		Main.cItem = cItem;
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		stg = primaryStage;
		
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene scene = new Scene(root);
				
		primaryStage.setTitle("Hospital Inventory Management");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void successfulLogin() throws IOException {
		DBController dbc = new DBController();
		dbc.connectDB();
		dbc.retrieveRecord();
		
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
		Scene scene = new Scene(root, 1000, 1000);
		
		stg.setTitle("Boston Children's Hospital");
		stg.setScene(scene);
				
		stg.setOnCloseRequest(event -> {
			dbc.disconnectDB();			
		});
	}
	
	public void showAddDialog(EventHandlerDispatcher dispatcherObj) {
		try {
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("AddDialog.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();

	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Add Item");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(stg);
	        
	        AddDialogHandler adh = loader.getController();
	        adh.init(dispatcherObj, dialogStage);
	        
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.showAndWait();			
		} catch (Exception ex) {
			System.out.println("Error loading Add Dialog " + ex);
		}
	}
	
	public void showUpdateDialog(EventHandlerDispatcher dispatcherObj, String fxmlName, Item i) {
		try {
			FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource(fxmlName));
	        AnchorPane page = (AnchorPane) loader.load();
	        
	        Stage dialogStage = new Stage();
	        dialogStage.setTitle("Update Item");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(stg);
	        
	        UpdateStockHandler updateStock = loader.getController();
	        if(i.getIsConsumable())
	        	updateStock.setSelectedConsumables(dispatcherObj, dialogStage, (Consumables) i);
	        else
	        	updateStock.setSelectedFixedItem(dispatcherObj, dialogStage, (FixedItem) i);
	        
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);
	        dialogStage.showAndWait();			
		} catch (Exception ex) {
			System.out.println("Error loading Update Dialog " + ex);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}