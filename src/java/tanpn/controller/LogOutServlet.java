/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import tanpn.utils.MyApplicationConstants;

/**
 *
 * @author Nhat Tan
 */
@WebServlet(name = "LogOutServlet", urlPatterns = {"/LogOutServlet"})
public class LogOutServlet extends HttpServlet {
//    private final String SEARCH_PAGE = "search.jsp";
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
        
//        ServletContext context = this.getServletContext();
//        Properties siteMaps = (Properties) context.getAttribute("SITE_MAPS");
//        String url = siteMaps.getProperty(MyApplicationConstants.LoginFeature.SEARCH_PAGE);
        
        try {
            //1. Get Session dang hien co
            //Get false boi vi ko ton tai tra ve null -> Ko can lam tiep nua
            HttpSession session = request.getSession(false);
            
            //2. If session != null -> xoa toan bo attribute co trong Session
            if (session != null) {
                
                //Delete Session
                session.invalidate();
                
                //Delete Cookie for Next Time dont automatically login
                
                //Read all cookies Sended from Request
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    //SetMaxAge = 0 - Delete inmediately
                
                    //Delete All Cookies
                    /*
                    for (int i = 0; i < cookies.length; i ++) {
                        cookies[i].setMaxAge(0);
                        response.addCookie(cookies[i]);

                    }
                    */

                    //Delete the last cookie - it means Cookie is using
                    int decrease = 1;
                    if (cookies[cookies.length - 1].getName().equals("JSESSIONID")) {
                        decrease++;
                    }

                    cookies[cookies.length - decrease].setMaxAge(0);

                    //Update Cookies
                    response.addCookie(cookies[cookies.length - decrease]);
                }
                
                
//                url = siteMaps.getProperty(MyApplicationConstants.DispatchFeature.LOGIN_PAGE);
              //  url = MyApplicationConstants.DispatchFeature.LOGIN_PAGE;
                
            } //end of invalidate session
            
        } catch(ArrayIndexOutOfBoundsException ex) {
            log("LogOut ArrayIndexOutOfBounds...." + ex.getMessage());
        
        } finally {
            String url = MyApplicationConstants.DispatchFeature.LOGIN_PAGE;
            //Luc nay la 1 session ko co attribute -> Ko co du lieu can luu tru de xu li
            // -> Dung sendRedirect nham muc dich tiet kiem bo nho, thoi gian
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
