package application;

import java.sql.Date;

public class FixedItem extends Item{
	private String department;
	
	FixedItem(int itemNo, String itemName, int qty, double price, Date purchaseDate, String department){
		super(itemNo, itemName, qty, price, purchaseDate, false);
		this.department = department;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
}