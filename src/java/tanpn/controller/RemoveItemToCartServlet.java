/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tanpn.cart.CartObject;
import tanpn.product.ProductDTO;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
@WebServlet(name = "RemoveItemToCartServlet", urlPatterns = {"/RemoveItemToCartServlet"})
public class RemoveItemToCartServlet extends HttpServlet {

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
        try {
          
          //1. Cust goest to his/her cart placement
          //Neu nguoi dung bi Session Time Out, Bam Remove -> Check false
            HttpSession session = request.getSession(false);
            
            //Co Session thi moi xoa
            if (session != null) {
                //2. Cust take his/her cart
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    //3. Cust gets all items
                    Map <String, Integer> items = cart.getItems();
                    
                    //Do ben Remove Servlet luon luon gan = null khi ko co -> chi can ktra != null
                    if (items != null) {
                        //4. Cust return item by items form cart
                        String[] selectedItems =  request.getParameterValues("chkItem");
                        if (selectedItems != null) {
                            //5. System removes item by item from cart
                            //Maybe Have Exception
                            for (String item: selectedItems) {
                                cart.removeItemFromCart(item);
                                
                            } //end remove
                                   
                            //Gio nam trong tay minh -> Phai setAttribute
                            session.setAttribute("CART", cart);
                            
                        } //end selectedItem has existed
                    } //end of items has existed
                } // end cart has existed
            } //end session has existed
            
        } finally {
            //6. System refresh cart
            //call view cart function again using url rewriting
//            String urlRewriting = "DispatchController"
//                                + "?btAction=View Your Cart";
            ServletContext context = this.getServletContext();
            Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
            String urlRewriting = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.VIEW_CART_CONTROLLER);
            response.sendRedirect(urlRewriting);
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
