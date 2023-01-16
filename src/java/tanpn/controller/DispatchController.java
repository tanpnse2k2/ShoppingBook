package tanpn.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
@WebServlet (name = "DispatcherController", urlPatterns = {"/DispatchController"})   
public class DispatchController extends HttpServlet {
//    private final String LOGIN_PAGE = "";
//    private final String LOGIN_CONTROLLER = "LoginServlet";
//    private final String LOGIN_CONTROLLER = "loginController";
//    private final String SEARCH_LASTNAME_CONTROLLER = "SearchLastnameServlet";
//    private final String DELETE_ACCOUNT_CONTROLLER = "DeleteAccountServlet";
//    private final String FIRST_REQUEST_CONTROLLER = "FirstRequestServlet";
//    private final String UPDATE_INFORAMTION_CONTROLLER = "UpdateSevlet";
//    private final String VIEW_CART_CONTROLLER = "userCart.jsp";
//    private final String ADD_ITEM_TO_CART_CONTROLLER = "AddItemToCartServlet";
//    private final String REMOVE_ITEM_TO_CART_CONTROLLER = "RemoveItemToCartServlet";
//    private final String LOG_OUT_FROM_SEARCH_PAGE = "LogOutServlet";
//    private final String VIEW_PRODUCT_PAGE = "LoadProductListServlet";
//    private final String CHECK_OUT_SERVLET = "CheckOutServlet";
//    private final String CREATE_NEW_ACCOUNT_CONTROLLER = "CreateNewAccountServlet";
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
        String url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOGIN_PAGE);
        
        
        //URL will link to the first PAGE
//        String url = LOGIN_PAGE;
       // String url = "invalid.html";
       
        //Which button in user click?
        String button = request.getParameter("btAction");
        
        try {
            if (button == null) {
                //Map chuc nang do vao ben trong dieu phoi
                //Lan thu nhat
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.FIRST_REQUEST_CONTROLLER);
                
            } else if (button.equals("Login")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOGIN_CONTROLLER);
            } else if (button.equals("Search")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.SEARCH_CONTROLLER);
            } else if (button.equals("delete")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.DELETE_CONTROLLER);
            } else if (button.equals("Update")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.UPDATE_CONTROLLER);
            } else if (button.equals("View Product")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.VIEW_PRODUCT_PAGE);
            } else if (button.equals("Add book to Your Cart")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.ADD_ITEM_TO_CART_CONTROLLER);
            } else if (button.equals("View Your Cart")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.VIEW_CART_CONTROLLER);
            } else if (button.equals("Remove Selected Action")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.REMOVE_ITEM_TO_CART_CONTROLLER);
            } else if (button.equals("Log Out")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOG_OUT_FROM_SEARCH_PAGE);
            } else if (button.equals("CheckOut")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.CHECK_OUT_SERVLET);
            } else if (button.equals("Create New Account")) {
                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.CREATE_NEW_ACCOUNT_CONTROLLER);;
            }
        }
            
        finally{
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        }
    }

    
    //1. Tao view va view do chuyen toi dieu phoi
    //2. Map chuc nang do vao ben trong dieu phoi
    //3. Tao servlet chuc nang
    //4. call DAO
    //5. Ket noi Database
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
