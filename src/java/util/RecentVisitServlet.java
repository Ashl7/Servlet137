/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Arash
 */
public class RecentVisitServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private ArrayList<String> recentVisitID;
            

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {    
      
      
        ArrayList<String> recentPages = null;
        HashMap<Integer,String> id_image = null;
        HttpSession session = request.getSession(false);
        if(session != null) {
            recentPages = (ArrayList<String>)session.getAttribute("visited");
            id_image = (HashMap<Integer,String>)session.getAttribute("id_image");
        }
      
        response.setContentType("text/html");     
     
        try (PrintWriter out = response.getWriter()) {
              // Recently Visted Items
              out.println(" <hr>"); 
              out.println("<p style=\"text-align: center\">Recently Visited Items</p>");
              out.println("<table id=\"visited\" border=\"1\">   ");
              out.println("<tr>");
              if (recentPages != null) {          
                  if (recentPages.size() > 0) {
                      out.println("<p>in if block </p>");
                      int counter = 0;
                      for(int i = recentPages.size()-1; i >= 0; i--) {
                          if (counter > 4) {
                              break;
                          }
                          out.println("<td><a href=\"SingleItemServlet?id=" + recentPages.get(i) + "\">");                            
                          out.println("<img src=\"img/hats/" + id_image.get(Integer.valueOf(recentPages.get(i))) + "\" alt =\"" + id_image.get(Integer.valueOf(recentPages.get(i))) +"\" align = \"middle\" height=\"100\" weight = \"100\"></a>");
                          out.println("</td>");
                          counter++;
                      }
                  }
                  else {
                      out.println("<p>in else block </p>");
                  }
              }

              out.println("</tr>");
              out.println("</table>");  
              out.println("<p>Session ID: " + session.getId() + "</p>"); 

              // FOOTER
              out.println("<div id=\"footer\">");
              out.println("Copyright © HatSpace.com");
              out.println("</div> ");
                
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
