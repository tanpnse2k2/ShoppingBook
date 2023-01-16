/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.naming.NamingException;
import tanpn.utils.DBHelper;

/**
 *
 * @author Nhat Tan
 */
public class OrderDAO {
    
    
    private Map<String, Integer> quantityListBuy;
    
    public Map<String, Integer> getQuantityListBuy() {
        return quantityListBuy;
    }
    
    public void loadQuantityListBuy() 
            throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select sku, SUM(quantity) as quantity "
                        + "From OrderDetails "
                        + "Group by sku";
                
                stm = con.prepareCall(sql);
                
                rs = stm.executeQuery();
                while (rs.next()) {
                    String sku = rs.getString("sku");
                    int quantity = rs.getInt("quantity");
                    
                    if (this.quantityListBuy == null) {
                        this.quantityListBuy = new HashMap<>();
                    }
                    
                    this.quantityListBuy.put(sku, quantity);
                
                }
                        
            
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        
        }
    
    }
    
   
    
    public void UpdateOrderTable(float total) throws NamingException, SQLException {
        
        Connection con = null;
        PreparedStatement stm = null;
        int EffectiveRows = 0;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Insert Into [Order] (dateBuy, total) "
                        + "Values (GETDATE(), ?)";
                
                stm = con.prepareCall(sql);
                stm.setString(1, String.valueOf(total));
                
                EffectiveRows = stm.executeUpdate();
                
            }
        
        } finally {
            
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    
    }
    
    public int getOrderID() 
            throws NamingException, SQLException {
    
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = 0;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
//                Select MAX because we need to get the last OrderID and OrderID Identity (Tu dong Tang)
            //Select Max thi No hien ra (No Column Name) - Ko hien ten -> Phai ep ten
                String sql = "Select MAX(id) as id "
                        + "From [Order]";
                
             //OR
//                String sql = "Select TOP 1(id) "
//                        + "From [order] order by id desc"; 
//                
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    result = rs.getInt("id");
                }

                
            }
            
            
        } finally {
        
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        
        
        return result;
    }
    
    public void updateOrderDetailsTable (String sku, int orderID, 
            int quantity, float price, float total)
                        throws NamingException, SQLException {
    
        Connection con = null;
        PreparedStatement stm = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Insert into OrderDetails(sku, orderid, quantity, price, total) "
                        + "Values (?, ?, ?, ?, ?)";
                
                stm = con.prepareStatement(sql);
                stm.setString(1, sku);
                stm.setString(2, String.valueOf(orderID));
                stm.setString(3, String.valueOf(quantity));
                stm.setString(4, String.valueOf(price));
                stm.setString(5, String.valueOf(total));
                
                stm.executeUpdate();
            }
        
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        
        
        }
    
    }
}
