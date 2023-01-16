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
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tanpn.hashsha256.hashSHA256;
import tanpn.registration.RegistrationDAO;
import tanpn.registration.RegistrationUpdateError;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
@WebServlet(name = "UpdateSevlet", urlPatterns = {"/UpdateSevlet"})
public class UpdateSevlet extends HttpServlet {
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
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String isRole = request.getParameter("chkAdmin");
        String searchValue = request.getParameter("lastSearchValue");
        boolean errorFound = false;
        RegistrationUpdateError errors = new RegistrationUpdateError();
       
//        ServletContext context = this.getServletContext();
//        SendRedirect -> Dung filter cho nhanh
//        Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
//        String url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.ERROR_PAGE);
        String url = MyApplicationConstants.DispatchFeature.ERROR_PAGE;

        //Need to remove Attribute ERRORS first for the next time may be dont have Errors
        HttpSession session = request.getSession();
        
       
        try {
            
            //Neu Password la chuoi trang (User dont want to update) -> Ko can check loi ma di update luon
            //Ko can kiem tra gi ca vi updateInformation da check ky tu trang r
            if ((password.trim().length() < 6 || password.trim().length() > 30) && (password.trim().length() > 0)) {
                errorFound = true;
                errors.setPasswordLengthError("Password is required input from 6 to 30 characters");
                errors.setUsernameWithPasswordLengthError(username);
                //System.out.println("Username: " + username + " password: " + password);
            } 
            
            if (errorFound) {
               
                session.setAttribute("UPDATEERRORS", errors);
            
            } else {
            
                RegistrationDAO dao = new RegistrationDAO();
               
                boolean result = dao.updateInformation(username, password.trim(), isRole);
                
                    
                
            }
            
//            url = "DispatchController"
//                            + "?btAction=Search"
//                            + "&txtSearchValue=" + searchValue;
        
            //Ap dung filter - ko can btAction vi map thang toi search thong qua searchLastNameController
            url = "searchLastNameController?"
                    + "&txtSearchValue=" + searchValue;
            
        }  catch (NamingException ex) {
            log("Update Account Naming... " + ex.getMessage());
        } catch (SQLException ex) {
            log("Update Account SQL... " + ex.getMessage());
        }
        
        finally {
            response.sendRedirect(url);
           
//            RequestDispatcher rd = request.getRequestDispatcher(url);
//            rd.forward(request, response);
        
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
