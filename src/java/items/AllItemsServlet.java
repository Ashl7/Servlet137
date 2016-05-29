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
    private ArrayList<Hat> hatArray;
    
    // Loads the data(hats) from database into the hatArray object
    private void loadData() {
        
        hatArray = new ArrayList<Hat>();
        
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
            
            // Execute a query
            statement = connection.createStatement();
            String sqlQuery;           
            sqlQuery = "SELECT * FROM hats";
            resultSet = statement.executeQuery(sqlQuery);           

            // Extract data from result set            
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
            out.println("<table id=\"items\">");
            //      First row
            out.println("<tr>");
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(0).getId() + "\">");   
            out.println("<img src=\"img/hats/" + hatArray.get(0).getImage_url() + "\" alt =\"" + hatArray.get(0).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(0).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(0).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(0).getMaterial() + "</p></td>");
            
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(1).getId() + "\">");   
            out.println("<img src=\"img/hats/" + hatArray.get(1).getImage_url() + "\" alt =\"" + hatArray.get(1).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(1).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(1).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(1).getMaterial() + "</p></td>");
            
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(2).getId() + "\">");   
            out.println("<img src=\"img/hats/" + hatArray.get(2).getImage_url() + "\" alt =\"" + hatArray.get(2).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(2).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(2).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(2).getMaterial() + "</p></td>");
            
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(3).getId() + "\">");   
            out.println("<img src=\"img/hats/" + hatArray.get(3).getImage_url() + "\" alt =\"" + hatArray.get(3).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(3).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(3).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(3).getMaterial() + "</p></td>");

            out.println("</tr>"); 
            
            //      Second row
            out.println("<tr>");
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(4).getId() + "\">");   
            out.println("<img src=\"img/hats/" + hatArray.get(4).getImage_url() + "\" alt =\"" + hatArray.get(4).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(4).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(4).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(4).getMaterial() + "</p></td>");
            
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(5).getId() + "\">");   
            out.println("<img src=\"img/hats/" + hatArray.get(5).getImage_url() + "\" alt =\"" + hatArray.get(5).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(5).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(5).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(5).getMaterial() + "</p></td>");
            
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(6).getId() + "\">");  
            out.println("<img src=\"img/hats/" + hatArray.get(6).getImage_url() + "\" alt =\"" + hatArray.get(6).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(6).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(6).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(6).getMaterial() + "</p></td>");
            
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(7).getId() + "\">");   
            out.println("<img src=\"img/hats/" + hatArray.get(7).getImage_url() + "\" alt =\"" + hatArray.get(7).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(7).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(7).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(7).getMaterial() + "</p></td>");

            out.println("</tr>"); 
            
            
            //      Third row
            out.println("<tr>");
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(8).getId() + "\">");   
            out.println("<img src=\"img/hats/" + hatArray.get(8).getImage_url() + "\" alt =\"" + hatArray.get(8).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(8).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(8).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(8).getMaterial() + "</p></td>");
            
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(9).getId() + "\">");   
            out.println("<img src=\"img/hats/" + hatArray.get(9).getImage_url() + "\" alt =\"" + hatArray.get(9).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(9).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(9).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(9).getMaterial() + "</p></td>");
            
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(10).getId() + "\">");   
            out.println("<img src=\"img/hats/" + hatArray.get(10).getImage_url() + "\" alt =\"" + hatArray.get(10).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(10).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(10).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(10).getMaterial() + "</p></td>");
            
            out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(11).getId() + "\">");  
            out.println("<img src=\"img/hats/" + hatArray.get(11).getImage_url() + "\" alt =\"" + hatArray.get(11).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
            out.println("</a>");
            out.println("<p>Price: " + hatArray.get(11).getPrice() + "</p>");
            out.println("<p>Color: " + hatArray.get(11).getColor() + "</p>");
            out.println("<p>Material: " + hatArray.get(11).getMaterial() + "</p></td>");

            out.println("</tr>"); 
            
            out.println("</table>");
            
            // Recently Visted Items
            out.println(" <hr>");
            out.println("<p style=\"text-align: center\">Recently Visited Items</p>");
            out.println("<table id=\"visited\" border=\"1\">   ");
            out.println("<tr>");
            out.println("<td><a href=\"php/one_page.php?id=2\">");
            out.println("<img src=\"img/hats/armenian_bucket.jpg\" alt =\"Armenian Hat\" align = \"middle\" height=\"100\" weight = \"100\"></a>");
            out.println("</td>");
            out.println("<td><a href=\"php/one_page.php?id=2\">");
            out.println("<img src=\"img/hats/armenian_bucket.jpg\" alt =\"Armenian Hat\" align = \"middle\" height=\"100\" weight = \"100\">");
            out.println("</a></td>");
            out.println("<td><a href=\"php/one_page.php?id=2\">");
            out.println("<img src=\"img/hats/armenian_bucket.jpg\" alt =\"Armenian Hat\" align = \"middle\" height=\"100\" weight = \"100\">");
            out.println("</a></td>");
            out.println("<td><a href=\"php/one_page.php?id=2\">");
            out.println("<img src=\"img/hats/armenian_bucket.jpg\" alt =\"Armenian Hat\" align = \"middle\" height=\"100\" weight = \"100\">");
            out.println("</a></td>");
            out.println("<td><a href=\"php/one_page.php?id=2\">");
            out.println("<img src=\"img/hats/armenian_bucket.jpg\" alt =\"Armenian Hat\" align = \"middle\" height=\"100\" weight = \"100\">");
            out.println("</a></td>");
            out.println("</tr>");
            out.println("</table>"); 
            
           
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

