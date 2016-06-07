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
import java.util.Map;
import com.mysql.jdbc.Driver;
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
    
    private ShoppingCart cart;
    private ArrayList<Hat> hatArray;
    
    private void loadData() {
        final String url = "jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124grp30";
        final String dbname = "inf124grp30";
        final String username = "inf124grp30";
        final String password = "st#VuY6R";   
        
        hatArray = new ArrayList<Hat>();
        
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
        cart = new ShoppingCart();
        
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html><head>");
            out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
            out.println("<title>Check Out Page</title>");
            
            
            
            //Javascript function for mouseover image enlargement
            out.println("<script type='text/javascript'>");
            out.println("function normalImg(x){");
            out.println("x.style.height = \"200px\";");
            out.println("x.style.width = \"200px\";}");
            out.println("function largeImg(x){");
            out.println("x.style.height = \"400px\";");
            out.println("x.style.width = \"400px\";}");
            
            //CheckOrder function for validation of input from OrderForm
            out.println("function CheckOrder() { ");
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
                
                out.println("if (!/^[A-z]+$/.test(FirstName)) {");
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
                out.println("else if(!/^[a-zA-Z\\s]/.test(City)) {");
                    out.println("alert ('Non-alphabetic characters not allowed in City field')");
                    out.println("return false;");
                out.println("}");
                out.println("else if(!/^[a-zA-Z\\s]/.test(State)) {");
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
                out.println("else if(!/^[a-zA-Z\\s]/+.test(CCName)) {");
                    out.println("alert ('Non-alphabetic characters not allowed in Credit Card Name field');");
                    out.println("return false;");
                out.println("}");
                out.println("else {");
                    
                out.println("return true;");
                out.println("}");
            out.println("</script>");
            
            //linking to display_order.css for styling elements of page
            out.println("<link rel='stylesheet' type='text/css' href='../css/display_order.css'>");
            out.println("</head>");
            
            out.println("<body>");
            
            out.println("<p style=\"font-size:160%;\">Cart Contents</p>");
            
            out.println("<table id=\"cartcontent\">");
            //grabbing the cart object through session object
            cart = (ShoppingCart) session.getAttribute("ShoppingCart");
            Integer quantity;
            Integer total_price = 0;
            HashMap<Integer, Integer> usable = cart.getItems();
            //iterate through cart contents and display correct items depending on hatID
            for (Map.Entry<Integer, Integer> entry : usable.entrySet()) {
                //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
                if(entry.getKey() == 1)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(0).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(0).getImage_url() + "\" alt =\"" + hatArray.get(0).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(0).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(0).getPrice() * quantity);
                }
                else if(entry.getKey() == 2)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(1).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(1).getImage_url() + "\" alt =\"" + hatArray.get(1).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(1).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(1).getPrice() * quantity);
                }
                else if(entry.getKey() == 3)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(2).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(2).getImage_url() + "\" alt =\"" + hatArray.get(2).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(2).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(2).getPrice() * quantity);
                }
                else if(entry.getKey() == 4)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(3).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(3).getImage_url() + "\" alt =\"" + hatArray.get(3).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(3).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(3).getPrice() * quantity);
                }
                else if(entry.getKey() == 5)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(4).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(4).getImage_url() + "\" alt =\"" + hatArray.get(4).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(4).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(4).getPrice() * quantity);
                }
                else if(entry.getKey() == 6)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(5).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(5).getImage_url() + "\" alt =\"" + hatArray.get(5).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(5).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(5).getPrice() * quantity);
                }
                else if(entry.getKey() == 7)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(6).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(6).getImage_url() + "\" alt =\"" + hatArray.get(6).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(6).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(6).getPrice() * quantity);
                }
                else if(entry.getKey() == 8)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(7).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(7).getImage_url() + "\" alt =\"" + hatArray.get(7).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(7).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(7).getPrice() * quantity);
                }else if(entry.getKey() == 9)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(8).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(8).getImage_url() + "\" alt =\"" + hatArray.get(8).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(8).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(8).getPrice() * quantity);
                }
                else if(entry.getKey() == 10)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(9).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(9).getImage_url() + "\" alt =\"" + hatArray.get(9).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(9).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(9).getPrice() * quantity);
                }
                else if(entry.getKey() == 11)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(10).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(10).getImage_url() + "\" alt =\"" + hatArray.get(10).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(10).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(10).getPrice() * quantity);
                }
                else if(entry.getKey() == 12)
                {
                    quantity = entry.getValue();
                    out.println("<tr>");
                    out.println("<td><a href=\"SingleItemServlet?id=" + hatArray.get(11).getId() + "\">");
                    out.println("<img src=\"img/hats/" + hatArray.get(11).getImage_url() + "\" alt =\"" + hatArray.get(11).getTitle() + "\" align = \"middle\" height=\"200\" weight = \"200\" onmouseover = \"largeImg(this)\" onmouseout = \"normalImg(this)\">");
                    out.println("</a>");
                    out.println("<p>Price per hat: " + hatArray.get(11).getPrice() + "</p>");
                    out.println("<p>Quantity in Cart: " + quantity.toString() + "</p>");
                    out.println("</tr>");
                    total_price += (hatArray.get(11).getPrice() * quantity);
                }
                
                
            }
            out.println("<tr>");
            out.println("<p> Total Price in Cart: " + total_price.toString() + "</p>");
            out.println("</tr>");
            out.println("</table>");   
            
            out.println("<p style=\"font-size:120%;\">Shipping Information</p>");
            
            //form here
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
                    out.println("Email: ");
                out.println("</div>");
                
                out.println("<div class='right'>");
                    out.println("<input id='EmailAddress' type='text' name='EmailAddressField' required/><br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("Phone Number: ");
                out.println("</div>");
                
                out.println("<div class='right'>");
                    out.println("<input id='PhoneNumber' type='text' name='PhoneNumberField' maxlength='10' value='##########' required/><br />");
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
                    out.println("<input type='radio' name='Shipping' value='2 Days Air' />2 Days Air<br />");
                    out.println("<input type='radio' name='Shipping' value='6 Days Ground' />6 Days Ground<br />");
                out.println("</div>");
                out.println("<div class='left'>");
                    out.println("<input type='submit' value='Submit Order' /><input type=\"reset\" value=\"Reset!\">");
                out.println("</div>");
                
            out.println("</form>");
            out.println("</div>");
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
