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
import util.ServletSessionCounter;

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
        
        //Counting the number of viewers(active session)
        ServletSessionCounter ssc = new ServletSessionCounter();
        int sessionCount = ssc.getActiveSession();
        
        
        // update session variable visited to have this recently visited page in it
        ArrayList<String> recentPages = null;
        HttpSession session = request.getSession(false);
        recentPages = (ArrayList<String>)session.getAttribute("visited");
        recentPages.add(request.getParameter("id"));
        session.setAttribute("visited", recentPages);
        
        int id = Integer.parseInt(request.getParameter("id"));
        String name = "", image = "", color = "", material = "", description = "";
        int price = 0;
        
        for(Hat item : hatArray){
            if(item.getId() == id){
                name = item.getTitle();
                image = item.getImage_url();
                color = item.getColor();
                material = item.getMaterial();
                description = item.getDesc();
                price = item.getPrice();
            }
        }
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Browse Our Collection</title>");  
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/item_description.css\">");
            
            // BODY
            out.println("<body>");
            
            // HEADER
            out.println(" <div id=\"header\">");
            out.println("<h1>HatSpace</h1>");
            out.println("<form>");
            out.println("<input id=\"search\" type=\"text\" placeholder=\"Search\">");
            out.println("</form>");
            out.println("</div>");
            
            out.println("<img src=\"img/hats/" + image + "\" alt =\"" + name + "\"/>");
            out.println("<div>");
            out.println("<ul>");
            out.println("<li>Name: "+name + "</li>");
            out.println("<li>Color:"+ color +"</li>");
            out.println("<li>Material:"+ material +"</li>");
            out.println("<li>Price:"+ price +"</li>");
            out.println(" </ul>");
            out.println("</div>");
            out.println("<div class =\"description\">");
            out.println(description);
            out.println("</div>");
            out.println("</div>");
   
                     
            
            
            // Check out 
            out.println("<div>");
            out.println("<form method=\"get\" action=\"SubmitOrderServlet\">");
            out.println("<button type=\"submit\">Check Out Cart</button>");
            out.println("</form>");
            
            out.println("<form name=\"AddToCart\" action=AddToCart method=\"get\">");
            out.println("<input type=\"hidden\" name=\"clicked_button\" id=\"clicked_button\" value=\"" + id +"\"/>");
            out.println("<button type=\"submit\" >Add to Cart</button>");
            out.println("</form>");
            out.println("<form method=\"get\" action=\"SubmitOrderServlet\">");
            out.println("<button type=\"submit\">Check Out Cart</button>");
            out.println("</form>");
            out.println("</body>");
            out.println("</html>");
            out.println("</div>");
            
            
            // FOOTER
            out.println("<div id=\"footer\">");
            out.println("Copyright © HatSpace.com");
            out.println("</div> ");
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
