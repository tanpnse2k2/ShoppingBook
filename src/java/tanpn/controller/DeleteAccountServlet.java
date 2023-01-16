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
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tanpn.registration.RegistrationDAO;
import tanpn.registration.RegistrationDTO;
import tanpn.registration.RegistrationDeleteError;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
@WebServlet(name = "DeleteAccountServlet", urlPatterns = {"/DeleteAccountServlet"})
public class DeleteAccountServlet extends HttpServlet {
    //Tao trang loi khi co loi chuyen qua trang do
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
        PrintWriter out = response.getWriter();
        
        //Get Listener
//        ServletContext context = this.getServletContext();
//        Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
//        String url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.ERROR_PAGE);
        String url = MyApplicationConstants.DispatchFeature.ERROR_PAGE;
        
        String username = request.getParameter("pk");
        String searchValue = request.getParameter("LastSearchValue");
        boolean errorFound = false;
        RegistrationDeleteError errors = new RegistrationDeleteError();

        //Get Session to Save the Errors
        HttpSession session = request.getSession();
        
        
        RegistrationDTO user = (RegistrationDTO) session.getAttribute("USER");
        
        try {
            //If delete user is logging -> Error 
            if (user.getUsername().equals(username)) {
                errorFound = true;
                errors.setDeleteUserIsLoggingError("Can not delete User is Logging");
            }
            
            if (errorFound) {
                //1. Set Attribute about Cannot delete
                session.setAttribute("DELETEERRORS", errors);
            
            } else {
                //1. Call DAO
                RegistrationDAO dao = new RegistrationDAO();
                boolean result = dao.deleteAccount(username);
            }
            //2. Refresh -> Call the Search function again using URL Rewriting Technology
//            url = "DispatchController"
//                            + "?btAction=Search"
//                            + "&txtSearchValue=" + searchValue;
            url = "searchLastNameController?"
                    + "&txtSearchValue=" + searchValue;
          
        } catch (SQLException ex) {
            log("Delete Account SQL..." + ex.getMessage());
        
        } catch (NamingException ex)  {
            log("Delete Account Naming...." + ex.getMessage());
        } finally{
            //Dung sendRedirect vi neu dung RD se bi tao mang do trung` btAction -> Tao mang (Delete, search) -> Ko biet dung cai j
            response.sendRedirect(url);
            //Khong delete Attribute o day duoc vi chua forward toi thi da mat
            
            //-> URL Rewriting 100% dung` sendRedirect
            //Van Security do che duong truyen, nhung ko duy tri Request Obj
            
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
