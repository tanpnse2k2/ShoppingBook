/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
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
import tanpn.order.OrderDAO;
import tanpn.product.ProductDAO;
import tanpn.product.ProductDTO;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
@WebServlet(name = "LoadProductListServlet", urlPatterns = {"/LoadProductListServlet"})
public class LoadProductListServlet extends HttpServlet {
//    private final String SHOPPING_PAGE = "shopping.jsp";

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
        String url = siteMaps.getProperty(MyApplicationConstants.ViewProductFeature.SHOPPING_PAGE);
        
        try  {
            
            //Add list Product to session to use for all session of this USER
            HttpSession session = request.getSession();
            ProductDAO dao = new ProductDAO(); 
            dao.showProduct();
            List<ProductDTO> productList = dao.getProductList();
            
            //Load List order Details
            OrderDAO orderDAO = new OrderDAO();
            orderDAO.loadQuantityListBuy();
            Map<String, Integer> ListWasBuy = orderDAO.getQuantityListBuy();
            
            //If (Quanity of Product > quantiy Was Buy -> Load
           
            //We cannot remove object while loops -> ConCurrentModificationException
            // -> Use Iterator
//            Iterator<ProductDTO> itr = productList.iterator();
//            while (itr.hasNext()) {
//                ProductDTO product = itr.next();
//                String sku = product.getSku();
//                
//                int quantityWasBuy = ListWasBuy.get(sku);
//                int quantityOfProduct = product.getQuantity();
//                
//              quantityOfProduct == quantityWasBuy it means SOLD OUT this PRODUCT!!!
//              => need to remove from List to Load to User 
//                if (quantityOfProduct == quantityWasBuy) {
//                    //It also affect on the List
//                    itr.remove();
//                }
                
//            }
            
            session.setAttribute("PRODUCTLIST", productList);
            
            //Load Product Name for use in User CART
            Map<String, String> productName = new  HashMap<>();
            
            for (ProductDTO product: productList) {
                String sku = product.getSku();
                String name = product.getName();
                productName.put(sku, name);
                
            
            }
            
            session.setAttribute("PRODUCTNAME", productName);
            
        } catch  (NamingException ex) {
            log("Load ProductList Naming... " + ex.getMessage());
        } catch  (SQLException ex) {
            log("Load ProductList SQL... " + ex.getMessage());
        }
        
        finally {
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
