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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import tanpn.order.OrderDAO;
import tanpn.product.ProductDAO;
import tanpn.product.ProductDTO;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {
//    private final String ERROR_PAGE = "error.html";
//    private final String TOO_MUCH_QUANTITY_PAGE = "removequantity.html";
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
            //1. Customer goes to cart placement
            HttpSession session = request.getSession();
            
            if (session != null) {
                //2. Customer take his/her cart
                CartObject cart = (CartObject) session.getAttribute("CART");
                if (cart != null) {
                    //3. Customer get all items
                    //Just Need to Check Enough Quantiy Because View Cart -> Check Out -> SKU has Existed!!
                    
                    
                    
                    
                    OrderDAO dao = new OrderDAO();
                    
                    ProductDAO productDAO = new ProductDAO();
                    
                    //Need: Quantity of Product, Quantity of OrderDetails, Quantity of Customer want to Buy
                    //Quantity of User want to Buy
                    Map<String, Integer> items = cart.getItems();
                    
                    //Quantity of Product - We also have ProductDTO when LoadProductServlet in session
                    List<ProductDTO> ListOfProduct = (ArrayList<ProductDTO>) session.getAttribute("PRODUCTLIST");
                    
                    //Quantity of OrderDetails
                    dao.loadQuantityListBuy();
                    Map<String, Integer> quantityOfOrderDetails = dao.getQuantityListBuy();
                    
                    //3.1 Check if Product Quantity is Enough to Provide to Customer
                    boolean isEnoughQuantity = true;
                    for (ProductDTO product: ListOfProduct) {
                        String sku = product.getSku();
                        
                        int quantityInDB = product.getQuantity();
                        //If Was Buy then Add Value 
                        int quantityWasBuy = 0;
                        if (quantityOfOrderDetails.get(sku) != null){
                            quantityWasBuy = quantityOfOrderDetails.get(sku);
                        }
                        if (items.get(sku) != null) {
                            int quantityCustWantBuy = items.get(sku);

                            if (quantityInDB < quantityWasBuy + quantityCustWantBuy) {
                                //O day co the quang loi, xoa Items do cua Khach Hang
                                isEnoughQuantity = false;
                                //break;
//                              Quang loi + xoa item do
                                items.remove(sku);
                                
                            }
                        }
                    }
                    //If Enough Quantity -> Update Systems
                    //Else Back to ViewCart and noti to Remove any Produt
                    
                    if (isEnoughQuantity) {
                        //4. System updates
                        //4.1 Update Order 
                        
                        float total = 0;
                        Map<String, Float> priceOfProduct = new HashMap<>();
                        //Use Map for next time use, not need to call dao.getPrice again
                        
                        //4.1.1 Calculate total Price
                        
                        for (String key: items.keySet()) {
                            Float price = productDAO.getPriceOfProduct(key);
                            priceOfProduct.put(key, price);
                            
                            total += price * items.get(key);
                            
                        }
                        //4.1.2 Update To Order Table
                        dao.UpdateOrderTable(total);
                        
                        //4.2 Update OrderDetails
                        //4.2.1 Get the OrderID
                        //Neednt check is Order ID == null Because Just Add IN 4.1.2
                        int OrderID = dao.getOrderID();
                        //4.2.2 Write Every Product From CART To Order Details
                        for (String key: items.keySet()) {
                            int quantityCustomerBuy = items.get(key);
                            
                            //get Price of Product - was Prepare at 4.1.1
                            float price = priceOfProduct.get(key);
                            
                            //total of Single Product with Quantity
                            total = quantityCustomerBuy * price;
                            
                            //Update the OrderDetails
                            dao.updateOrderDetailsTable(key, OrderID, quantityCustomerBuy, price, total);
                            
                        
                        }
                        
                        //After Update: Remove Attribute
                        session.removeAttribute("CART");
                        
                        //Continue to Shopping
//                        url = "DispatchController"
//                                + "?btAction=View Product";
                        url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.VIEW_PRODUCT_PAGE);
                    } // End of Enough Quantity
                    else {
                        String errors = "We can't provide enough quantity you want to buy. Please check your CART Again!!";
                        request.setAttribute("ERRORS_CART",errors);
                        url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.VIEW_CART_CONTROLLER);
                    
                    }
                    

                    
                    
                } //end of check Cart != null
            } //end of find Session
            
            
        } catch(SQLException ex) {
            log("Check Out SQL... " + ex.getMessage());
        
        } catch(NamingException ex) {
            log("Check Out Naming... " + ex.getMessage());
        } finally {
            //Just delete Attribute CART or Hold the Attribute CART to Remove some Items to CHECK OUT AGAIN
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
        
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
