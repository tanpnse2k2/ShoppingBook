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
import tanpn.hashsha256.hashSHA256;
import tanpn.registration.RegistrationCreateError;
import tanpn.registration.RegistrationDAO;
import tanpn.registration.RegistrationDTO;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
@WebServlet(name = "CreateNewAccountServlet", urlPatterns = {"/CreateNewAccountServlet"})
public class CreateNewAccountServlet extends HttpServlet {
//    private final String ERROR_PAGE = "createNewAccount.jsp";
//    private final String LOGIN_PAGE = "login.html";
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
        String url = siteMaps.getProperty(MyApplicationConstants.CreateNewAccountFeature.ERRORS_CREATE_ACCOUNT_PAGE);
        
        String username = request.getParameter("txtUsername");
        String password = request.getParameter("txtPassword");
        String fullName = request.getParameter("txtFullname");
        String confirm = request.getParameter("txtConfirm");
        boolean errorFound = false;
        //Loi nguoi dung tao ra phai luu lai Object va tao ra 1 Attribute
        RegistrationCreateError errors = new RegistrationCreateError();
        
        try {
            //1. Check user's error
            if (username.trim().length() < 6 || username.trim().length() > 20) {
                errorFound = true;
                errors.setUsernameLengthError("Username is required input from 6 to 20 characters");
            }
            
            if (password.trim().length() < 6 || password.trim().length() > 30) {
                errorFound = true;
                errors.setPasswordLengthError("Password is required input from 6 to 30 characters");
            } else if (!confirm.trim().equals(password.trim())) {
                errorFound = true;
                errors.setConfirmLengthError("Confirm must be matched password");
            
            }
            if (fullName.trim().length() < 2 || fullName.trim().length() > 50) {
                errorFound = true;
                errors.setFullNameLengthError("Full Name is required input from 2 to 50 characters");
            }
            if (errorFound) {
               // System.out.println("asasaww HEHEHWIDHWD");
//              1.1 cache store
                request.setAttribute("CREATE_ERRORS", errors);
//              1.2 show errors to user
            } else {
                //Never null -> neednt test null
                hashSHA256 hash = new hashSHA256();
                String hashPassword = hash.HashPassword(password);
                //2. Insert to DB -> call DAO
                RegistrationDAO dao = new RegistrationDAO();
                RegistrationDTO dto = new RegistrationDTO(username, hashPassword, fullName, false);
                boolean result = dao.createAccount(dto);
                if (result) {
                    url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOGIN_PAGE);
                
                } //create is successfully
                //3. Check system's error
            }
            
        } catch (SQLException ex) {
            String errMsg = ex.getMessage();
            //Ten Class Dang dung, loai loi~, ten loi~
            log("Create Account SQL " + errMsg);
            
            if (errMsg.contains("duplicate")) {
                errors.setUsernameIsExisted(username + " is Existed!!");
                request.setAttribute("CREATE_ERRORS", errors);
            }
        
        } catch (NamingException ex) {
            log("Create Account Naming " + ex.getMessage());
        
        }finally {
            //4. Transfer specified page
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
