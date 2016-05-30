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
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Arash 
 * 
 * Ana should implement her part in this Servlet
 */
public class SingleItemServlet extends HttpServlet {
    
    // Hashmap holding the hit count for every item
    private HashMap<Integer,Integer> hitCount;    
    // Array holding all hats in the database
    private ArrayList<Hat> hatArray;
    
    
    public void init() { 
        
        // If the container is restarted or destroyed, hit counter resets
        hitCount = new HashMap<Integer,Integer>(); 
        hatArray = new ArrayList<Hat>(); 
        
        loadData();      
    } 
    
    
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
            
            // Execute the query
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
                hitCount.put(hat.getId(), 0);
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
               
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // increase the hit count when doGet or doPost is called
        // use it to display the number of hits on webpage 
        Integer count = hitCount.get(Integer.valueOf(request.getParameter("id")));
        count++;
        hitCount.put(Integer.valueOf(request.getParameter("id")), count);
        
        
        // update session variable visited to have this recently visited page in it
        ArrayList<String> recentPages = null;
        HttpSession session = request.getSession(false);
        recentPages = (ArrayList<String>)session.getAttribute("visited");
        recentPages.add(request.getParameter("id"));
        session.setAttribute("visited", recentPages);
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SingleItemServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SingleItemServlet: Shows details of each product</h1>");
            out.println("<p>Product ID: " + request.getParameter("id") + "</p>");
            out.println("<p>Hit Counts: " + count + "</p>");
            
            if (recentPages.size() > 0) {
                out.println("<p>in if block </p>");
                for(int i = recentPages.size()-1; i >= 0; i--) {
                    out.println("<p>visited ID: " + recentPages.get(i) + "</p>");                        
                }
            }
            else {
                out.println("<p>in else block </p>");
            }
            
            out.println("</body>");
            out.println("</html>");
        }

    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
