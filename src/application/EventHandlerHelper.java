package application;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;

public class EventHandlerHelper {
	
	static String consumablePrevOpt = "allItems";
	static String fixedAssetPrevOpt = "allItems";
	
	public boolean toggleConRB(EventHandlerDispatcher dispatcherObj, String param) {
		String temp = consumablePrevOpt;

		dispatcherObj.cAllItemsRB.setSelected(param == "allItems");
		dispatcherObj.cOutOfStockRB.setSelected(param == "outOfStock");
		dispatcherObj.expiryDateRB.setSelected(param == "expiry");
		dispatcherObj.noOfDays.setText("0");
		dispatcherObj.noOfDays.setDisable(!(param == "expiry"));
		dispatcherObj.conToRemove.clear();
		dispatcherObj.conRemoveButton.setDisable(true);
		dispatcherObj.selectAllCon.setSelected(false);
		
		consumablePrevOpt = param;
		return temp != param;
	}
	
	public boolean toggleFixedRB(EventHandlerDispatcher dispatcherObj, String param) {
		String temp = fixedAssetPrevOpt;
		dispatcherObj.fAllItemsRB.setSelected(param == "allItems");
		dispatcherObj.fOutOfStockRB.setSelected(param == "outOfStock");
		dispatcherObj.fiToRemove.clear();
		dispatcherObj.fiRemoveButton.setDisable(true);
		dispatcherObj.selectAllFI.setSelected(false);
		
		fixedAssetPrevOpt = param;		
		return temp != param;
	}
	
	public ArrayList<Consumables> handleExpiredItmes(ArrayList<Consumables> items, int numOfDays) {
		ArrayList<Consumables> expiredItem = new ArrayList<>();

		Calendar newExpDate = Calendar.getInstance();
		newExpDate.setTime(new Date());
		newExpDate.add(Calendar.DAY_OF_MONTH, numOfDays);
		
		for(Consumables item : items) {				
			Calendar itemExpiry = Calendar.getInstance();
			itemExpiry.setTime(item.getExpiryDate());
				
			if(newExpDate.after(itemExpiry) || newExpDate.equals(itemExpiry))
				expiredItem.add(item);
		}
		
		return expiredItem;
	}
		
	public void clearGrid(GridPane gp) {
		ArrayList<Node> tempList = new ArrayList<>();
		
		for(int i = 0; i < gp.getChildren().size(); i++) {
			if(i < 9)
				tempList.add(gp.getChildren().get(i));
			else
				break;
		}
		
		gp.getChildren().clear();
		gp.getChildren().addAll(tempList);
	}
	
	public void setVisibilityConsumables(EventHandlerDispatcher dispatcherObj, boolean show) {
		dispatcherObj.consumablesNoDataLabel.setVisible(!show);
		dispatcherObj.medicinesGrid.setVisible(show);
	}
		
	public void setVisibilityFixedAssets(EventHandlerDispatcher dispatcherObj, boolean show) {
		dispatcherObj.fixedAssetNoDataLabel.setVisible(!show);
		dispatcherObj.fixedAssetsGrid.setVisible(show);
	}
	
	public Optional<ButtonType> showAlert(AlertType type, String title, String headerText, String contextText) {
		Alert alert = new Alert(type);
		alert.setTitle(contextText);
		alert.setHeaderText(headerText);
		
		if(!contextText.equals(""))
			alert.setContentText(contextText);
		
		return alert.showAndWait();
	}
	
	public int returnItemIntCode(String s) {
		return Integer.parseInt(s.substring(4));
	}
}