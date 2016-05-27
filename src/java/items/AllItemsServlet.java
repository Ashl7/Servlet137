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
    
    // Array holding all hats in the database
    private ArrayList<Hat> hatArray = new ArrayList<Hat>();
    
    // Loads the data(hats) from database into the hatArray object
    private void loadData() {
        
        final String url = "jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124grp30";
        final String dbname = "inf124grp30";
        final String username = "inf124grp30";
        final String password = "st#VuY6R";   
        
        Connection connection = null;
        Statement statement = null; 
        ResultSet resultSet = null;
        try {
            // Load the MYSQL JDBC driver.
            Class.forName("com.mysql.jdbc.Driver");             
            
            //Open a connection
            connection = DriverManager.getConnection(url,username,password);       
            
            //STEP 4: Execute a query
            statement = connection.createStatement();
            String sqlQuery;           
            sqlQuery = "SELECT * FROM hats";
            resultSet = statement.executeQuery(sqlQuery);           

            //STEP 5: Extract data from result set            
            while(resultSet.next()) {             
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
             }
            
            resultSet.close();
            statement.close();
            connection.close();  
        }        
        catch(Exception e) {
            e.printStackTrace();
            loadData();
        }
    }
    
    // The content to be shown on the screen when Servlet is loaded
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {                 
        
        loadData();           
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Browse Our Collection</title>");  
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/items_style.css\">");
            
            // JAVASCRIPT
            out.println("<script>");
            out.println("function normalImg(x){");
            out.println("x.style.height = \"200px\";");
            out.println("x.style.width = \"200px\";}");
            out.println("function largeImg(x){");
            out.println("x.style.height = \"400px\";");
            out.println("x.style.width = \"400px\";}");
            out.println("</script>");
            
            out.println("</head>");
            
            // BODY
            out.println("<body>");
            
            // HEADER
            out.println(" <div id=\"header\">");
            out.println("<h1>HatSpace</h1>");
            out.println("<form>");
            out.println("<input id=\"search\" type=\"text\" placeholder=\"Search\">");
            out.println("</form>");
            out.println("</div>");
            
            // ITEMS
            out.println("<table border=\"1\" style=\"width:100%\">");
            out.println("<tr>");
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(0).getId() + "\">");
            out.println("<img src=\"img/hats/" + hatArray.get(0).getImage_url() + "\" alt =\"" + hatArray.get(0).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(0).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(0).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(0).getMaterial() + "</p></td>");
            out.println("");

            
            
            
                    
       
            
            
            
            
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
        return "Servlet to load up all hats from the database and show them in"
                + " search page";
    }// </editor-fold>

}

