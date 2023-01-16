/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tanpn.listener;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import tanpn.utils.DBHelper;

/**
 * Web application lifecycle listener.
 *
 * @author Nhat Tan
 */
public class MyContentServletListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
//        System.out.println("deploying..................");
//        ServletContext context = sce.getServletContext();
//        String siteMapFile = context.getInitParameter("SITE_MAP_FILE");
//        try {
//            Properties siteMaps = DBHelper.getSiteMaps(siteMapFile, context);
//            
//            context.setAttribute("SITE_MAPS", siteMaps);
//        } catch (IOException ex) {
//            //Ban chat LOG duoc ho tro trong Servlet -> Co Scope la co the dung
//            // -> Cho nao` khac ngoai Scope thi dung Scope de get Log
//            context.log("Context Listener IO " + ex.getMessage());
//        }

        System.out.println("depoying...............");
        ServletContext context = sce.getServletContext();
        String siteMapFile = context.getInitParameter("SITE_MAP_FILE");
        //Bay gio co site map file duoi dang String -> Can chuyen ve Properties
        try {
            Properties siteMaps = DBHelper.getSiteMaps(siteMapFile, context);
            context.setAttribute("SITE_MAPS", siteMaps);
            //Now have SITE_MAPS properties
        
        } catch (IOException ex) {
            context.log("Context Lisenter IO " + ex.getMessage());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("RIP");
    }
}
