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

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Yuki
 */
public class DisplayOrderServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private HashMap<Integer,Integer> shopping_cart;
    private ArrayList<Hat> hatArray;
    
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
            
            //need to get prices for cart from this statement for use
            statement = connection.createStatement();
            String sqlQuery;           
            sqlQuery = "SELECT * FROM hats";
            resultSet = statement.executeQuery(sqlQuery);  
            
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        loadData();
        HttpSession session = request.getSession(true);
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Check Out Page</title>");
            
            //Javascript CheckOrder function
            /*
            out.println("<script type='text/javascript'>");
            out.println("function CheckOrder() { ");
                out.println("var HatID = document.OrderForm.HatID.value;");
                out.println("var QuantityOrdered = document.OrderForm.QuantityField.value;");
                out.println("var FirstName = document.OrderForm.FirstNameField.value;");
                out.println("var LastName = document.OrderForm.LastNameField.value;");
                out.println("var PhoneNumber = document.OrderForm.PhoneNumberField.value;");
                out.println("var ShippingAddress = document.OrderForm.ShippingAddressField.value;");
                out.println("var City = document.OrderForm.CityField.value;");
                out.println("var State = document.OrderForm.StateField.value;");
                out.println("var ZipCode = document.OrderForm.ZipCodeField.value;");
                out.println("var CCNumber = document.OrderForm.CreditCardNumberField.value;");
                out.println("var CCName = document.OrderForm.CreditCardNameField.value;");
                out.println("var ExpMonth = document.OrderForm.ExpMonthField.value;");
                out.println("var ExpYear = document.OrderForm.ExpYearField.value;");
                out.println("var ShippingMethod = '';");
                
        
                out.println("for (i=0; i<document.OrderForm.Shipping.length; i++) {");
                    out.println("if (document.OrderForm.Shipping[i].checked == true) {");
                        out.println("ShippingMethod = document.OrderForm.Shipping[i].value;");
                    out.println("}");
                out.println("}");
                
                out.println("if (parseInt(QuantityOrdered) <= 0) {");
                    out.println("alert ('Quantity must be a positive nonzero integer.');");
                    out.println("return false;");
                out.println("}");
                out.println("else if (!/^[A-z]+$/.test(FirstName)) {");
                    out.println("alert ('No numbers allowed in First Name field');");
                    out.println("return false;");
                out.println("}");
                out.println("else if (!/^[A-z]+$/.test(LastName)) {");
                    out.println("alert ('No numbers allowed in Last Name field');");
                    out.println("return false;");
                out.println("}");
                out.println("else if(!/^[0-9]{10}$/.test(PhoneNumber)) {");
                    out.println("alert ('Phone number must be 10 digits long. Example: 1234567890');");
                    out.println("return false;");
                out.println("}");
                out.println("else if(!/^[A-z]+$/.test(City)) {");
                    out.println("alert ('Non-alphabetic characters not allowed in City field')");
                    out.println("return false;");
                out.println("}");
                out.println("else if(!/^[A-z]+$/.test(State)) {");
                    out.println("alert ('Non-alphabetic characters not allowed in State field');");
                    out.println("return false;");
                out.println("}");
                out.println("else if(!/^[0-9]{5}$/.test(ZipCode)) {");
                    out.println("alert ('Zip Code must contain only numbers');");
                    out.println("return false;");
                out.println("}");
                out.println("else if(!/^[0-9]{16}$/.test(CCNumber)) {");
                    out.println("alert ('Credit Card number must contain only numbers');");
                    out.println("return false;");
                out.println("}");
                out.println("else if(!/^[A-z]+$/.test(CCName)) {");
                    out.println("alert ('Non-alphabetic characters not allowed in Credit Card Name field');");
                    out.println("return false;");
                out.println("}");
                out.println("else {");
                    
                out.println("return true;");
                out.println("}");
            out.println("</script>");
            */
            
            out.println("<link rel='stylesheet' type='text/css' href='../css/item_description.css'>");
            out.println("</head>");
            
           
            
            shopping_cart = (HashMap)request.getSession().getAttribute("cart");
            
            
            //display all the shopping cart stuff here
            
            
            
            
            //form element
            out.println("<body>");
            out.println("<div id ='form'>");
            out.println("<form name = 'OrderForm' method='POST' onsubmit='CheckOrder();' action='SubmitOrderServlet'>");
            out.println("<div class='left'>");
                    out.println("First Name: ");
                out.println("</div>");
                out.println("<div class='right'>");
                    out.println("<input id='FirstName' type='text' name='FirstNameField' required/><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("Last Name: ");
                out.println("</div>");
                
                out.println("<div class='right'>");
                    out.println("<input id='LastName' type='text' name='LastNameField' required/><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("Phone Number: ");
                out.println("</div>");
                out.println("<div class='right'>");
                    out.println("<input id='PhoneNumber' type='text' name='PhoneNumberField' maxlength='10' value='XXXXXXXXXX' required/><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("Address: ");
                out.println("</div>");
                out.println("<div class='right'>");
                    out.println("<input id='ShippingAddress' type='text' name='ShippingAddressField' required/><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("City: ");
                out.println("</div>");
                out.println("<div class='right'>");
                    out.println("<input id='CityName' type='text' name='CityField' required/><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("State: ");
                out.println("</div>");
                out.println("<div class='right'>");
                    out.println("<input id='StateName' type='text' name='StateField' required/><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("ZIP: ");
                out.println("</div>");
                out.println("<div class='right'>");
                    out.println("<input id='ZipCode' type='text' name='ZipCodeField' maxlength='5' required/><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("Card Number: ");
                out.println("</div>");
                out.println("<div class='right'>");
                    out.println("<input id='CreditCardNumber' type='text' name='CreditCardNumberField' maxlength='16' required/><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("Name on card: ");
                out.println("</div>");
                out.println("<div class='right'>");
                    out.println("<input id='CreditCardName' type='text' name='CreditCardNameField' required/><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("Expiration Date:");
                out.println("</div>");
                out.println("<div class='right'>");
                    out.println("<select id='CCExpiresMonth' name='ExpMonthField' required>");
                                    out.println("<option value='' SELECTED>--Month--");
                                    out.println("<option value='01'>January (01)");
                                    out.println("<option value='02'>February (02)");
                                    out.println("<option value='03'>March (03)");
                                    out.println("<option value='04'>April (04)");
                                    out.println("<option value='05'>May (05)");
                                    out.println("<option value='06'>June (06)");
                                    out.println("<option value='07'>July (07)");
                                    out.println("<option value='08'>August (08)");
                                    out.println("<option value='09'>September (09)");
                                    out.println("<option value='10'>October (10)");
                                    out.println("<option value='11'>November (11)");
                                    out.println("<option value='12'>December (12)");
                                 out.println("</select> /");
                                 out.println("<select id='CCExpiresYear' name='ExpYearField' required>");
                                    out.println("<option value='' SELECTED>--Year--");
                                    out.println("<option value='16'>2016");
                                    out.println("<option value='17'>2017");
                                    out.println("<option value='18'>2018");
                                    out.println("<option value='19'>2019");
                                    out.println("<option value='20'>2020");
                                    out.println("<option value='21'>2021");
                                    out.println("<option value='22'>2022");
                                    out.println("<option value='23'>2023");
                                    out.println("<option value='24'>2024");
                                    out.println("<option value='25'>2025");
                                    out.println("<option value='26'>2026");
                                    out.println("<option value='27'>2027");
                                 out.println("</select><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("Shipping Method:");
                out.println("</div>");
                out.println("<div class='right'>");
                    out.println("<input type='radio' name='Shipping' value='Overnight'/>Overnight<br />");
                    out.println("<input type='radio' name='Shipping'value='2 Days Air' />2 Days Air<br />");
                    out.println("<input type='radio' name='Shipping' value='6 Days Ground' />6 Days Ground<br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("<input type='submit' value='Submit Email' />");
                out.println("</div>");
                    
            out.println("</form>");
            out.println("</div>");
            out.println("</body>");
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
