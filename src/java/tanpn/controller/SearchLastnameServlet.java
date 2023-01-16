/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
import tanpn.registration.RegistrationDAO;
import tanpn.registration.RegistrationDTO;
import tanpn.registration.RegistrationDeleteError;
import tanpn.registration.RegistrationUpdateError;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
@WebServlet(name = "SearchLastnameServlet", urlPatterns = {"/SearchLastnameServlet"})
public class SearchLastnameServlet extends HttpServlet {
//    private final String SEARCH_PAGE = "search.html";
//    private final String SEARCH_RESULT_PAGE = "search.jsp";
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
        //Co tri la co gia tri != chuoi rong
        String searchValue = request.getParameter("txtSearchValue");

        
        
       // String url = SEARCH_RESULT_PAGE;
        ServletContext context = this.getServletContext();
        Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
        String url = siteMaps.getProperty(MyApplicationConstants.LoginFeature.SEARCH_PAGE);
       
        try {
            //1. Check value search Value
            if (searchValue.trim().length() > 0) {
                //2. call DAO
                RegistrationDAO dao = new RegistrationDAO();
                dao.searchLastname(searchValue);
                //3. process result
                //Result la dong - tuy theo keyword ma result thay doi
                List<RegistrationDTO> result = dao.getAccountList();
                request.setAttribute("SEARCH_RESULT", result);
                
                
              //  url = siteMaps.getProperty(MyApplicationConstants.LoginFeature.SEARCH_PAGE);
//                url = MyApplicationConstants.LoginFeature.SEARCH_PAGE;
            } //end search Value has valid value
            
          
            
        } catch (NamingException ex){
            log("Search LastName Naming... " + ex.getMessage());
        } catch (SQLException ex) {
            log("Search LastName SQL... " + ex.getMessage());
        } finally {
             //De chuyen tu Controller sang View chon RequestDispatcher -> (SendRedirect)Do Response ve browser roi -> Du lieu o Server bi huy 
             RequestDispatcher rd = request.getRequestDispatcher(url);
             rd.forward(request, response);
             
//             HttpSession session = request.getSession();
//             
//             if (session.getAttribute("UPDATEERRORS") != null) {
//                session.removeAttribute("UPDATEERRORS");
//             }
//             if (session.getAttribute("DELETEERRORS") != null) {
//                session.removeAttribute("DELETEERRORS");
//         
//             }
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
