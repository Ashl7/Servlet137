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
        String firstname = request.getParameter("FirstNameField").trim();
        String lastname = request.getParameter("LastNameField").trim();
        String phonenumber = request.getParameter("PhoneNumberField").trim();
        String streetaddress = request.getParameter("ShippingAddressField").trim();
        String city = request.getParameter("CityField").trim();
        String state = request.getParameter("StateField").trim();
        String zipcode = request.getParameter("ZipCodeField").trim();
        String ccnumber = request.getParameter("CreditCardNumberField").trim();
        String ccname = request.getParameter("CreditCardNameField").trim();
        String expmonth = request.getParameter("ExpMonthField").trim();
        String expyear = request.getParameter("ExpYearField").trim();
        String shippingmethod = request.getParameter("Shipping").trim();
        String email = request.getParameter("EmailAddressField").trim();
        
        
        try {
            // Load the MYSQL JDBC driver.
            Class.forName("com.mysql.jdbc.Driver");             
            
            //Open a connection
            Connection connection = DriverManager.getConnection(url,username,password);       
            
            // Execute a query
            
            
            String sqlOrder = "INSERT INTO orders(first_name, last_name, phone, address, city, state, zip, card, name_on_card, month, year, shipping, email) VALUES("
                    + firstname + ", " + lastname + ", " + phonenumber + ", " + streetaddress + ", " + city + ", " + state + ", " + zipcode + ", " + ccnumber 
                    + ", " + ccname + ", " + expmonth + ", " + expyear + ", " + shippingmethod + ", " + email + ")";
            
            PreparedStatement statement = connection.prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
            
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Integer orderID = resultSet.getInt(1);
            session.setAttribute("OrderID", orderID);
            String sqlOrder2;
            for (Map.Entry<Integer, Integer> entry : usable.entrySet()) {
                sqlOrder2 = "INSERT INTO item_in_cart(hatID, amount, orderID) VALUES("
                        + entry.getKey().toString() + ", " + entry.getValue().toString() + ", "
                        + orderID.toString() + ")";
                
                statement.executeUpdate(sqlOrder2);
    }
            resultSet.close();
            statement.close();
            connection.close();  
        }        
        catch(Exception e) {
            e.printStackTrace();

        }
        
        
        RequestDispatcher rs = request.getRequestDispatcher("confirmation.jsp");
        rs.forward(request, response);
        
        
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
