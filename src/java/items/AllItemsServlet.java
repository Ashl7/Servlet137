/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



package items;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Driver;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author Arash
 * 
 * TO NEERAJ: Basically this class needs to show the last two items on the 
 * zip_codes database
 */
public class AllItemsServlet extends HttpServlet {

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
        
        ArrayList<Hat> hatArray = new ArrayList<Hat>();        
          
        final String url = "jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124grp30";
        final String dbname = "inf124grp30";
        final String username = "inf124grp30";
        final String password = "st#VuY6R";    
        
        String state = "";
        String city = "";       

        Connection connection = null;
        Statement statement = null; 
        ResultSet resultSet = null;
        try {
            // Load the MYSQL JDBC driver.
            Class.forName("com.mysql.jdbc.Driver"); 
            
            //Open a connection
            connection = DriverManager.getConnection(url,username,password);
            state = connection.toString();
            if (connection == null) {
               state = "conn was null";               
            }

           //STEP 4: Execute a query
           statement = connection.createStatement();
           String sqlQuery;
           sqlQuery = "SELECT state, city FROM zip_codes";
           // sqlQuery = "SELECT * FROM hats";
           resultSet = statement.executeQuery(sqlQuery);           
           
           if (resultSet==null) {
               city = "rs was null";
           }
           
           //STEP 5: Extract data from result set
           try {
                while(resultSet.next()) {
                   //Retrieve by column name 
                   state = resultSet.getString("state");
                   city = resultSet.getString("city");

                   /*
                   Hat hat = new Hat();

                   hat.setId(resultSet.getInt("id"));
                   hat.setTitle(resultSet.getString("title"));
                   hat.setType(resultSet.getString("type"));
                   hat.setColor(resultSet.getString("color"));
                   hat.setPrice(resultSet.getInt("price"));
                   hat.setMaterial(resultSet.getString("material"));
                   hat.setImage_url(resultSet.getString("image_url"));
                   hat.setDesc(resultSet.getString("description"));

                   hatArray.add(hat);        
                   */
                }      
                resultSet.close();
                statement.close();
                connection.close();                                   
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                processRequest(request, response);
            }
        }
        
        catch(Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                processRequest(request, response);
        }

           
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet all_items</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet all_items at " + request.getContextPath() + "</h1>");
            out.println("<p>" + "City oh oh= " + city + " State=" + state + "</p>");
            out.println("</body>");
            out.println("</html>");         
            
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

