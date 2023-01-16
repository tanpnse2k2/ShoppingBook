/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.registration;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import tanpn.hashsha256.hashSHA256;
import tanpn.utils.DBHelper;

/**
 *
 * @author Nhat Tan
 */
public class RegistrationDAO implements Serializable{
    // Cung cap phuong thuc de access Database
//    public boolean checkLogin(String username, String password) 
            
    public RegistrationDTO checkLogin(String username, String password) 
        throws SQLException, /*ClassNotFoundException*/ NamingException {
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        RegistrationDTO result = null;
        try {
            //1. connect DB
            con = DBHelper.makeConnection();
            
            if (con != null) {
                //2. CRUD
                //2.1 Create SQL String
                String sql = "Select username, lastname "
                        + "From Registration "
                        + "Where username = ? And password = ?";
                
                //2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                
                //2.3 Execute Query --> Result Set
                rs = stm.executeQuery();
                
                //2.4 Process result Set
                if (rs.next()) {
                    String fullName = rs.getString("lastname");
                    result = new RegistrationDTO(username, password, fullName, false);
                    
                }
            } // end connection is available
            
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
    
    private List<RegistrationDTO> accountList;

    //Phat sinh roi rac, Refactor phat sinh hang loat
    public List<RegistrationDTO> getAccountList() {
        return accountList;
    }
    
    public void searchLastname (String searchValue) 
            throws SQLException, /*ClassNotFoundException,*/ NamingException {
        
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
//        boolean result = false;
        try {
            //1. connect DB
            con = DBHelper.makeConnection();
            
            if (con != null) {
                //2. CRUD
                //2.1 Create SQL String
                String sql = "Select username, password, Lastname, isAdmin "
                        + "From Registration "
                        + "Where lastname Like ?";
                
                //2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + searchValue + "%");
                
                
                //2.3 Execute Query --> Result Set
                rs = stm.executeQuery();
                
                //Result set anh xa tung field 1 trong table
                while (rs.next()) {
                    //Co 2 cach lay, Lay theo vi tri, ten cot
                    //Ko nen lay theo vi tri vi` phai dem, neu thay doi/ dao thu tu trong DB la sai
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String fullname = rs.getString("Lastname");
                    boolean role = rs.getBoolean("isAdmin");
                    RegistrationDTO dto = new RegistrationDTO(username,
                            password, fullname, role);
                    if (this.accountList == null) {
                        this.accountList = new ArrayList<>();
                    
                    } //end account List has not existed
                    
                    this.accountList.add(dto);
                }//end traverse Result set
                    
                
                
               
            } // end connection is available
            
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
    
    public boolean deleteAccount(String username) 
            throws SQLException, /*ClassNotFoundException*/ NamingException {
        
//        Lien quan den RS se bo vi no se ra so
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            //1. connect DB
            con = DBHelper.makeConnection();
            
            if (con != null) {
                //2. CRUD
                //2.1 Create SQL String
//                De dau sao trong String sql se khong sai nhung ko chay dc - ko bao loi luon
                String sql = "Delete From Registration "
                        + "Where username = ?";
                
                //2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
               
                
                //Tat ca cau lenh Insert, Delete, Update deu la executeUpdate
                int effectedRow = stm.executeUpdate();
                
                if (effectedRow > 0) {
                    result = true;
                }
            } // end connection is available
            
        } finally {
            
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        
        }
        
        
        return result;
    
    
    }
    
    
    public boolean updateInformation(String username, String password,
                    String isRole) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stmPassword = null;
        PreparedStatement stmIsRole = null;
        boolean Result = false;
        //ResultSet rs = null;
        if (isRole == null) {
            isRole = "false";
        } else {
            isRole = "true";
        }
        
        try {
            con = DBHelper.makeConnection();
            int effectiveRowPassword = 0;
            int effectiveRowIsRole = 0;
            //Co 2 cach de lam
            //1. Update tung cai
            if (password.trim().length() != 0) {
                hashSHA256 hash = new hashSHA256();
                String hashPassword = hash.HashPassword(password.trim());
                
                
                String sql = "Update Registration "
                        + "Set password = ? "
                        + "Where username = ?";
                
                stmPassword = con.prepareStatement(sql);
                stmPassword.setString(1, hashPassword);
                stmPassword.setString(2, username);
                
                effectiveRowPassword = stmPassword.executeUpdate();
            
            }
            
            
                String sql = "Update Registration "
                        + "Set isAdmin = ? "
                        + "Where username = ?";
                stmIsRole = con.prepareStatement(sql);
                stmIsRole.setString(1, isRole);
                stmIsRole.setString(2, username);
            
                effectiveRowIsRole = stmIsRole.executeUpdate();
                
            //2. Lay information theo username roi update
            if (effectiveRowIsRole > 0 || effectiveRowPassword > 0) {
                Result = true;
            }
                
            
        } catch (NamingException ex){
            
            ex.printStackTrace();
            
        } catch (SQLException ex){
        
            ex.printStackTrace();
            
        } finally {
            
            
          if (stmIsRole != null) {
              stmIsRole.close();
          }
            
          if (stmPassword != null) {
              stmPassword.close();
          }
          
          if (con != null) {
              con.close();
          }
        
        }
    
       
        return Result;
    }
    
    //Truyen 1 ham tren 3 tham so -> nen dung 1 object chung de truyen -> DTO
    public boolean createAccount (RegistrationDTO dto) 
                throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            //1. connect DB
            con = DBHelper.makeConnection();
            
            if (con != null) {
                //2. CRUD
                //2.1 Create SQL String
                String sql = "Insert Into Registration("
                        + "username, password, lastname, isAdmin"
                        + ") "
                        + "Values("
                        + "?, ?, ?, ?"
                        + ")";
                
                //2.2 Create Statement Object
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getUsername());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getFullName());
                stm.setBoolean(4, dto.isRole());
                
                //2.3 Execute Query --> Result Set
                int effectedRow = stm.executeUpdate();
                
                //2.4 Process result Set
                if (effectedRow > 0) {
                    result = true;
                }
                
            } // end connection is available
            
        } finally {
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
