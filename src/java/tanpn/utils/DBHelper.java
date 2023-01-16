/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.utils;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 *
 * @author Nhat Tan
 */
public class DBHelper {
    // Khong can tao obj nen co phuong thuc static
    public static Connection makeConnection() 
            throws /*ClassNotFoundException,*/ NamingException, SQLException {
        
        //Khac nhau HDH: Quy tat goi ten file -> Dong bo 
        
        //1.Find Server Context - JMDI (Java name directory interface)
        Context serverContext = new InitialContext();
       
        //2. Find Container Context
         Context tomcatContext = (Context) serverContext.lookup("java:comp/env");
        
        //3. Get Data Source
        DataSource ds = (DataSource)tomcatContext.lookup("MrBean");
        
        //4. Open connection
        Connection con = ds.getConnection();
        
        return con;
        
        
        //1. Load Driver
        //Ten driver bat dau bang chu com
//        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        //Cau lenh bat loi ClassnotfoundException
//        
//        //2. Make Connection URL String
//        String url = "jdbc:sqlserver://localhost:1433;" 
//                + "databaseName=account";
//        
//        
//        //3. Open Connect
//        Connection con = DriverManager.getConnection(url,"sa", "12345");
//       
//        
//        return con;
    }
    
    
    public static Properties getSiteMaps(String siteMapFile, ServletContext context) 
                throws IOException {
//        if (siteMapFile == null) {
//            return null;
//        }
//        
//        if (siteMapFile.trim().isEmpty()) {
//            return  null;
//        }
//        
//        Properties result = new Properties();
//        InputStream is = null;
//        
//        try {
//            is = context.getResourceAsStream(siteMapFile); 
//            result.load(is);
//            
//            return result;
//       } finally {
//            if (is != null) {
//                is.close();
//            }
//        }
        
        if (siteMapFile == null) {
            return null;
        }
        if (siteMapFile.trim().isEmpty()) {
            return null;
        }
        
        Properties result = new Properties();
        //Phuong thuc chuyen tu String sang Properties
        InputStream is = null;
        try {
            is = context.getResourceAsStream(siteMapFile);
            result.load(is);
            
            return result;
            
        } finally {
            if (is != null) {
                is.close();
            }
        }
        
    }
}
