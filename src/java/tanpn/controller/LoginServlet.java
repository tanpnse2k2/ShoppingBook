/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tanpn.hashsha256.hashSHA256;
import tanpn.registration.RegistrationDAO;
import tanpn.registration.RegistrationDTO;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
public class LoginServlet extends HttpServlet {
//    private final String SEARCH_PAGE = "search.jsp";
//    private final String INVALID_PAGE = "invalid.html";
    
    //Khong map siteMaps thi se bi 404
//      private final String SEARCH_PAGE = "searchPage";
//      private final String INVALID_PAGE = "invalidPage";
      
//      Bay gio ko map tat ca cac file nua
   
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
//      Because have DispatchController check btAction -> no need to check again (btAction, Login button) -> get Username, Password

        ServletContext context = this.getServletContext();
        //Vi day la dang dung Foward Dispatcher -> Ko co lien quan gi FIlter nen ko dung dc Filter
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
        String url = siteMaps.getProperty(MyApplicationConstants.LoginFeature.INVALID_PAGE);
//        String url = INVALID_PAGE;
        String username = request.getParameter("txtUserName");
        String password = request.getParameter("txtPassword");
        //String button = request.getParameter("btAction");
        try  {
            
           // if (button.equals("Login")) {
              
            
                //1. call Model/DAO
                //1.1 new Object
                
                //out.print(username + "  " + password);
                RegistrationDAO dao = new RegistrationDAO();
                
                //1.2 call method of that obj
//                boolean result = dao.checkLogin(username, password);
                hashSHA256 hash = new hashSHA256();
                String hashPassword = hash.HashPassword(password);
                RegistrationDTO result = dao.checkLogin(username, hashPassword);
                
//                if (result)
                if (result != null) {
                    
//                    url = SEARCH_PAGE;
                    
                    //Thay vi dung Cookie thi dung Attribute
                    //getSession la true vi luc nay can tao moi Attribute
                    HttpSession session = request.getSession(true);
                    session.setAttribute("USER", result);
                    
                    Cookie cookie = new Cookie(username, password);
                    cookie.setMaxAge(60 * 3);
                    response.addCookie(cookie);
                    //cookie = new Cookie(username, url)
                    //Login thanh cong nen tao cookies
                    url = siteMaps.getProperty(MyApplicationConstants.LoginFeature.SEARCH_PAGE);
                     
                   
                }
           // }
        } //end of clicked Login //end of clicked Login //end of clicked Login //end of clicked Login
        catch (SQLException ex) {
                log("Login SQL...." + ex.getMessage());
                        
                
                }
        catch (NamingException ex) {
                log("Login Naming...." + ex.getMessage());
                
                }
         finally {
            //Neu dung RequestDispatcher -> Server chua gui Response ve cho Client -> Ko co Cookie -> 500 - NullPointerException
            //Dung sendRedirect -> Server gui 1 response ve, Trong khi do tao 1 resource khac di den URL SearchPage
            //Ko anh huong den qua trinh search vi ko co search gi ca
//            response.sendRedirect(url);
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
//            out.close();
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
