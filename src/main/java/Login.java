

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	
	protected String first;
	protected String last;
	protected String foundation_name;
	

	
	private String styleTableForHTML = 				//style for scrollable table
			"<style>\n"	
		    + "table {\n"	
		    + "  font-family: arial, sans-serif;\n"
		    + "  border-collapse: collapse;\n"
		    + "  width: auto;"
		    + "  border:1px solid black;"
		    + "}\n"
		    
		    + ".tableFixHead {\n"
		    + "	 overflow: auto;\n"
		    + "  height: 200px;\n"		//table height
			+ "}\n"
			
		      
			+ ".tableFixHead thead th {\n"
		    + "  position: sticky;\n"
		    + "  top: 0;\n"
			+ "}\n"
		    
		    + "td, th {\n"
		    + "  border: 1px solid #dddddd;\n"
		    + "  text-align: left;\n"
		    + "  padding: 8px;\n"
		    + "	 font-size: 12px;\n"	
		    + "}\n"
		    
		    + "th{\n"
		   	+ "  background: #eee;\n"
		    + "}\n"
		    
		    + "tr:nth-child(even) {\n"
		    + "  background-color: #dddddd;\n"
		    + "}\n" +
		    "</style>" ;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String acctype= request.getParameter("acctype");
		String email;
		String password;
		try {
			email = request.getParameter("email");
			password = request.getParameter("password");
			if (email.isBlank() || password.isBlank()) {
		       	  PrintWriter out = response.getWriter();
		       	  response.setContentType("text/html");
		       	  out.println("<script type=\"text/javascript\">");
		       	  out.println("alert('Missing Info to Login!!\\ndsafafg');");
		       	  out.println("window.location.href=\"login.html\";");
		       	  out.println("</script>");
				}else {
					if(checkAccountExistence(acctype, email, password)) {
						if(acctype.equals("Donors")) {
							donor_profile(acctype, email, password, first, last, response, request);
							//System.out.println(first + " " + last);
						} else {
							foundation_profile(acctype, email, password, foundation_name, response, request);
							//System.out.println(foundation_name);
						}
					}else {
						PrintWriter out = response.getWriter();
				       	response.setContentType("text/html");
				       	out.println("<script type=\"text/javascript\">");
				       	out.println("alert('Wrong Email or Password!!');");
				       	out.println("window.location.href=\"login.html\";");
				       	out.println("</script>");
						System.out.println("Wrong Info Account");
					}
				}
		}catch(Exception e) {
		
		
			acctype = (String) request.getSession().getAttribute("acctype");
			email = (String) request.getSession().getAttribute("email");
			password = (String) request.getSession().getAttribute("password");
			
			/*System.out.println(acctype);
			System.out.println(email);
			System.out.println(password);
			*/
			
			if(acctype.equals("Donors")) {
				donor_profile(acctype, email, password, first, last, response, request);
			} else {
				foundation_profile(acctype, email, password, foundation_name, response, request);
			}
		}
	}
															//v1 = email; v2 = password
	protected boolean checkAccountExistence(String accType, String v1, String v2) {
		   Connection connection = null;

	       String selectSql = "SELECT * FROM "+ accType.toLowerCase() +" WHERE email = ? AND password = ?";
	       try {
 	          DBConnection.getDBConnection(getServletContext());
 	          connection = DBConnection.connection;
 	          PreparedStatement preparedStmt = connection.prepareStatement(selectSql);
 	          preparedStmt.setString(1, v1);
 	          preparedStmt.setString(2, v2);
 	          ResultSet rsCheck = preparedStmt.executeQuery();
 	          if (rsCheck.next() != false) {	//if account already existence
 	        	 if(accType.equals("Donors")) {
 	        		first = rsCheck.getString("first").trim();
 	        		last = rsCheck.getString("last").trim();
 	        	  }else {
 	        		foundation_name = rsCheck.getString("found_name").trim();
 	        	  }
 	        	  connection.close();
 	        	  return true;				
 	          }else {
 	        	  connection.close();
 	        	  return false;
 	          }
 	          
 	       } catch (Exception e) {
 	          e.printStackTrace();
 	       }
	       return false;
	       
	}
	
	protected void donor_profile(String accType, String email, String password, String first, String last, HttpServletResponse response, HttpServletRequest request) throws IOException {
		
		request.getSession().setAttribute("email", email);
		request.getSession().setAttribute("acctype", accType);
		request.getSession().setAttribute("password", password);
		
		response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
	      String title = "Welcome " + first + " " + last;
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	            "transitional//en\">\n"; //
	      out.println(docType + //
	            "<html>\n" + //
	            "<head><title>Welcome</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n"); 
	      
	      out.println("<div style=\'float:right\'>");
	      out.println("<form action=\"Logout\" method=\"POST\">");
	      out.println("<button>Logout</button>");
	      out.println("</form>");
	      out.println("</div>");
	            
	      out.println("<h1 align=\"left\"><t>" + title + "</h1>\n");
	      
	      

	      Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      try {
	         DBConnection.getDBConnection(getServletContext());
	         connection = DBConnection.connection;
	         	
	         	 
	        	 //String selectSQL = "SELECT * FROM finance WHERE donor_email LIKE ?";
	        	 
	        	 String selectSQL = "SELECT found_name, amount, spent, date_time, note "
	        	 		+ "FROM foundation, finance "
	        	 		+ "WHERE foundation.email = finance.found_email AND finance.donor_email = ? "
	        	 		+ "ORDER BY date_time DESC;";
	             preparedStatement = connection.prepareStatement(selectSQL);
	             preparedStatement.setString(1, email);
	             
	         ResultSet rs = preparedStatement.executeQuery();
	         
	         //out.println("<div style=\"height:100px; overflow:auto;\">\n");
	         out.println("<div class=\"tableFixHead\">\n");
	         
	         out.println("<table>\n"
	 				+ "	<tr>\n" 
	        		+ "	<thead>"	
	 				+ "		<th>Foundation</th>\n"
	 				+ "		<th>Amount</th>\n"
	 				+ "		<th>Spent</th>\n"
	 				+ "		<th>Datetime</th>\n"
	 				+ "		<th>Note</th>\n"
	 				+ "	</thead>\n"
	 				+ "	</tr>\n");
	         
	         String found_name;
	         String amount;
	 	     String spent;
	 	     String datetime;
	 	     String note;
	 	     
	 	     
	 	     //select found_name, amount, spent from foundation, finance 
	 	     //where foundation.email = "foodB@gmail.com" and foundation.email = finance.found_email;
	 	     
	         while (rs.next()) {
	            //int id = rs.getInt("id");
	            //String id = rs.getString("id").trim();
	        	found_name = rs.getString("found_name").trim();
	        	amount = rs.getString("amount").trim();
		        spent = rs.getString("spent").trim();
		        datetime = rs.getString("date_time").trim();
		        note = (rs.getString("note").trim());
		        
		        out.println("<tr>\n");
		        out.println("<td>" + found_name + "</td>\n");
		        out.println("<td>" + amount + "</td>\n");
		        out.println("<td>" + spent + "</td>\n");
		        out.println("<td>" + datetime + "</td>\n");
		        out.println("<td>" + note + "</td>\n");
		        out.println("</tr>\n");   
	         }
	         
	         out.println("</table>\n");
	         out.println("</div>\n");
	         //out.println("<form action=\"search_data.html\">");
	         out.println("<form action=\"SearchData\"><br>");
	         
	         out.println("<input type=\"submit\" name=\"submit\" value=\"Start Donation\" />");
	         
	         out.println("</form>");
	         
	         
	         
	         
	         //out.println("<input type=\"button\" onclick=\"location.href=\"search_data.html\";\" value=\"Search Foundation\" /><br/>");
	        
	         out.println(styleTableForHTML);//
	         
	         out.println("</body></html>");
	         
	         //out.println("<a href=./sign_up.html>Sign Up</a> <br>");
	        // out.println("</body></html>");
	         rs.close();
	         preparedStatement.close();
	         connection.close();
	      } catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (preparedStatement != null)
	               preparedStatement.close();
	         } catch (SQLException se2) {
	         }
	         try {
	            if (connection != null)
	               connection.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
	}
	
	protected void foundation_profile(String accType, String email, String password, String foundation_name, HttpServletResponse response, HttpServletRequest request) throws IOException {
		request.getSession().setAttribute("email", email);
		request.getSession().setAttribute("acctype", accType);
		request.getSession().setAttribute("password", password);
		
		/*
		System.out.printf("%s %s %s\n",request.getSession().getAttribute("email"),
				request.getSession().getAttribute("acctype"),request.getSession().getAttribute("password"));
		
		
		System.out.printf("%s %s %s\n",request.getSession().getAttribute("email"),
				request.getSession().getAttribute("acctype"),request.getSession().getAttribute("password"));
		*/
		
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
	      String title = foundation_name + " foundation";
	      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
	            "transitional//en\">\n"; //
	      /*out.println(docType + //
	            "<html>\n" + //
	    		  
	            "<head><title>Welcome</title></head>\n" + //
	            "<body bgcolor=\"#f0f0f0\">\n" + //
	            //"<h1 align=\"left\">" + title + "</h1>\n");
	      		"<h1>" + title + "</h1>\n" 
	            
	    		  );*/
	      out.println(docType + //
		            "<html>\n" + //
		            "<head><title>Welcome</title></head>\n" + //
		            "<body bgcolor=\"#f0f0f0\">\n"); 
		      
		      out.println("<div style=\'float:right\'>");
		      out.println("<form action=\"Logout\" method=\"POST\">");
		      out.println("<button>Logout</button>");
		      out.println("</form>");
		      out.println("</div>");
		            
		      out.println("<h1 align=\"left\"><t>" + title + "</h1>\n");
	      
	      
	      
	      Connection connection = null;
	      PreparedStatement preparedStatement = null;
	      try {
	    	  	String amount;
		        String spent;
		        String datetime;
		        double total = 0;
		        String note;
		        
		        
		        
	         DBConnection.getDBConnection(getServletContext());
	         connection = DBConnection.connection;

	         
	        	 //String selectSQL = "SELECT * FROM finance WHERE found_email LIKE ?";
	        	 
	        	 String selectSQL = "SELECT amount, spent, date_time, note "
	        	 		+ "FROM finance WHERE finance.found_email = ? "
	        	 		+ "ORDER BY date_time DESC;";
	             preparedStatement = connection.prepareStatement(selectSQL);
	             preparedStatement.setString(1, email);
	             
	         ResultSet rs = preparedStatement.executeQuery();
	         
	         
	         //out.println("<div style=\"height:100px; overflow:auto;\">\n");
	         
	         out.println("<div class=\"tableFixHead\">\n");
	         
	        out.println("<table>\n"
				+ "	<tr>\n"
				+ "	<thead>"
				+ "		<th>Amount</th>\n"
				+ "		<th>Spent</th>\n"
				+ "		<th>Datetime</th>\n"
				+ "		<th>Note</th>\n"
				+ "	</thead>"
				+ "	</tr>\n");
	         
	        
	         while (rs.next()) {
	            //int id = rs.getInt("id");
	            //String id = rs.getString("id").trim();
	            amount = rs.getString("amount").trim();
	            spent = rs.getString("spent").trim();
	            datetime = rs.getString("date_time").trim();
	            note = rs.getString("note").trim();
	            
	            
	            	total += (Double.parseDouble(amount)) - (Double.parseDouble(spent));
	            
	            
	            	out.println("<tr>\n");
	            	out.println("<td>" + amount + "</td>\n");
	            	out.println("<td>" + spent + "</td>\n");
	            	out.println("<td>" + datetime + "</td>\n");
	            	out.println("<td>" + note + "</td>\n");
	            	out.println("</tr>\n");
	         }
	         request.getSession().setAttribute("total", total);
	         
	         out.println("</table>\n");
	         out.println("</div>\n");
	         
	         
	         out.printf("\n<br>Total: $%.2f <br>\n", total);	
	         //out.println("<form action=\"EMPTY\">");								//Revise here*****
	         //out.println("<input type=\"submit\" value=\"Withdraw\" /></form>");
	         out.println("<form action=\"Transaction\" method=\"POST\">");
	         
	         out.println("<br>Amount to withdraw:  <input type=\"text\" name=\"amount\">");
	         out.println("<br>Note:<br><textarea name=\"note\" cols=\"40\" rows=\"5\"></textarea>");
	         out.println("<br><input type=\"submit\" name=\"submit\" value=\"Withdraw\" /><br/>\n");
	         out.println("</form>\n");
	         
	         
	         //out.println("<input type=\"button\" onclick=\"location.href=\"search_data.html\";\" value=\"Search Foundation\" /><br/>");
	        
	         out.println(styleTableForHTML);//
	         
	         
	         out.println("</body></html>");
	         
	         //out.println("<a href=./sign_up.html>Sign Up</a> <br>");
	        // out.println("</body></html>");
	         rs.close();
	         preparedStatement.close();
	         connection.close();
	      } catch (SQLException se) {
	         se.printStackTrace();
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         try {
	            if (preparedStatement != null)
	               preparedStatement.close();
	         } catch (SQLException se2) {
	         }
	         try {
	            if (connection != null)
	               connection.close();
	         } catch (SQLException se) {
	            se.printStackTrace();
	         }
	      }
	}
	
	/*private void logout(HttpServletRequest request) {
		request.getSession().removeAttribute("email");
		request.getSession().removeAttribute("acctype");
		request.getSession().removeAttribute("password");
	}*/
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
	


