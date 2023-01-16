/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tanpn.utils.DBHelper;

/**
 *
 * @author Nhat Tan
 */
public class ProductDAO implements Serializable{
    
    private List<ProductDTO> productList;

    public List<ProductDTO> getProductList() {
        return productList;
    }
  
    public void showProduct () throws SQLException, NamingException {
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
            
                String sql = "Select sku, name, price, quantity "
                        + "From tblProduct "
                        + "Where quantity > ? AND status = ?";
                
                stm = con.prepareStatement(sql);
                stm.setString(1, "0");
                stm.setString(2, "true");
                
                rs = stm.executeQuery();
                
                while (rs.next()) {
                    String sku = rs.getString("sku");
                    String name = rs.getString("name");
                    float price = rs.getFloat("price");
                    int quantiy = rs.getInt("quantity");
                    ProductDTO dto = new ProductDTO(sku, name, quantiy, price);
                    
                    if (this.productList == null) {
                        this.productList = new ArrayList<>();
                    }
                    
                    
                    this.productList.add(dto);
                }
                
            
            }
        
        } finally {
            
            if (rs != null) {
                rs.close();
            }
            if (stm != null)  {
                stm.close();
            }
            if (con != null) {
                 con.close();
             
             }
            
        }
        
        
        


    }
    
    
    public int getQuanity (String sku) 
            throws SQLException, NamingException  {
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = 0;
        try {
            con = DBHelper.makeConnection();
            
            String sql = "Select quantity "
                    + "From tblProduct "
                    + "Where sku = ?";
            
            stm = con.prepareStatement(sql);
            stm.setString(1, sku);
            
            rs = stm.executeQuery();
            
            if (rs.next()) {
                result = rs.getInt("quantity");
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
    
    public void updateQuantity(String sku, int quantity) 
            throws NamingException, SQLException {
    
        Connection con = null;
        PreparedStatement stm = null;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Update tblProduct "
                        + "Set quantity = ? "
                        + "Where sku = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, String.valueOf(quantity));
                stm.setString(2, sku);
                
                
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
    
     public float getPriceOfProduct(String sku) 
            throws NamingException, SQLException{
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        float result = 0;
        
        try {
            con = DBHelper.makeConnection();
            if (con != null) {
                String sql = "Select price "
                        + "From tblProduct "
                        + "Where sku = ? ";
                
                stm = con.prepareCall(sql);
                stm.setString(1, sku);
                
                rs = stm.executeQuery();
                
                if (rs.next()) {
                    result = rs.getFloat("price");
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
    
     public String getNameProduct (String sku) 
             throws SQLException, NamingException {
         Connection con = null;
         PreparedStatement stm = null;
         ResultSet rs = null;
         String result = null;
         
         try {
             con = DBHelper.makeConnection();
             if (con != null) {
                 String sql = "Select name "
                         + "From tblProduct "
                         + "Where username = ?";
                 stm = con.prepareStatement(sql);
                 stm.setString(1, sku);
                 
                 rs = stm.executeQuery();
                 
                 if (rs.next()) {
                     result = rs.getString("name");
                     return result;
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
    
}
