package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class EventHandlerDispatcher implements Initializable {
	
	@FXML RadioButton cAllItemsRB;
	@FXML RadioButton cOutOfStockRB;
	@FXML RadioButton expiryDateRB;
	@FXML TextField noOfDays;
	@FXML GridPane medicinesGrid;
	@FXML Label consumablesNoDataLabel;
	@FXML CheckBox selectAllCon;
	@FXML Button conRemoveButton;

	@FXML RadioButton fAllItemsRB;
	@FXML RadioButton fOutOfStockRB;
	@FXML GridPane fixedAssetsGrid;
	@FXML Label fixedAssetNoDataLabel;
	@FXML CheckBox selectAllFI;
	@FXML Button fiRemoveButton;
	
	Main mainObj = new Main();
	EventHandlerHelper helperObj = new EventHandlerHelper();
	DBController dbc = new DBController();
	
	ArrayList<Integer> conToRemove = new ArrayList<>();
	ArrayList<Integer> fiToRemove = new ArrayList<>();
	
	ArrayList<Consumables> dataOnConGrid;
	ArrayList<FixedItem> dataOnFIGrid;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		populateMedicineGrid(mainObj.getcItem());
		populateFixedAssetGrid(mainObj.getfItem());
	}
	
	/************************** Consumable Toggle Buttons START *************************/
	public void handleConAllItemsRB(){
		if(helperObj.toggleConRB(this, "allItems"))
			populateMedicineGrid(mainObj.getcItem());						
	}

	public void handleConOutOfStockRB(ActionEvent evt){
		if(helperObj.toggleConRB(this, "outOfStock") || !(evt.getSource() instanceof RadioButton)) {
			ArrayList<Consumables> outOfStock = new ArrayList<>();
			
			for(Consumables item : mainObj.getcItem()){
				if(item.getQty() == 0)
					outOfStock.add(item);		
			}
			populateMedicineGrid(outOfStock);
		}	
	}
		
	public void handleConExpiryItemsRB(ActionEvent evt){
		if(helperObj.toggleConRB(this, "expiry") || !(evt.getSource() instanceof RadioButton))
			populateMedicineGrid(helperObj.handleExpiredItmes(mainObj.getcItem(), 0));			
	}
	
	public void handleExpiryByDays(){
		String val = noOfDays.getText();

		if(val.equals(""))
			populateMedicineGrid(helperObj.handleExpiredItmes(mainObj.getcItem(), 0));
		else if(val.matches("^[0-9][0-9]?$"))
			populateMedicineGrid(helperObj.handleExpiredItmes(mainObj.getcItem(), Integer.parseInt(val)));
		else
			helperObj.showAlert(Alert.AlertType.ERROR, "Invalid Input", "Enter only numbers between 0 & 99", "");
	}
	/************************** Consumable Toggle Buttons END *************************/

	/************************** Consumable Update START *************************/
	public void updateConsumable(ActionEvent evt) {
		int idx = Integer.parseInt(((Hyperlink) evt.getSource()).getId());
		mainObj.showUpdateDialog(this, "UpdateConStock.fxml", (Item) dataOnConGrid.get(idx));
	}
	
	public void handleConUpdate(Consumables c) {
		for(Consumables con : mainObj.getcItem()) {
			if(con.getItemNo().equals(c.getItemNo())) {
				con.setQty(c.getQty());
				con.setPrice(c.getPrice());
				con.setPurchaseDate(c.getPurchaseDate());
				con.setExpiryDate(c.getExpiryDate());
				break;
			}
		}
		
		reApplyConFilter();
	}
	
	private void reApplyConFilter() {
		if(helperObj.consumablePrevOpt.equals("outOfStock")) {
			ActionEvent evt = new ActionEvent();
			handleConOutOfStockRB(evt);
		} else if(helperObj.consumablePrevOpt.equals("expiry")) {
			ActionEvent evt = new ActionEvent();
			handleConExpiryItemsRB(evt);
		} else
			populateMedicineGrid(mainObj.getcItem());
	}
	/************************** Consumable Update END *************************/
	
	/************************** Add Consumable START *************************/
	public void addItem() {
		mainObj.showAddDialog(this);
	}
	
	public void handleConInsert(Consumables c) {
		mainObj.getcItem().add(c);
		reApplyConFilter();
	}
	/************************** Add Consumable END *************************/
	
	/************************** Consumable CheckBox START *************************/
	public void removeConsumable(ActionEvent evt) {
		CheckBox c = (CheckBox)evt.getSource();
		String code = dataOnConGrid.get(Integer.parseInt(c.getId())).getItemNo();

		if(c.isSelected())
			conToRemove.add(helperObj.returnItemIntCode(code));
		else
			conToRemove.remove(helperObj.returnItemIntCode(code));
			
		selectAllCon.setSelected(conToRemove.size() == dataOnConGrid.size());
		conRemoveButton.setDisable(conToRemove.size() == 0);
	}
		
	public void handleSelectAllCon() {
		if(selectAllCon.isSelected()) {
			for(Consumables c : dataOnConGrid)
				conToRemove.add(helperObj.returnItemIntCode(c.getItemNo()));
		} else
			conToRemove.clear();
		
		toggleRowCheckBox(medicinesGrid, selectAllCon.isSelected());
		conRemoveButton.setDisable(conToRemove.size() == 0);
	}
	
	private void toggleRowCheckBox(GridPane gp, boolean selected) {
		for (Node n : gp.getChildren()){
			if(n instanceof CheckBox)
				((CheckBox) n).setSelected(selected);
		}
	}
	/************************** Consumable CheckBox END *************************/
	
	/************************** Remove Consumable START *************************/
	public void deleteConsumable() {		
		Optional<ButtonType> res = helperObj.showAlert(Alert.AlertType.WARNING, "Warning", "Selected items will be deleted.", "");
		
		if(res.get() == ButtonType.OK){
			if(dbc.deleteRecord(conToRemove)) {
				mainObj.getcItem().removeIf(con -> conToRemove.contains(helperObj.returnItemIntCode(con.getItemNo())));
				dataOnConGrid.removeIf(con -> conToRemove.contains(helperObj.returnItemIntCode(con.getItemNo())));
				
				populateMedicineGrid(dataOnConGrid);
				
				helperObj.showAlert(Alert.AlertType.INFORMATION, "Success", "Record(s) deleted successfully.", "");
			} else
				helperObj.showAlert(Alert.AlertType.ERROR, "Error", "Delete operation failed.", "Error occurred while deleting selected items. Please contact IT team.");
		
			conToRemove.clear();
		}
	}
	/************************** Remove Consumable END *************************/
					
	/************************** Fixed Item Toggle Buttons START *************************/
	public void handleFixedAllItemsRB(){
		if(helperObj.toggleFixedRB(this, "allItems"))
			populateFixedAssetGrid(mainObj.getfItem());						
	}
	
	public void handleFixedOutOfStockCBox(ActionEvent evt){
		if(helperObj.toggleFixedRB(this, "outOfStock") || !(evt.getSource() instanceof RadioButton)) {
			ArrayList<FixedItem> outOfStock = new ArrayList<>();
			
			for(FixedItem item : mainObj.getfItem()){
				if(item.getQty() == 0)
					outOfStock.add(item);		
			}
			populateFixedAssetGrid(outOfStock);
		}
	}
	/************************** Fixed Item Toggle Buttons END *************************/
	
	/************************** Fixed Item Update START *************************/
	public void updateFixedItem(ActionEvent evt) {
		int idx = Integer.parseInt(((Hyperlink)evt.getSource()).getId());
		mainObj.showUpdateDialog(this, "UpdateFixedItem.fxml", (Item) dataOnFIGrid.get(idx));
	}
	
	public void handleFIUpdate(FixedItem f) {		
		for(FixedItem fItem : mainObj.getfItem()) {
			if(fItem.getItemNo().equals(f.getItemNo())) {
				fItem.setQty(f.getQty());
				fItem.setPrice(f.getPrice());
				fItem.setPurchaseDate(f.getPurchaseDate());
				break;
			}			
		}
		
		reApplyFIFilter();
	}
	
	private void reApplyFIFilter() {
		if(helperObj.fixedAssetPrevOpt.equals("outOfStock")) {
			ActionEvent evt = new ActionEvent();
			handleFixedOutOfStockCBox(evt);
		} else
			populateFixedAssetGrid(mainObj.getfItem());		
	}
	/************************** Fixed Item Update END *************************/
	
	/************************** Add Fixed Item START *************************/
	public void handleFIInsert(FixedItem fi) {
		mainObj.getfItem().add(fi);
		reApplyFIFilter();
	}
	/************************** Add Fixed Item END *************************/

	
	/************************** Fixed Item CheckBox START *************************/
	public void removeFixedItem(ActionEvent evt) {
		CheckBox c = (CheckBox)evt.getSource();
		String code = dataOnFIGrid.get(Integer.parseInt(c.getId())).getItemNo();

		if(c.isSelected())
			fiToRemove.add(helperObj.returnItemIntCode(code));
		else
			fiToRemove.remove(helperObj.returnItemIntCode(code));
			
		selectAllFI.setSelected(fiToRemove.size() == dataOnFIGrid.size());
		fiRemoveButton.setDisable(fiToRemove.size() == 0);
	}
	
	public void handleSelectAllFI() {
		if(selectAllFI.isSelected()) {
			for(FixedItem f : dataOnFIGrid)
				fiToRemove.add(helperObj.returnItemIntCode(f.getItemNo()));
			
		} else
			fiToRemove.clear();
		
		toggleRowCheckBox(fixedAssetsGrid, selectAllFI.isSelected());
		fiRemoveButton.setDisable(fiToRemove.size() == 0);
	}
	/************************** Fixed Item CheckBox END *************************/
	
	/************************** Remove Fixed Item START *************************/
	public void deleteFixedItem() {		
		Optional<ButtonType> res = helperObj.showAlert(Alert.AlertType.WARNING, "Warning", "Selected items will be deleted.", "");
		
		if(res.get() == ButtonType.OK){
			if(dbc.deleteRecord(fiToRemove)) {
				mainObj.getfItem().removeIf(fi -> conToRemove.contains(helperObj.returnItemIntCode(fi.getItemNo())));
				dataOnFIGrid.removeIf(fi -> fiToRemove.contains(helperObj.returnItemIntCode(fi.getItemNo())));
				
				populateFixedAssetGrid(dataOnFIGrid);
				
				helperObj.showAlert(Alert.AlertType.INFORMATION, "Success", "Record(s) deleted successfully.", "");
			} else
				helperObj.showAlert(Alert.AlertType.ERROR, "Error", "Delete operation failed.", "Error occurred while deleting selected items. Please contact IT team.");
			
			fiToRemove.clear();
		}
	}
	/************************** Remove Fixed Item END *************************/
	
	/************************** Grid Population START *************************/
	private void populateMedicineGrid(ArrayList<Consumables> cItems) {
		helperObj.clearGrid(medicinesGrid);

		for(int i = 0; i < cItems.size(); i++) {
			Consumables con = cItems.get(i);
			
			CheckBox cb = new CheckBox();
			cb.setId(String.valueOf(i));
			cb.setOnAction(evt -> removeConsumable(evt));
			
			Hyperlink hl = new Hyperlink("Update");
			hl.setId(String.valueOf(i));
			hl.setOnAction(evt -> updateConsumable(evt));
			
			medicinesGrid.addRow(i+1, cb, new Label(String.valueOf(i+1)), new Label(con.getItemNo()), new Label(con.getItemName()),
								new Label(String.valueOf(con.getQty())), new Label(String.valueOf(con.getPrice())),
								new Label(String.valueOf(con.getPurchaseDate())), new Label(String.valueOf(con.getExpiryDate())), hl);
		}
				
		dataOnConGrid = cItems;
		helperObj.setVisibilityConsumables(this, cItems.size() > 0);
	}
	
	private void populateFixedAssetGrid(ArrayList<FixedItem> fItems) {
		helperObj.clearGrid(fixedAssetsGrid);
		
		for(int i = 0; i < fItems.size(); i++) {
			FixedItem fi = fItems.get(i);
			
			CheckBox cb = new CheckBox();
			cb.setId(String.valueOf(i));
			cb.setOnAction(evt -> removeFixedItem(evt));
			
			Hyperlink hl = new Hyperlink("Update");
			hl.setId(String.valueOf(i));
			hl.setOnAction(evt -> updateFixedItem(evt));
			
			fixedAssetsGrid.addRow(i + 1, cb, new Label(String.valueOf(i+1)), new Label(fi.getItemNo()), new Label(fi.getItemName()),
									new Label(String.valueOf(fi.getQty())), new Label(String.valueOf(fi.getPrice())),
									new Label(String.valueOf(fi.getPurchaseDate())), new Label(fi.getDepartment()), hl);
		}
			
		dataOnFIGrid = fItems;
		helperObj.setVisibilityFixedAssets(this, fItems.size() > 0);
	}
	/************************** Grid Population END *************************/
}