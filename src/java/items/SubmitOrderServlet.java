/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package items;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
/**
 *
 * @author Yuki
 */
public class SubmitOrderServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private ShoppingCart cart;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        final String url = "jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124grp30";
        final String dbname = "inf124grp30";
        final String username = "inf124grp30";
        final String password = "st#VuY6R";   
        
        HttpSession session = request.getSession(true);
        cart = (ShoppingCart) session.getAttribute("ShoppingCart");
        HashMap<Integer, Integer> usable = cart.getItems();
        
        
        //try to submit the order info into database
        String firstname = request.getParameter("FirstNameField");
        String lastname = request.getParameter("LastNameField");
        String phonenumber = request.getParameter("PhoneNumberField");
        String streetaddress = request.getParameter("ShippingAddressField");
        String city = request.getParameter("CityField");
        String state = request.getParameter("StateField");
        String zipcode = request.getParameter("ZipCodeField");
        String ccnumber = request.getParameter("CreditCardNumberField");
        String ccname = request.getParameter("CreditCardNameField");
        String expmonth = request.getParameter("ExpMonthField");
        String expyear = request.getParameter("ExpYearField");
        String shippingmethod = request.getParameter("Shipping");
        String email = request.getParameter("EmailAddressField");
        
        
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head>");
        out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.println("<title>Submission Page</title>");
        out.println("</head>");
        out.println("<body>"); 
        out.println("<h2> firstname = " + firstname + "<br/>");
        out.println("lastname = " + lastname + "<br/>");
        out.println("phonenumber = " + phonenumber + "<br/>");
        out.println("streetaddress = " + streetaddress + "<br/>");
        out.println("city = " + city + "<br/>");
        out.println("state = " + state + "<br/>");
        out.println("zipcode = " + zipcode + "<br/>");
        out.println("ccnumber = " + ccnumber + "<br/>");
        out.println("ccname = " + ccname + "<br/>");
        out.println("expmonth = " + expmonth + "<br/>");
        out.println("expyear = " + expyear + "<br/>");
        out.println("shippingmethod = " + shippingmethod + "<br/>");
        out.println("email = " + email + "</h2>");
        
        Connection connection = null;
        //PreparedStatement stmt = null;
        Statement stmt = null; 
        ResultSet rs = null;
        
        
        try {
            // Load the MYSQL JDBC driver.
            Class.forName("com.mysql.jdbc.Driver");             
            
            //Open a connection
            connection = DriverManager.getConnection(url,username,password);       
            stmt = connection.createStatement();
            // Execute a query
            String sqlOrder = "INSERT INTO orders(first_name, last_name, phone, address, city, state, zip, card, name_on_card, month, year, shipping, email) VALUES('"
                    + firstname + "', '" + lastname + "', '" + phonenumber + "', '" + streetaddress + "', '" + city + "', '" + state + "', '" + zipcode + "', '" + ccnumber 
                    + "', '" + ccname + "', '" + expmonth + "', '" + expyear + "', '" + shippingmethod + "', '" + email + "')";
            
            
            //String sqlOrder = "INSERT INTO orders(first_name, last_name, phone, address, city, state, zip, card, name_on_card, month, year, shipping, email) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            out.println("<p> Insert into orders: " + sqlOrder + "</p>");
            
            stmt.executeUpdate(sqlOrder);
            
            
            out.println("<p> orders insert executed</p>");
            String sqlQuery = "SELECT LAST_INSERT_ID()";
            rs = stmt.executeQuery(sqlQuery);
            rs.next();
            Integer orderid = rs.getInt(1);
            for (Map.Entry<Integer, Integer> entry : usable.entrySet()) {
                sqlOrder = "INSERT INTO items_in_cart(hatID, amount, orderID) VALUES("
                        + entry.getKey() + ", " + entry.getValue() + ", " + orderid + ")";
                
                out.println("<p> Insert into items in cart: " + sqlOrder + "</p>");
                stmt.executeUpdate(sqlOrder);
                
                out.println("<p> individual item insert executed</p>");
            }
            
            session.setAttribute("OrderID", orderid.toString());
            
            rs.close();
            stmt.close();
            connection.close();  
            
        }        
        catch(Exception e) {
            out.println("<p> " + e.getMessage() + "</p>");

        }
        out.println("</body>");  
        out.println("</html>");
        
        
        response.sendRedirect("/e-commerce_servlet/confirmation.jsp");
        
        
        //RequestDispatcher rs = request.getRequestDispatcher("/confirmation.jsp");
        //rs.forward(request, response);
        
        
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
        doGet(request, response);
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
