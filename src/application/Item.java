package application;

import java.sql.Date;

public abstract class Item {
	private String itemNo;
	private String itemName;
	private int qty;
	private double price;
	private Date purchaseDate;
	private boolean isConsumable;
			
	Item(int itemNo, String itemName, int qty, double price, Date purchaseDate, boolean isConsumable){
		this.itemNo = "C-00" + itemNo;
		this.itemName = itemName;
		this.qty = qty;
		this.price = price;
		this.purchaseDate = purchaseDate;
		this.isConsumable = isConsumable;
	}
	
	public String getItemNo() {
		return itemNo;
	}
	
	public void setItemNo(int itemNo) {
		this.itemNo = "C-00" + itemNo;
	}
	
	public String getItemName() {
		return itemName;
	}
	
	public int getQty() {
		return qty;
	}
	
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public boolean getIsConsumable() {
		return isConsumable;
	}
}