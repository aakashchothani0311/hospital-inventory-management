package application;

import java.sql.Date;

public class Consumables extends Item{
	private Date expiryDate;
	
	Consumables(int itemNo, String itemName, int qty, double price, Date purchaseDate, Date expiryDate) {
		super(itemNo, itemName, qty, price, purchaseDate, true);
		this.expiryDate = expiryDate;
	}
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
}