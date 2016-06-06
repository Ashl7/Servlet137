<%-- 
    Document   : confirmation
    Created on : Jun 5, 2016, 10:45:02 PM
    Author     : Yuki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

<BODY style=background-color:burlywood>
   <!-- Set global information for the page -->
   <%@ page language="java" %>
   <%@ page import="java.sql.*" %>
   <% //Class.forName ("org.gjt.mm.mysql.Driver");
   Class.forName("com.mysql.jdbc.Driver");
        Connection connection = null;
        Statement statement = null; 
        ResultSet resultSet = null;
        HttpSession currentsession = request.getSession(true);
        
        String id = currentsession.getAttribute("OrderID").toString();
        
//        final String url = "jdbc:mysql://localhost/test";
//        final String dbname = "mysql";
//        final String username = "root";
//        final String password = "";
        final String url = "jdbc:mysql://sylvester-mccoy-v3.ics.uci.edu/inf124grp30";
        final String dbname = "inf124grp30";
        final String username = "inf124grp30";
        final String password = "st#VuY6R"; 
        
        connection = DriverManager.getConnection (url, username, password);
        
        String query = "SELECT first_name, last_name, address, city, state, zip, phone, email, shipping, hatID, amount, title, price FROM items_in_cart INNER JOIN hats ON hats.id = items_in_cart.hatID INNER JOIN orders ON orders.id = items_in_cart.orderID WHERE orderID =" + id;
        statement = connection.createStatement();
        resultSet = statement.executeQuery (query);
        
        
        
        
                
   %>

<h1> Thank you for your order</h1>
	<h2> This is your confirmation</h2>

		<table>
			<tr>
				<td>Order ID:</td><td> <%= id %></td>
			</tr>
                        <%
                            resultSet.next();
                            
                               int amount = resultSet.getInt ("amount"); 
                               String title = resultSet.getString("title");
                               Double price = resultSet.getDouble("price");
                               out.println ("<tr>");
                               out.println("<td>Hat Name:</td><td>" + title + "(" + amount + " x $" + String.valueOf(price) + ")</td>");
                               out.println("</tr>");                            
                            
                            
                            
                            String name = resultSet.getString("first_name") + resultSet.getString("last_name");
                            String addr = resultSet.getString("address");
                            String zip = resultSet.getString("zip");
                            String city_state_zip = resultSet.getString("city") + resultSet.getString("state") + zip;
                            String phone = resultSet.getString("phone");
                            String email = resultSet.getString("email");
                            String shipping = resultSet.getString("shipping");
                            ;
                            
                            double total = 0.0;
                            total += price * amount;
                                
                            while (resultSet.next())
                            { 

                                
                                
                               price = resultSet.getDouble("price");
                               amount = resultSet.getInt ("amount"); 
                               title = resultSet.getString("title");
                               total += resultSet.getDouble("price") * amount;

                               out.println ("<tr>");
                               out.println("<td>Hat Name:</td><td>" + title + "(" + amount + " x $" + String.valueOf(price) + ")</td>");
                               out.println("</tr>");
                            }        
                        %>
                        

                       
                        
			<tr>
				<td>Name:</td><td> <%=name %></td>
			</tr>
			<tr>
				<td>Address:</td><td> <%=addr%></td>
			</tr>

			<tr>
				<td></td><td> <%= city_state_zip%></td>
			</tr>
			<tr>
				<td>Phone:</td><td> <%=phone%></td>
			</tr>
			<tr>
				<td>Email:</td><td><%=email%></td>
			</tr>
			<tr>
				<td>Shipping:</td><td> <%=shipping%></td>
			</tr>
			<tr>
				<td>Tax:</td><td>
                                    <%
                                        query = "SELECT tax_rate FROM tax_rates WHERE zip_code = " + zip;
                                        resultSet = statement.executeQuery (query);
                                        resultSet.next();
                                        double taxRate = resultSet.getDouble("tax_rate");
                                        double tax = total * taxRate;
                                        out.println(tax);
                                        
                                    %></td>
			</tr>
			<tr>
				<td>Order Total:</td><td> <%
                                                               total += tax;
                                                               out.println(total);
                                                            %></td>
			</tr>
                        
                        <%
                            resultSet.close();
                            statement.close();
                            connection.close();  
                        %>


		</table>



</BODY>

</html>
