/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.controller;

//import com.sun.org.apache.xpath.internal.axes.HasPositionalPredChecker;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
@WebServlet(name = "FirstRequestServlet", urlPatterns = {"/FirstRequestServlet"})
public class FirstRequestServlet extends HttpServlet {
//    private String LOGIN_PAGE = "login.html";
//    private String SEARCH_PAGE = "search.jsp";
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
//        ServletContext context = this.getServletContext();
//        Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
        String url = MyApplicationConstants.DispatchFeature.LOGIN_PAGE;
        
        try {
            //1. get all cookies
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
            //2. get last cookies
            Cookie lastCookie = cookies[cookies.length - 1];
            
            //3. get username and password form cookies
            String username = lastCookie.getName();
            String password = lastCookie.getValue();
            
            //4. call DAO
            RegistrationDAO dao = new RegistrationDAO();
//            boolean result = dao.checkLogin(username, password);
            hashSHA256 hash = new hashSHA256();
            String hashPassword = hash.HashPassword(password);
            RegistrationDTO result = dao.checkLogin(username, hashPassword);
                
            //5. process
//            if (result)
            if (result != null) {
                //First time login, if have session -> use, no -> create => use True
                HttpSession session = request.getSession();
                //Phai set trung ten voi ben Login, boi vi JSP get Attribute ten USER
                session.setAttribute("USER", result);
                
                url = MyApplicationConstants.LoginFeature.SEARCH_PAGE;
            
            }
            } //end cookies has existed
        } catch (SQLException ex) {
            log("First Request SQL..." + ex.getMessage());
            
        } catch (NamingException ex){
            log("First Request Naming..." + ex.getMessage());
        }
        
        
        finally {
            //Dung cai gi cung~ duoc vi ko can quan tam chuyen luu username nua hay ko vi Username nam trong Cookie
            //Muon che duong truyen thi dung RequestDispatcher
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
