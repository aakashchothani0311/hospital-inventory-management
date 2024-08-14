package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBController implements ConstantsClass{
	
	private static Connection con;
	
	public void connectDB() {
		try {
		    con = DriverManager.getConnection("jdbc:mysql://localhost/stock", "root", "");
		} catch (Exception ex) {
		    System.out.print("Error connecting to DB: " + ex);
		}
	}
	
	public void retrieveRecord() {
		try {
		    ResultSet rs = con.createStatement().executeQuery("select * from item");
		    
		    ArrayList<FixedItem> fItem = new ArrayList<FixedItem>();
		    ArrayList<Consumables> cItem = new ArrayList<Consumables>();
			    		    
	        while (rs.next()) {	             
	             if(rs.getBoolean(consumable))
	            	 cItem.add(new Consumables(rs.getInt(itemNo), rs.getString(itemName), rs.getInt(qty), rs.getDouble(price), rs.getDate(purchaseDate), rs.getDate(expiryDate)));
	             else
	            	 fItem.add(new FixedItem(rs.getInt(itemNo), rs.getString(itemName), rs.getInt(qty), rs.getDouble(price), rs.getDate(purchaseDate), rs.getString(department)));
	        }
	        
		    Main obj = new Main();
		    obj.setfItem(fItem);
		    obj.setcItem(cItem);
		} catch (SQLException ex) {
		    System.out.print("Error retrieving records: " + ex);
		}
	}
	
	public int insertRecord(Item i) {
		try {
		    String sql = "INSERT INTO `item`(`item_name`, `qty`, `price`, `purchase_date`, `consumable`, ";
		    sql += i.getIsConsumable() ? "`expiry_date`" : "`dept`";
		    sql += ") VALUES (?, ? , ? , ?, ?, ?)";
		    
		    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		    ps.setString(1, i.getItemName());
		    ps.setInt(2, i.getQty());
		    ps.setDouble(3, i.getPrice());
		    ps.setDate(4, i.getPurchaseDate());
		    ps.setBoolean(5, i.getIsConsumable());
		    
		    if(i.getIsConsumable())
			    ps.setDate(6, ((Consumables) i).getExpiryDate());
		    else
		    	ps.setString(6, ((FixedItem) i).getDepartment());
		    		    		    
		    ps.executeUpdate();
		    
		    ResultSet generatedKeys = ps.getGeneratedKeys();
		    generatedKeys.next();		    		
		    return generatedKeys.getInt(1);
		} catch (SQLException ex) {
		    System.out.print("Error inserting records:" + ex);
		    return -1;
		}
	}
	
	public boolean updateRecord(Item i) {
		try {		    
		    String sql = "UPDATE `item` SET `qty`=?,`price`=?,`purchase_date`=?,`expiry_date`=?,`dept`=? WHERE item_code=?";
		    PreparedStatement ps = con.prepareStatement(sql);
		    ps.setInt(1, i.getQty());
		    ps.setDouble(2, i.getPrice());
		    ps.setDate(3, i.getPurchaseDate());
		    
		    if(i.getIsConsumable()) {
			    ps.setDate(4, ((Consumables) i).getExpiryDate());
			    ps.setString(5, "");
		    } else {
			    ps.setDate(4, null);
			    ps.setString(5, ((FixedItem) i).getDepartment());
		    }
		    
		    ps.setInt(6, Integer.parseInt(i.getItemNo().substring(4)));
		    
		    ps.execute();		    
		    return true;
		} catch (SQLException ex) {
		    System.out.print("Error updating records:" + ex);
		    return false;
		}
	}
	
	public boolean deleteRecord(ArrayList<Integer> ids) {
        try {
        	ArrayList<String> temp = new ArrayList<>();
        	
        	for (Integer i : ids)
        	    temp.add(i.toString());
        	        		
    		String sql="DELETE FROM `item` WHERE item_code IN (" +  String.join(",", temp) + ")" ;			 
 			con.createStatement().execute(sql);
 			
 			return true;
		} catch (SQLException ex) {
		    System.out.print("Error deleting records:" + ex);
			return false;
		}
	}
	
	public void disconnectDB() {
	    try {
			con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}		
	}
}