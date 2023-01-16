/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tanpn.cart.CartObject;
import tanpn.product.ProductDAO;
import tanpn.product.ProductDTO;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
@WebServlet(name = "AddItemToCartServlet", urlPatterns = {"/AddItemToCartServlet"})
public class AddItemToCartServlet extends HttpServlet {
//    private final String SHOPPING_PAGE = "shopping.html";
//    private final String ERROR_PAGE = "error.html";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
        String url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.ERROR_PAGE);
        try {
            //1. Cust goes to cart placement
            //Khi vao sieu thi luon luon phai co gio -> True, KO dien gi ca (default) = true
            HttpSession session = request.getSession(); 
            
            //2. Cust takes his/her cart
            CartObject cart = (CartObject) session.getAttribute("CART");
            
            if (cart == null) {
                cart = new CartObject();
            }
            
            //3. Cust chosen a specific item
            //Thay cai nay thanh Object ProductDTO - da co san trong session khi set LoadToCartServlet
           
            String item = request.getParameter("cboBook");
            String quantity = request.getParameter("quantity");
            
          
            
            //Ko can kiem tra != null vi Parameter luon luon ton tai
            //3.1 Check if quantity Customer Enter is a Number
            try {
                //4. Cust drops item down
                int number = Integer.parseInt(quantity.trim());
                cart.addItemToCart(item, number);
                session.setAttribute("CART", cart);
                
            
            } catch (NumberFormatException ex) {
               log("Add Item To Cart Servlet NumberFormat..." + ex.getMessage());
               //Catch exception number in jsp page
            }
            
            //5. Cust continuely goes to shopping
            
            //URL Rewritting to Refresh the Page
//            url = "DispatchController"
//                    + "?btAction=View Product";
            url = "loadProductPage";
            
        
        } finally {
            //Dung cach gi cung duoc, che duong truyen thi RD, show duong truyen thi sendRedirect
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
            //Do Dung Filter nen phai sendRedirect
            response.sendRedirect(url);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}


